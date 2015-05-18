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
