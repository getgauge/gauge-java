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

package com.thoughtworks.gauge.processor;


import com.thoughtworks.gauge.StepRegistry;
import com.thoughtworks.gauge.refactor.RefactorFile;
import gauge.messages.Messages;
import gauge.messages.Spec;
import org.walkmod.javalang.JavaParser;
import org.walkmod.javalang.ast.CompilationUnit;
import org.walkmod.javalang.ast.body.MethodDeclaration;
import org.walkmod.javalang.ast.body.Parameter;
import org.walkmod.javalang.ast.body.VariableDeclaratorId;
import org.walkmod.javalang.ast.expr.AnnotationExpr;
import org.walkmod.javalang.ast.expr.BinaryExpr;
import org.walkmod.javalang.ast.expr.SingleMemberAnnotationExpr;
import org.walkmod.javalang.ast.expr.StringLiteralExpr;
import org.walkmod.javalang.ast.type.ClassOrInterfaceType;
import org.walkmod.javalang.visitors.VoidVisitorAdapter;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RefactorRequestProcessor implements IMessageProcessor {
    @Override
    public Messages.Message process(Messages.Message message) {
        Messages.RefactorRequest refactorRequest = message.getRefactorRequest();
        int size = StepRegistry.getStepAnnotationFor(StepRegistry.getAliasStepTexts(refactorRequest.getOldStepValue().getStepValue())).size();
        if (size > 1)   return createRefactorResponse(message, false, "Refactoring for steps having aliases are not supported.");
        JavaParser.setCacheParser(true);
        String fileName = StepRegistry.getFileName(StepRegistry.get(refactorRequest.getOldStepValue().getStepValue()));
        if (fileName == null) return createRefactorResponse(message, false, "Step Implementation Not Found");
        try {
            JavaElement javaElement = refactorJavaFile(refactorRequest.getOldStepValue().getParameterizedStepValue(),
                                               refactorRequest.getNewStepValue(),
                                               refactorRequest.getParamPositionsList(),
                                               fileName);
            if (javaElement == null) return createRefactorResponse(message, false, "Step Implementation Not Found");
            new RefactorFile(javaElement).refactor();
            return createRefactorResponseWithFilepath(message, true, "", javaElement.file.getAbsolutePath());
        } catch (IOException e) {
            return createRefactorResponse(message, false, "Unable to read/write file while refactoring");
        } catch (Exception e) {
            return createRefactorResponse(message, false, "Unable to perform java refactoring: \n" + e.toString());
        }
    }
    
    private Messages.Message createRefactorResponseWithFilepath(Messages.Message message, boolean success, String errorMessage, String filePath) {
        return Messages.Message.newBuilder()
                .setMessageId(message.getMessageId())
                .setMessageType(Messages.Message.MessageType.RefactorResponse)
                .setRefactorResponse(Messages.RefactorResponse.newBuilder().setSuccess(success).setError(errorMessage).addFilesChanged(filePath).build())
                .build();
    }

    private Messages.Message createRefactorResponse(Messages.Message message, boolean success, String errorMessage) {
            return Messages.Message.newBuilder()
                    .setMessageId(message.getMessageId())
                    .setMessageType(Messages.Message.MessageType.RefactorResponse)
                    .setRefactorResponse(Messages.RefactorResponse.newBuilder().setSuccess(success).setError(errorMessage).build())
                    .build();
    }

    private JavaElement refactorJavaFile(String oldStepValue, Spec.ProtoStepValue newStepValue, List<Messages.ParameterPosition> paramPositions, String fileName) {
        File workingDir = new File(System.getProperty("user.dir"));
        List<JavaParseWorker> javaParseWorkers = parseJavaFiles(workingDir, fileName);
        try {
            for (JavaParseWorker javaFile : javaParseWorkers) {
                CompilationUnit compilationUnit = javaFile.getCompilationUnit();
                MethodVisitor methodVisitor = new MethodVisitor(oldStepValue, newStepValue, paramPositions);
                methodVisitor.visit(compilationUnit, null);
                if (methodVisitor.refactored) {
                    methodVisitor.javaElement.file = javaFile.getJavaFile();
                    return methodVisitor.javaElement;
                }
            }
        }catch (Exception ignored){}
        return null;
    }

    private List<JavaParseWorker> parseJavaFiles(File workingDir, String fileName) {
        ArrayList<JavaParseWorker> javaFiles = new ArrayList<JavaParseWorker>();
        File[] allFiles = workingDir.listFiles();
        for (File file : allFiles) {
            if (file.isDirectory()) {
                javaFiles.addAll(parseJavaFiles(file, fileName));
            } else {
                try {
                    if (file.getName().toLowerCase().endsWith(".java") && file.getCanonicalPath().contains(fileName)) {
                        JavaParseWorker worker = new JavaParseWorker(file);
                        worker.start();
                        javaFiles.add(worker);
                    }
                } catch (IOException ignored) {
                }
            }
        }
        return javaFiles;
    }

    class JavaParseWorker extends Thread {

        private File javaFile;
        private CompilationUnit compilationUnit;

        JavaParseWorker(File javaFile) {
            this.javaFile = javaFile;
        }

        @Override
        public void run() {
            try {
                FileInputStream in = new FileInputStream(javaFile);
                compilationUnit = JavaParser.parse(in);
                in.close();
            } catch (Exception e) {
                // ignore exceptions
            }
        }

        public File getJavaFile() {
            return javaFile;
        }

        CompilationUnit getCompilationUnit() {
            try {
                join();
            } catch (InterruptedException e) {

            }
            return compilationUnit;
        }
    }

    public class JavaElement {
        public int beginLine;
        public int endLine;
        public int beginColumn;
        public int endColumn;
        public String text;
        public File file;

        public JavaElement(int beginLine, int endLine, int beginColumn, int endColumn, String text, File file) {
            this.beginLine = beginLine;
            this.endLine = endLine;
            this.beginColumn = beginColumn;
            this.endColumn = endColumn;
            this.text = text;
            this.file = file;
        }
    }

    private class MethodVisitor extends VoidVisitorAdapter {
        private String oldStepValue;
        private Spec.ProtoStepValueOrBuilder newStepValue;
        private List<Messages.ParameterPosition> paramPositions;
        private boolean refactored;
        public JavaElement javaElement;

        public MethodVisitor(String oldStepValue, Spec.ProtoStepValueOrBuilder newStepValue, List<Messages.ParameterPosition> paramPositions) {
            this.oldStepValue = oldStepValue;
            this.newStepValue = newStepValue;
            this.paramPositions = paramPositions;
        }

        @Override
        public void visit(MethodDeclaration methodDeclaration, Object arg) {
            try {
                List<AnnotationExpr> annotations = methodDeclaration.getAnnotations();
                if (annotations == null)
                    return;
                for (AnnotationExpr annotationExpr : annotations) {
                    if (!(annotationExpr instanceof SingleMemberAnnotationExpr))
                        continue;

                    SingleMemberAnnotationExpr annotation = (SingleMemberAnnotationExpr) annotationExpr;
                    if (annotation.getMemberValue() instanceof BinaryExpr) {
                        ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");
                        try {
                            Object result = engine.eval(annotation.getMemberValue().toString());
                            refactor(methodDeclaration, new StringLiteralExpr(result.toString()), annotation);
                        } catch (ScriptException e) {
                            continue;
                        }
                    }
                    if (annotation.getMemberValue() instanceof StringLiteralExpr) {
                        StringLiteralExpr memberValue = (StringLiteralExpr) annotation.getMemberValue();
                        refactor(methodDeclaration, memberValue, annotation);
                    }
                }
            } catch (Exception ignored) {
            }
        }

        private void refactor(MethodDeclaration methodDeclaration, StringLiteralExpr memberValue, SingleMemberAnnotationExpr annotation) {
            if (memberValue.getValue().equals(oldStepValue)) {
                List<Parameter> newParameters = Arrays.asList(new Parameter[paramPositions.size()]);
                memberValue.setValue(newStepValue.getParameterizedStepValue());
                List<Parameter> parameters = methodDeclaration.getParameters();
                for (int i = 0, paramPositionsSize = paramPositions.size(); i < paramPositionsSize; i++) {
                    if (paramPositions.get(i).getOldPosition() < 0)
                        newParameters.set(i, new Parameter(new ClassOrInterfaceType("String"), new VariableDeclaratorId(convertToCamelCase(newStepValue.getParameters(i)))));
                    else
                        newParameters.set(paramPositions.get(i).getNewPosition(), parameters.get(paramPositions.get(i).getOldPosition()));
                }
                methodDeclaration.setParameters(newParameters);
                annotation.setMemberValue(memberValue);
                this.javaElement = new JavaElement(methodDeclaration.getBeginLine(), methodDeclaration.getEndLine(), methodDeclaration.getBeginColumn(), methodDeclaration.getEndColumn(), methodDeclaration.toString(), null);
                this.refactored = true;
            }
        }
    }

    private String convertToCamelCase(String parameter) {
        String[] words = parameter.split(" ");
        String text = words[0];
        for (int i = 1, wordsLength = words.length; i < wordsLength; i++) {
            text += words[i].substring(0, 1).toUpperCase() + words[i].substring(1);
        }
        return text;
    }
}
