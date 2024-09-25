/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge.refactor;

import com.github.javaparser.Range;
import gauge.messages.Messages;
import gauge.messages.Spec;

import java.util.ArrayList;

public class RefactoringResult {
    private final boolean passed;
    private final String errorMessage;
    private final String fileChanged;
    private final Messages.FileChanges fileChanges;

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
