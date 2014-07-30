package com.thoughtworks.gauge;

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

}
