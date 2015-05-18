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

package com.thoughtworks.gauge.refactor;

public class RefactoringResult {
    private boolean passed;
    private String errorMessage;
    private String fileChanged;

    public RefactoringResult(boolean passed, String errorMessage) {
        this.passed = passed;
        this.errorMessage = errorMessage;
        this.fileChanged = "";
    }

    public RefactoringResult(boolean passed, String errorMessage, String fileChanged) {
        this.passed = passed;
        this.errorMessage = errorMessage;
        this.fileChanged = fileChanged;
    }

    public boolean passed() {
        return this.passed;
    }

    public String errorMessage() {
        return this.errorMessage;
    }

    public String fileChanged() {
        return this.fileChanged;
    }
}
