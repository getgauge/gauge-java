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

import com.github.javaparser.Range;
import gauge.messages.Messages;
import gauge.messages.Spec;

import java.util.ArrayList;

public class RefactoringResult {
    private boolean passed;
    private String errorMessage;
    private String fileChanged;
    private Messages.FileChanges fileChanges;

    public RefactoringResult(boolean passed, String errorMessage) {
        this.passed = passed;
        this.errorMessage = errorMessage;
        this.fileChanged = "";
        this.fileChanges = Messages.FileChanges.newBuilder().build();
    }

    public RefactoringResult(boolean passed, JavaRefactoringElement element) {
        this.passed = passed;
        this.errorMessage = "";
        this.fileChanged = element.getFile().getAbsolutePath();
        this.fileChanges = getFileChanges(element);
    }

    private Messages.FileChanges getFileChanges(JavaRefactoringElement element) {
        ArrayList<Diff> diffs = element.getDiffs();
        Messages.FileChanges.Builder changes = Messages.FileChanges.newBuilder().setFileName(element.getFile().getAbsolutePath());
        for (Diff diff : diffs) {
            Range range = diff.getRange();
            String text = diff.getText();
            Spec.Span span = Spec.Span.newBuilder()
                    .setStart(range.begin.line)
                    .setStartChar(range.begin.column)
                    .setEnd(range.end.line)
                    .setEndChar(range.end.column).build();
            Messages.TextDiff textDiff = Messages.TextDiff.newBuilder().setContent(text).setSpan(span).build();
            changes.addDiffs(textDiff);
        }
        return changes.build();
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

    public Messages.FileChanges fileChanges() {
        return this.fileChanges;
    }
}
