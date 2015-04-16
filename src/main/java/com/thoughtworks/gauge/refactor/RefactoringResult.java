// Copyright 2015 ThoughtWorks, Inc.

// This file is part of Gauge-Java.

// Gauge-Java is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.

// Gauge-Java is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.

// You should have received a copy of the GNU General Public License
// along with Gauge-Java.  If not, see <http://www.gnu.org/licenses/>.

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
