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

import java.io.File;

public class JavaRefactoringElement {
    private int beginLine;
    private int endLine;
    private int indentation;
    private String text;
    private File file;

    public JavaRefactoringElement(int beginLine, int endLine, int beginColumn, String text, File file) {
        this.beginLine = beginLine;
        this.endLine = endLine;
        this.indentation = beginColumn;
        this.text = text;
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public int getBeginLine() {
        return beginLine;
    }

    public int getEndLine() {
        return endLine;
    }

    public int getIndentation() {
        return indentation;
    }

    public String getText() {
        return text;
    }
}
