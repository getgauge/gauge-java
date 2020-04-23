/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge;

import com.github.javaparser.Range;
import com.github.javaparser.ast.body.Parameter;

import java.lang.reflect.Method;
import java.util.List;

public class StepRegistryEntry {
    private StepValue stepValue;
    private String fileName;
    private Range span;
    private Boolean hasAlias;
    private List<String> aliases;
    private Method methodInfo;
    private String stepText;
    private String name;
    private List<Parameter> parameters;

    public StepRegistryEntry(StepValue stepValue, Method method) {
        this.stepValue = stepValue;
        this.methodInfo = method;
    }

    public StepRegistryEntry() {
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

    public void setHasAlias(Boolean hasAlias) {
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

    public Boolean getHasAlias() {
        return hasAlias;
    }

    public String getName() {
        return name;
    }
}
