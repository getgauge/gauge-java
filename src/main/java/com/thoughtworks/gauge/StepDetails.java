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
 * Holds the information about the current Step executing at Runtime
 */
public class StepDetails {
    private String text = "";
    private Boolean isFailing = false;

    public StepDetails(String text, boolean isFailing) {
        this.text = text;
        this.isFailing = isFailing;
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

}
