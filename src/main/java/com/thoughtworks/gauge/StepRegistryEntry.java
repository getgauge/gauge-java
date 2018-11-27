package com.thoughtworks.gauge;

import com.github.javaparser.Range;
import com.github.javaparser.ast.TypeParameter;

import java.lang.reflect.Method;
import java.util.List;

public class StepRegistryEntry {
    private StepValue stepValue;
    private String className;
    private String fileName;
    private Range span;
    private Boolean hasAlias;
    private List<String> aliases;
    private Method methodInfo;
    private String stepText;
    private String name;
    private Boolean continueOnFailure;
    private List<TypeParameter> parameters;

    public StepRegistryEntry(StepValue stepValue, Method method) {
        this.stepValue = stepValue;
        this.methodInfo = method;
    }

    public StepRegistryEntry() {

    }


    public void setStepValue(StepValue stepValue) {
        this.stepValue = stepValue;
    }

    public void setClassName(String className) {
        this.className = className;
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

    public void setMethodInfo(Method methodInfo) {
        this.methodInfo = methodInfo;
    }

    public void setStepText(String stepText) {
        this.stepText = stepText;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setContinueOnFailure(Boolean continueOnFailure) {
        this.continueOnFailure = continueOnFailure;
    }

    public String getFileName() {
        return fileName;
    }

    public Range getSpan() {
        return span;
    }

    public String getClassName() {
        return className;
    }

    public void setParameters(List<TypeParameter> parameters) {
        this.parameters = parameters;
    }

    public List<TypeParameter> getParameters() {
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
