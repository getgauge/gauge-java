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

import com.github.javaparser.ast.CompilationUnit;
import com.thoughtworks.gauge.StepValue;
import com.thoughtworks.gauge.registry.StepRegistry;
import gauge.messages.Messages;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JavaRefactoring {
    private final StepValue oldStepValue;
    private final StepValue newStepValue;
    private final List<Messages.ParameterPosition> paramPositions;

    public JavaRefactoring(StepValue oldStepValue, StepValue newStepValue, List<Messages.ParameterPosition> paramPositionsList) {
        this.oldStepValue = oldStepValue;
        this.newStepValue = newStepValue;
        this.paramPositions = paramPositionsList;
    }

    public RefactoringResult performRefactoring() {
        String fileName = StepRegistry.getFileName(oldStepValue.getStepText());
        if (fileName == null || fileName.isEmpty()) {
            return new RefactoringResult(false, "Step Implementation Not Found: Unable to find a file Name to refactor");
        }
        if (StepRegistry.hasAlias(oldStepValue.getStepText())) {
            return new RefactoringResult(false, "Refactoring for steps having aliases are not supported.");
        }
        JavaRefactoringElement element;
        try {
            element = createJavaRefactoringElement(fileName);
            new FileModifier(element).refactor();
        } catch (IOException e) {
            return new RefactoringResult(false, "Unable to read/write file while refactoring. " + e.getMessage());
        } catch (RefactoringException e) {
            return new RefactoringResult(false, "Step Implementation Not Found: " + e.getMessage());
        } catch (Exception e) {
            return new RefactoringResult(false, "Refactoring failed: " + e.getMessage());
        }

        return new RefactoringResult(true, "", element.getFile().getAbsolutePath());
    }

    public JavaRefactoringElement createJavaRefactoringElement(String fileName) throws RefactoringException {
        System.out.println("WD: " +Util.workingDir());
        System.out.println("Filename: " + fileName);

        List<JavaParseWorker> javaParseWorkers = parseJavaFiles(Util.workingDir(), fileName);
        if (javaParseWorkers.isEmpty()) {
            throw new RefactoringException("Unable to find file: " + fileName);
        }
        try {
            for (JavaParseWorker javaFile : javaParseWorkers) {
                System.out.println("Processing: " + javaFile.getJavaFile().getAbsolutePath());
                CompilationUnit compilationUnit = javaFile.getCompilationUnit();
                RefactoringMethodVisitor methodVisitor = new RefactoringMethodVisitor(oldStepValue, newStepValue, paramPositions);
                methodVisitor.visit(compilationUnit, null);
                if (methodVisitor.refactored()) {
                    JavaRefactoringElement javaElement = methodVisitor.getRefactoredJavaElement();
                    javaElement.setFile(javaFile.getJavaFile());
                    return javaElement;
                }
            }
        } catch (Exception e) {
            throw new RefactoringException("Failed creating java element: " + e.getMessage());
        }
        return null;
    }

    private List<JavaParseWorker> parseJavaFiles(File workingDir, String fileName) {
        ArrayList<JavaParseWorker> javaFiles = new ArrayList<JavaParseWorker>();
        File[] allFiles = workingDir.listFiles();
        for (File file : allFiles) {
            if (file.isDirectory()) {
                javaFiles.addAll(parseJavaFiles(file, fileName));
            } else {
                if (file.getAbsolutePath().contains(fileName)) {
                    JavaParseWorker worker = new JavaParseWorker(file);
                    worker.start();
                    javaFiles.add(worker);
                }
            }
        }
        return javaFiles;
    }
}
