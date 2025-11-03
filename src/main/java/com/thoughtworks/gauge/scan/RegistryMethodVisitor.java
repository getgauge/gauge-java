/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge.scan;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.thoughtworks.gauge.StepRegistryEntry;
import com.thoughtworks.gauge.StepValue;
import com.thoughtworks.gauge.Util;
import com.thoughtworks.gauge.registry.StepRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class RegistryMethodVisitor extends VoidVisitorAdapter<Void> {

    private final StepRegistry stepRegistry;
    private final String file;

    public RegistryMethodVisitor(StepRegistry stepRegistry, String file) {
        this.stepRegistry = stepRegistry;
        this.file = file;
    }

    @Override
    public void visit(MethodDeclaration methodDeclaration, Void ignored) {
        List<AnnotationExpr> annotations = methodDeclaration.getAnnotations();
        if (annotations.isEmpty()) {
            return;
        }

        for (AnnotationExpr annotationExpr : annotations) {
            if (!(annotationExpr instanceof SingleMemberAnnotationExpr annotation)) {
                continue;
            }
            if (annotation.getMemberValue() instanceof ArrayInitializerExpr memberValue) {
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
        StepValue stepValue = new StepValue(stepText, parameterizedStep);

        StepRegistryEntry entry = new StepRegistryEntry();
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

    @SuppressWarnings("unchecked")
    private String getClassName(MethodDeclaration methodDeclaration) {
        AtomicReference<String> className = new AtomicReference<>();
        methodDeclaration.findAncestor(ClassOrInterfaceDeclaration.class)
                .ifPresent(c -> className.set(c.getNameAsString()));
        String classNameStr = className.get() == null ? null : className.get();
        if (classNameStr == null) {
            return null;
        }

        AtomicReference<Name> packageName = new AtomicReference<>();
        methodDeclaration.findCompilationUnit().flatMap(CompilationUnit::getPackageDeclaration).ifPresent(p -> packageName.set(p.getName()));
        String packageNameStr = packageName.get() == null ? null : packageName.get().asString();

        if (packageNameStr == null) {
            return classNameStr;
        }
        return packageNameStr + "." + classNameStr;
    }

    private String getParameterizedStep(Expression expression) {
        if (expression instanceof BinaryExpr expr) {
            return Util.trimQuotes(expr.getLeft().toString()) + Util.trimQuotes(expr.getRight().toString());
        }
        return Util.trimQuotes(expression.toString());
    }

    private Boolean hasAlias(SingleMemberAnnotationExpr annotation) {
        return annotation.getMemberValue() instanceof ArrayInitializerExpr;
    }

    private List<String> getAliases(SingleMemberAnnotationExpr annotation) {
        List<String> aliases = new ArrayList<>();
        if (annotation.getMemberValue() instanceof ArrayInitializerExpr memberValue) {
            for (Expression expression : memberValue.getValues()) {
                aliases.add(getParameterizedStep(expression));
            }
        }
        return aliases;
    }
}
