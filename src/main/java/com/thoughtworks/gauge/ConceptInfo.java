package com.thoughtworks.gauge;

public class ConceptInfo {

    private final StepValue stepValue;
    private final String filePath;
    private final Integer lineNumber;

    public ConceptInfo(StepValue stepValue, String filePath, Integer lineNumber) {
        this.filePath = filePath;
        this.lineNumber = lineNumber;
        this.stepValue = stepValue;
    }


    public String getFilePath() {
        return filePath;
    }

    public Integer getLineNumber() {
        return lineNumber;
    }

    public StepValue getStepValue() {
        return stepValue;
    }
}
