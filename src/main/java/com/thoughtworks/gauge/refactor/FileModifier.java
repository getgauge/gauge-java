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

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileModifier {
    private JavaRefactoringElement javaElement;
    private List<String> content;

    public FileModifier(JavaRefactoringElement javaElement) {
        this.javaElement = javaElement;
    }

    public void refactor() throws IOException {
        readFileContent();
        refactorContent();
        write();
    }

    private void refactorContent() {
        String[] lines = javaElement.getText().split("\\r?\\n");
        String spaces = "";
        for (int j = 0; j < javaElement.getIndentation(); j++) {
            spaces += " ";
        }
        for (int i = javaElement.getBeginLine(); i <= javaElement.getEndLine() ; i++) {
            content.remove(javaElement.getBeginLine() - 1);
        }
        for (int i = javaElement.getBeginLine(),index = 0; index < lines.length ; i++,index++) {
            content.add(i - 1, spaces + lines[index]);
        }
    }

    private void readFileContent() throws IOException {
        File file = javaElement.getFile();
        FileReader in = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(in);
        List<String> content = new ArrayList<String>();
        String currentLine = "";
        while ((currentLine = bufferedReader.readLine()) != null) {
            content.add(currentLine);
        }
        this.content = content;
    }

    private void write() throws IOException {
        FileOutputStream stream = new FileOutputStream(javaElement.getFile(), false);
        StringBuilder content = new StringBuilder("");
        for (String line : this.content) {
            content.append(line).append(Util.lineSeparator());
        }
        stream.write(content.toString().getBytes());
        stream.close();
    }

}
