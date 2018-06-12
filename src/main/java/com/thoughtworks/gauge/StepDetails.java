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
