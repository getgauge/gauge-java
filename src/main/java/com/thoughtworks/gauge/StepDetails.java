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

public class StepDetails {
    private String text= "";
    private Boolean isFailing = false;

    public StepDetails(String text, boolean isFailing) {
        this.text = text;
        this.isFailing = isFailing;
    }

    public StepDetails() {
    }

    public Boolean getIsFailing() {
        return isFailing;
    }
    public String getText() {
        return text;
    }

}
