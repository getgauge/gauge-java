/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge.scan;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.ArrayInitializerExpr;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.SingleMemberAnnotationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.thoughtworks.gauge.StepRegistryEntry;
import com.thoughtworks.gauge.StepValue;
import com.thoughtworks.gauge.Util;
import com.thoughtworks.gauge.registry.StepRegistry;

import java.util.ArrayList;
import java.util.List;

public class RegistryMethodVisitor extends VoidVisitorAdapter {

    private StepValue stepValue;
    private StepRegistryEntry entry;
    private StepRegistry stepRegistry;
    private String file;

    public RegistryMethodVisitor(StepRegistry stepRegistry, String file) {
        this.stepRegistry = stepRegistry;
        this.file = file;
    }

    @Override
    public void visit(MethodDeclaration methodDeclaration, Object arg) {
        List<AnnotationExpr> annotations = methodDeclaration.getAnnotations();
        if (annotations.isEmpty()) {
            return;
        }

        for (AnnotationExpr annotationExpr : annotations) {
            if (!(annotationExpr instanceof SingleMemberAnnotationExpr)) {
                continue;
            }
            SingleMemberAnnotationExpr annotation = (SingleMemberAnnotationExpr) annotationExpr;
            if (annotation.getMemberValue() instanceof ArrayInitializerExpr) {
                ArrayInitializerExpr memberValue = (ArrayInitializerExpr) annotation.getMemberValue();
                for (Expression expression : memberValue.getValues()) {
                    addStepToRegistry(expression, methodDeclaration, annotation);
                }
            } else {
                addStepToRegistry(annotation.getMemberValue(), methodDeclaration, annotation);
            }
        }
    }

    private void addStepToRegistry(Expression expression, MethodDeclaration methodDeclaration, SingleMemberAnnotationExpr annotation) {
        String parameterizedStep = getParameterizedStep(expression);
        String stepText = new StepsUtil().getStepText(parameterizedStep);
        stepValue = new StepValue(stepText, parameterizedStep);

        entry = new StepRegistryEntry();
        entry.setName(methodDeclaration.getDeclarationAsString());
        entry.setStepText(parameterizedStep);
        entry.setStepValue(stepValue);
        entry.setParameters(methodDeclaration.getParameters());
        entry.setSpan(methodDeclaration.getRange().get());
        entry.setHasAlias(hasAlias(annotation));
        entry.setAliases(getAliases(annotation));
        entry.setFileName(file);

        stepRegistry.addStep(stepValue, entry);
    }

    private String getParameterizedStep(Expression expression) {
        if (expression instanceof BinaryExpr) {
            return Util.trimQuotes(((BinaryExpr) expression).getLeft().toString()) + Util.trimQuotes(((BinaryExpr) expression).getRight().toString());
        }
        return Util.trimQuotes(expression.toString());
    }

    private Boolean hasAlias(SingleMemberAnnotationExpr annotation) {
        return annotation.getMemberValue() instanceof ArrayInitializerExpr;
    }

    private List<String> getAliases(SingleMemberAnnotationExpr annotation) {
        List<String> aliases = new ArrayList<>();
        if (annotation.getMemberValue() instanceof ArrayInitializerExpr) {
            ArrayInitializerExpr memberValue = (ArrayInitializerExpr) annotation.getMemberValue();
            for (Expression expression : memberValue.getValues()) {
                aliases.add(getParameterizedStep(expression));
            }
        }
        return aliases;
    }
}
