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

package com.thoughtworks.gauge;

import gauge.messages.Spec;

import java.util.ArrayList;
import java.util.List;

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
        this.parameters = new ArrayList<String>();
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

    public int getParamCount() {
        return parameters.size();
    }

    public String getStepAnnotationText() {
        return parameterizedStepText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof StepValue)) {
            return false;
        }

        StepValue stepValue = (StepValue) o;
        return !(parameterizedStepText != null ? !parameterizedStepText.equals(stepValue.parameterizedStepText) : stepValue.parameterizedStepText != null)
                && !(parameters != null ? !parameters.equals(stepValue.parameters) : stepValue.parameters != null)
                && !(stepText != null ? !stepText.equals(stepValue.stepText) : stepValue.stepText != null);
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
