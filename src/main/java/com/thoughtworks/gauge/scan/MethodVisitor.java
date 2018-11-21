package com.thoughtworks.gauge.scan;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.thoughtworks.gauge.StepRegistryEntry;
import com.thoughtworks.gauge.StepValue;
import com.thoughtworks.gauge.registry.StepRegistry;

import java.util.List;
import java.util.stream.Collectors;

public class MethodVisitor extends VoidVisitorAdapter {


    private StepValue stepValue;
    private StepRegistryEntry entry;
    private StepRegistry stepRegistry;
    private String file;

    public MethodVisitor(StepRegistry stepRegistry, String file) {
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
        String stepText = parameterizedStepText
                .replaceAll("(<.*?>)", "{}")
                .replaceAll("\"", "");
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
