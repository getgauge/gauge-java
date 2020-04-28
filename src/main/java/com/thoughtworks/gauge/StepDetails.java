/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge;

/**
 * Holds the information about the current step executing at runtime.
 */
public class StepDetails {
    private String text = "";
    private String dynamicText = "";
    private String stackTrace = "";
    private String errorMessage = "";
    private Boolean isFailing = false;

    public StepDetails(String text, boolean isFailing, String stackTrace, String errorMessage) {
        this(text, text, isFailing, stackTrace, errorMessage);
    }

    public StepDetails(String text, String dynamicText, boolean isFailing, String stackTrace, String errorMessage) {
        this.text = text;
        this.dynamicText = dynamicText;
        this.isFailing = isFailing;
        this.stackTrace = stackTrace;
        this.errorMessage = errorMessage;
    }

    public StepDetails() {
    }

    /**
     * @return True if the current spec or scenario or step is failing due to error.
     */
    public Boolean getIsFailing() {
        return isFailing;
    }

    /**
     * @return The name of the step as given in the spec file.
     */
    public String getText() {
        return text;
    }

    /**
     * @return The step text with values of dynamic parameters instead of placeholders.
     * Return the same value as {@link #text} in case of static parameters.
     */
    public String getDynamicText() {
        return dynamicText;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
