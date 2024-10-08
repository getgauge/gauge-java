/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge;

import com.github.javaparser.Range;
import com.github.javaparser.ast.body.Parameter;
import com.thoughtworks.gauge.scan.StepsUtil;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StepRegistryEntry {
    private final StepsUtil stepsUtil;
    private StepValue stepValue;
    private String fileName;
    private Range span;
    private boolean hasAlias;
    private List<String> aliases;
    private Method methodInfo;
    private String stepText;
    private String name;
    private List<Parameter> parameters;
    private boolean isExternal;
    private String fullyQualifiedName;

    public StepRegistryEntry(StepValue stepValue, Method method, boolean isExternal) {
        this();
        this.stepValue = stepValue;
        this.methodInfo = method;
        this.isExternal = isExternal;
        this.fullyQualifiedName = method.getDeclaringClass().getName() + "." + method.getName();
        String[] stepTexts = method.getAnnotation(Step.class).value();
        this.hasAlias = stepTexts.length > 1;
        this.aliases = this.hasAlias
                ? Arrays.stream(stepTexts).skip(1).map(this::getStepText).collect(Collectors.toList()) : new ArrayList<>();
    }

    public StepRegistryEntry() {
        this.stepsUtil = new StepsUtil();
    }

    public void setStepValue(StepValue stepValue) {
        this.stepValue = stepValue;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setSpan(Range span) {
        this.span = span;
    }

    public void setHasAlias(boolean hasAlias) {
        this.hasAlias = hasAlias;
    }

    public void setAliases(List<String> aliases) {
        this.aliases = aliases;
    }

    public void setStepText(String stepText) {
        this.stepText = stepText;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMethodInfo(Method method) {
        this.methodInfo = method;
    }

    public boolean getIsExternal() {
        return isExternal;
    }

    public String getFileName() {
        return fileName;
    }

    public Range getSpan() {
        return span;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public Method getMethodInfo() {
        return methodInfo;
    }

    public StepValue getStepValue() {
        return stepValue;
    }

    public List<String> getAliases() {
        return aliases;
    }

    public String getStepText() {
        return stepText;
    }

    public boolean getHasAlias() {
        return hasAlias;
    }

    public String getName() {
        return name;
    }

    public void setFullyQualifiedName(String fullyQualifiedName) {
        this.fullyQualifiedName = fullyQualifiedName;
    }

    public String getFullyQualifiedName() {
        return fullyQualifiedName;
    }

    private String getStepText(String stepAnnotationText) {
        return stepsUtil.getStepText(Util.trimQuotes(stepAnnotationText));
    }
}
