// Copyright 2015 ThoughtWorks, Inc.

// This file is part of Gauge-Java.

// This program is free software.
//
// It is dual-licensed under:
// 1) the GNU General Public License as published by the Free Software Foundation,
// either version 3 of the License, or (at your option) any later version;
// or
// 2) the Eclipse Public License v1.0.
//
// You can redistribute it and/or modify it under the terms of either license.
// We would then provide copied of each license in a separate .txt file with the name of the license as the title of the file.

package com.thoughtworks.gauge.refactor;

import com.github.javaparser.Position;
import com.github.javaparser.Range;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.Parameter;
import com.thoughtworks.gauge.GaugeConstant;
import com.thoughtworks.gauge.StepValue;
import com.thoughtworks.gauge.Util;
import com.thoughtworks.gauge.registry.StepRegistry;
import gauge.messages.Messages;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JavaRefactoring {
    private final StepValue oldStepValue;
    private final StepValue newStepValue;
    private final List<Messages.ParameterPosition> paramPositions;
    private StepRegistry registry;
    private String parameterizedStepValue;
    private boolean saveChanges;

    public JavaRefactoring(StepValue oldStepValue, StepValue newStepValue, List<Messages.ParameterPosition> paramPositionsList, StepRegistry registry, String parameterizedStepValue, boolean saveChanges) {
        this.oldStepValue = oldStepValue;
        this.newStepValue = newStepValue;
        this.paramPositions = paramPositionsList;
        this.registry = registry;
        this.parameterizedStepValue = parameterizedStepValue;
        this.saveChanges = saveChanges;
    }

    public RefactoringResult performRefactoring() {
        String oldStepText = oldStepValue.getStepText();
        String fileName;
        Boolean hasAlias;

        if (System.getenv(GaugeConstant.GAUGE_LSP_GRPC) != null) {
            fileName = registry.get(oldStepText).getFileName();
            hasAlias = registry.get(oldStepText).getHasAlias();
        } else {
            fileName = registry.getFileName(oldStepText);
            hasAlias = registry.getAllAliasAnnotationTextsFor(oldStepText).size() > 1;
        }

        if (fileName == null || fileName.isEmpty()) {
            return new RefactoringResult(false, "Step Implementation Not Found: Unable to find a file Name to refactor");
        }
        if (hasAlias) {
            return new RefactoringResult(false, "Refactoring for steps having aliases are not supported.");
        }
        if (registry.hasMultipleImplementations(oldStepText)) {
            return new RefactoringResult(false, "Duplicate step implementation found.");
        }

        JavaRefactoringElement element;
        try {
            element = createJavaRefactoringElement(fileName);
            if (saveChanges) {
                new FileModifier(element).refactor();
            }
        } catch (IOException e) {
            return new RefactoringResult(false, "Unable to read/write file while refactoring. " + e.getMessage());
        } catch (RefactoringException e) {
            return new RefactoringResult(false, "Step Implementation Not Found: " + e.getMessage());
        } catch (Exception e) {
            return new RefactoringResult(false, "Refactoring failed: " + e.getMessage());
        }
        return new RefactoringResult(true, element);
    }

    private Range getStepRange(Range range) {
        return new Range(new Position(range.begin.line, range.begin.column - 1), new Position(range.end.line, range.end.column));
    }

    private String updatedParameters(List<Parameter> parameters) {
        return new StringBuilder(StringUtils.join(parameters, ", ")).toString();
    }

    private Range getParamsRange() {
        List<Parameter> parameters = registry.get(oldStepValue.getStepText()).getParameters();
        if (parameters.isEmpty()) {
            int line = registry.get(oldStepValue.getStepText()).getSpan().begin.line + 1;
            int column = registry.get(oldStepValue.getStepText()).getName().length();
            return new Range(new Position(line, column), new Position(line, column));
        }
        Range firstParam = parameters.get(0).getRange();
        Range lastParam = parameters.get(parameters.size() - 1).getRange();
        return new Range(new Position(firstParam.begin.line, firstParam.begin.column - 1), new Position(lastParam.end.line, lastParam.end.column));
    }

    JavaRefactoringElement createJavaRefactoringElement(String fileName) throws RefactoringException {
        List<JavaParseWorker> javaParseWorkers = parseJavaFiles(Util.workingDir(), fileName);
        if (javaParseWorkers.isEmpty()) {
            throw new RefactoringException("Unable to find file: " + fileName);
        }
        try {
            for (JavaParseWorker javaFile : javaParseWorkers) {
                CompilationUnit compilationUnit = javaFile.getCompilationUnit();
                RefactoringMethodVisitor methodVisitor = new RefactoringMethodVisitor(oldStepValue, newStepValue, paramPositions);
                methodVisitor.visit(compilationUnit, null);
                if (methodVisitor.refactored()) {
                    JavaRefactoringElement javaElement = methodVisitor.getRefactoredJavaElement();
                    javaElement.setFile(javaFile.getJavaFile());
                    if (!saveChanges) {
                        javaElement = addJavaDiffElements(methodVisitor, javaElement);
                    }
                    return javaElement;
                }
            }
        } catch (Exception e) {
            throw new RefactoringException("Failed creating java element: " + e.getMessage());
        }
        throw new RefactoringException("Unable to find implementation");
    }

    private JavaRefactoringElement addJavaDiffElements(RefactoringMethodVisitor methodVisitor, JavaRefactoringElement javaElement) {
        List<Parameter> newParameters = methodVisitor.getNewParameters();
        Range stepLineSpan = methodVisitor.getStepLineSpan();
        javaElement.addDiffs(new Diff("\"" + parameterizedStepValue + "\"", getStepRange(stepLineSpan)));
        javaElement.addDiffs(new Diff(updatedParameters(newParameters), getParamsRange()));
        return javaElement;
    }

    private List<JavaParseWorker> parseJavaFiles(File workingDir, String fileName) {
        ArrayList<JavaParseWorker> javaFiles = new ArrayList<>();
        File[] allFiles = workingDir.listFiles();
        for (File file : allFiles) {
            if (file.isDirectory()) {
                javaFiles.addAll(parseJavaFiles(file, fileName));
            } else {
                if (file.getAbsolutePath().endsWith(fileName)) {
                    JavaParseWorker worker = new JavaParseWorker(file);
                    worker.start();
                    javaFiles.add(worker);
                }
            }
        }
        return javaFiles;
    }
}
