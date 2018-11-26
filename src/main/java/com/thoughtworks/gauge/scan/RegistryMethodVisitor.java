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
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.thoughtworks.gauge.StepRegistryEntry;
import com.thoughtworks.gauge.StepValue;
import com.thoughtworks.gauge.registry.StepRegistry;

import java.util.List;
import java.util.stream.Collectors;

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
        if (!annotations.isEmpty()) {
            if (hasAlias(annotations)) {
                List<String> allAliasAnnotations = getAllAliasAnnotations(annotations);
                for (String parameterizedStepText : allAliasAnnotations) {
                    addStepToRegistry(parameterizedStepText, methodDeclaration);
                }
            } else {
                String parameterizedStepText = getStepName(annotations.get(0));
                addStepToRegistry(parameterizedStepText, methodDeclaration);

            }
        }
    }

    private void addStepToRegistry(String parameterizedStepText, MethodDeclaration methodDeclaration) {
        List<AnnotationExpr> annotations = methodDeclaration.getAnnotations();
        String stepText = getStepText(parameterizedStepText);
        stepValue = new StepValue(stepText, parameterizedStepText);

        entry = new StepRegistryEntry();
        entry.setName(methodDeclaration.getDeclarationAsString());
        entry.setStepText(stepText);
        entry.setStepValue(stepValue);
        entry.setParameters(methodDeclaration.getTypeParameters());
        entry.setSpan(methodDeclaration.getRange());
        entry.setHasAlias(hasAlias(annotations));
        entry.setAliases(getAllAliasAnnotations(annotations));
        entry.setFileName(file);

        stepRegistry.addStep(stepValue, entry);
    }

    private String getStepText(String parameterizedStepText) {
        return parameterizedStepText
                .replaceAll("(<.*?>)", "{}")
                .replaceAll("\"", "");
    }

    private Boolean hasAlias(List<AnnotationExpr> annotations) {
        return annotations.get(0).getChildrenNodes().get(1).getChildrenNodes().size() > 0;
    }

    private List<String> getAllAliasAnnotations(List<AnnotationExpr> annotations) {
        return annotations.get(0).getChildrenNodes().get(1).getChildrenNodes().stream().map(node -> node.toString().replace("\"", "")).collect(Collectors.toList());
    }



    private String getStepName(AnnotationExpr expr) {
        return expr.getChildrenNodes().get(1).toString();
    }

}
