// Copyright 2015 ThoughtWorks, Inc.

// This file is part of Gauge-Java.

// Gauge-Java is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.

// Gauge-Java is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.

// You should have received a copy of the GNU General Public License
// along with Gauge-Java.  If not, see <http://www.gnu.org/licenses/>.

package com.thoughtworks.gauge.refactor;

import com.thoughtworks.gauge.StepRegistry;
import com.thoughtworks.gauge.StepValue;
import gauge.messages.Messages;
import org.walkmod.javalang.JavaParser;
import org.walkmod.javalang.ast.CompilationUnit;

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
        if (fileName == null) {
            return new RefactoringResult(false, "Step Implementation Not Found");
        }
        if (StepRegistry.hasAlias(oldStepValue.getStepText())) {
            return new RefactoringResult(false, "Refactoring for steps having aliases are not supported.");
        }
        JavaRefactoringElement element = createJavaRefactoringElement(fileName);
        if (element == null) {
            return new RefactoringResult(false, "Step Implementation Not Found");
        }
        try {
            new FileModifier(element).refactor();
        } catch (IOException e) {
            return new RefactoringResult(false, "Unable to read/write file while refactoring. " + e.getMessage());
        }
        return new RefactoringResult(true, "", element.getFile().getAbsolutePath() );
    }

    public JavaRefactoringElement createJavaRefactoringElement(String fileName) {
        JavaParser.setCacheParser(true);
        List<JavaParseWorker> javaParseWorkers = parseJavaFiles(Util.workingDir(), fileName);
        try {
            for (JavaParseWorker javaFile : javaParseWorkers) {
                CompilationUnit compilationUnit = javaFile.getCompilationUnit();
                RefactoringMethodVisitor methodVisitor = new RefactoringMethodVisitor(oldStepValue, newStepValue, paramPositions);
                methodVisitor.visit(compilationUnit, null);
                if (methodVisitor.refactored()) {
                    JavaRefactoringElement javaElement = methodVisitor.getRefactoredJavaElement();
                    javaElement.setFile(javaFile.getJavaFile());
                    return javaElement;
                }
            }
        } catch (Exception ignored){}
        return null;
    }

    private List<JavaParseWorker> parseJavaFiles(File workingDir, String fileName) {
        ArrayList<JavaParseWorker> javaFiles = new ArrayList<JavaParseWorker>();
        File[] allFiles = workingDir.listFiles();
        for (File file : allFiles) {
            if (file.isDirectory()) {
                javaFiles.addAll(parseJavaFiles(file, fileName));
            } else {
                if (file.getName().equals(fileName)) {
                    JavaParseWorker worker = new JavaParseWorker(file);
                    worker.start();
                    javaFiles.add(worker);
                }
            }
        }
        return javaFiles;
    }
}
