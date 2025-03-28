/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge.refactor;

import com.github.javaparser.Range;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.utils.StringEscapeUtils;
import com.thoughtworks.gauge.Logger;
import com.thoughtworks.gauge.StepValue;
import com.thoughtworks.gauge.Util;
import gauge.messages.Messages;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public class RefactoringMethodVisitor extends VoidVisitorAdapter {
    private final StepValue oldStepValue;
    private final StepValue newStepValue;
    private final List<Messages.ParameterPosition> paramPositions;
    private boolean refactored;
    private JavaRefactoringElement javaElement;
    private final NodeList<Parameter> newParameters = new NodeList<>();
    private Range stepSpan;


    public RefactoringMethodVisitor(StepValue oldStepValue, StepValue newStepValue, List<Messages.ParameterPosition> paramPositions) {
        this.oldStepValue = oldStepValue;
        this.newStepValue = newStepValue;
        this.paramPositions = paramPositions;
    }


    public void visit(MethodDeclaration methodDeclaration, Object arg) {
        try {
            List<AnnotationExpr> annotations = methodDeclaration.getAnnotations();
            if (annotations == null) {
                return;
            }
            for (AnnotationExpr annotationExpr : annotations) {
                if (!(annotationExpr instanceof SingleMemberAnnotationExpr)) {
                    continue;
                }

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
                if (annotation.getMemberValue() instanceof ArrayInitializerExpr) {
                    ArrayInitializerExpr memberValue = (ArrayInitializerExpr) annotation.getMemberValue();
                    if (memberValue.getValues().size() == 1) {
                        StringLiteralExpr expression = (StringLiteralExpr) memberValue.getValues().get(0);
                        refactor(methodDeclaration, expression, annotation);
                    }
                }
            }
        } catch (Exception e) {
            Logger.error("Exception while refactoring", e);
        }
    }

    private void refactor(MethodDeclaration methodDeclaration, StringLiteralExpr memberValue, SingleMemberAnnotationExpr annotation) {
        if (StringEscapeUtils.unescapeJava(memberValue.getValue()).trim().equals(oldStepValue.getStepAnnotationText().trim())) {
            IntStream.range(0, paramPositions.size()).forEach((i) -> newParameters.add(new Parameter()));
            memberValue.setValue(StringEscapeUtils.escapeJava(newStepValue.getStepAnnotationText()));
            List<Parameter> parameters = methodDeclaration.getParameters();
            for (int i = 0, paramPositionsSize = paramPositions.size(); i < paramPositionsSize; i++) {
                if (paramPositions.get(i).getOldPosition() < 0) {
                    String paramName = Util.getValidJavaIdentifier(Util.convertToCamelCase("arg " + newStepValue.getParameters().get(paramPositions.get(i).getNewPosition())));
                    if (paramName.equals("arg")) {
                        paramName += i;
                    }
                    Parameter param = new Parameter(new ClassOrInterfaceType(null, "Object"), new SimpleName(paramName));
                    newParameters.set(paramPositions.get(i).getNewPosition(), param);
                } else {
                    newParameters.set(paramPositions.get(i).getNewPosition(), parameters.get(paramPositions.get(i).getOldPosition()));
                }
            }
            for (int k = 0; k < newParameters.size(); k++) {
                for (int l = k + 1; l < newParameters.size(); l++) {
                    if (newParameters.get(k).getName().equals(newParameters.get(l).getName())) {
                        Parameter param = new Parameter(new ClassOrInterfaceType(null, "Object"), new SimpleName(newParameters.get(l).getName().asString() + l));
                        newParameters.set(l, param);
                    }
                }
            }
            methodDeclaration.setParameters(newParameters);
            annotation.setMemberValue(memberValue);
            this.javaElement = new JavaRefactoringElement(getJavaFileText(methodDeclaration), null);
            Optional<Range> range = annotation.getChildNodes().get(1).getRange();
            range.ifPresent(value -> stepSpan = value);
            this.refactored = true;
        }
    }

    private String getJavaFileText(MethodDeclaration methodDeclaration) {
        return getFileElement(methodDeclaration).toString();
    }

    private Node getFileElement(Node node) {
        if (node instanceof CompilationUnit) {
            return node;
        }
        return node.getParentNode().map(this::getFileElement).orElse(null);
    }

    public boolean refactored() {
        return this.refactored;
    }

    public JavaRefactoringElement getRefactoredJavaElement() {
        return this.javaElement;
    }

    public List<Parameter> getNewParameters() {
        return newParameters;
    }

    public Range getStepLineSpan() {
        return stepSpan;
    }
}
