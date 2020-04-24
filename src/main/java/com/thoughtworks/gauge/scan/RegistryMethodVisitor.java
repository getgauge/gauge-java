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

package com.thoughtworks.gauge.scan;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.ArrayInitializerExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.Name;
import com.github.javaparser.ast.expr.SingleMemberAnnotationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.thoughtworks.gauge.StepRegistryEntry;
import com.thoughtworks.gauge.StepValue;
import com.thoughtworks.gauge.Util;
import com.thoughtworks.gauge.registry.StepRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

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
        String className = getClassName(methodDeclaration);
        String fullyQualifiedName = className == null
                ? methodDeclaration.getNameAsString() : className + "." + methodDeclaration.getNameAsString();
        entry.setFullyQualifiedName(fullyQualifiedName);
        entry.setStepText(parameterizedStep);
        entry.setStepValue(stepValue);
        entry.setParameters(methodDeclaration.getParameters());
        entry.setSpan(methodDeclaration.getRange().get());
        entry.setHasAlias(hasAlias(annotation));
        entry.setAliases(getAliases(annotation));
        entry.setFileName(file);

        stepRegistry.addStep(stepValue, entry);
    }

    private String getClassName(MethodDeclaration methodDeclaration) {
        AtomicReference<String> className = new AtomicReference<>();
        methodDeclaration.findAncestor(com.github.javaparser.ast.body.ClassOrInterfaceDeclaration.class)
                .ifPresent(c -> className.set(c.getNameAsString()));
        String classNameStr = className.get() == null ? null : className.get();
        if (classNameStr == null) {
            return null;
        }

        AtomicReference<Name> packageName = new AtomicReference<>();
        methodDeclaration.findCompilationUnit()
                .ifPresent(c -> c.getPackageDeclaration()
                        .ifPresent(p -> packageName.set(p.getName()))
                );
        String packageNameStr = packageName.get() == null ? null : packageName.get().asString();

        if (packageNameStr == null) {
            return classNameStr;
        }
        return packageNameStr + "." + classNameStr;
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
