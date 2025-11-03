/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge;

import gauge.messages.Spec;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StepValue {
    private final String stepText;
    private final String parameterizedStepText;
    private final List<String> parameters;

    public StepValue(String stepText, String parameterizedStepText, List<String> parameters) {
        this.stepText = stepText;
        this.parameterizedStepText = parameterizedStepText;
        this.parameters = parameters;
    }

    public StepValue(String stepTemplateText, String parameterizedStepText) {
        this.stepText = stepTemplateText;
        this.parameterizedStepText = parameterizedStepText;
        this.parameters = new ArrayList<>();
    }

    public static StepValue from(Spec.ProtoStepValue protoStepValue) {
        return new StepValue(protoStepValue.getStepValue(), protoStepValue.getParameterizedStepValue(), protoStepValue.getParametersList());
    }

    public String getStepText() {
        return stepText;
    }

    public List<String> getParameters() {
        return parameters;
    }

    public String getStepAnnotationText() {
        return parameterizedStepText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof StepValue stepValue)) {
            return false;
        }

        return Objects.equals(parameterizedStepText, stepValue.parameterizedStepText)
                && Objects.equals(parameters, stepValue.parameters)
                && Objects.equals(stepText, stepValue.stepText);
    }

    @Override
    public int hashCode() {
        int result = stepText != null ? stepText.hashCode() : 0;
        result = 31 * result + (parameterizedStepText != null ? parameterizedStepText.hashCode() : 0); // SUPPRESS CHECKSTYLE
        result = 31 * result + (parameters != null ? parameters.hashCode() : 0); // SUPPRESS CHECKSTYLE
        return result;
    }

    @Override
    public String toString() {
        return "StepValue{"
                + "stepText='" + stepText + '\''
                + ", parameterizedStepText='" + parameterizedStepText + '\''
                + ", parameters=" + parameters
                + '}';
    }
}
