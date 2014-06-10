package com.thoughtworks.gauge;

public class StepDetails {
    private String text;
    private Boolean isFailing;

    public StepDetails(String text, boolean isFailing) {
        this.text = text;
        this.isFailing = isFailing;
    }

    public Boolean getIsFailing() {
        return isFailing;
    }
    public String getText() {
        return text;
    }

}