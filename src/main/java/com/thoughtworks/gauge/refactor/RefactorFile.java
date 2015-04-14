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

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RefactorFile {
    private JavaRefactoringElement javaElement;
    private List<String> content;

    public RefactorFile(JavaRefactoringElement javaElement) {
        this.javaElement = javaElement;
    }

    public void refactor() throws IOException {
        readFileContent();
        refactorContent();
        write();
    }

    private void refactorContent() {
        String[] lines = javaElement.getText().split("\n");
        String spaces = "";
        for (int j = 0; j < javaElement.getBeginColumn()-1; j++) {
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
            content.append(line).append("\n");
        }
        stream.write(content.toString().getBytes());
        stream.close();
    }
}