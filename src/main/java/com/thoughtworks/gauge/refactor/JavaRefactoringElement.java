/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge.refactor;

import java.io.File;
import java.util.ArrayList;

public class JavaRefactoringElement {
    private final ArrayList<Diff> diffs;
    private String text;
    private File file;

    public JavaRefactoringElement(String text, File file) {
        this.text = text;
        this.file = file;
        this.diffs = new ArrayList<>();
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getText() {
        return text;
    }

    public ArrayList<Diff> getDiffs() {
        return diffs;
    }

    public void addDiffs(Diff diff) {
        this.diffs.add(diff);
    }

}
