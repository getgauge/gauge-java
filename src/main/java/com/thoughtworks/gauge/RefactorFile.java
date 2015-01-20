package com.thoughtworks.gauge;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RefactorFile {
    private RefactorRequestProcessor.JavaElement javaElement;
    private List<String> content;

    public RefactorFile(RefactorRequestProcessor.JavaElement javaElement) {
        this.javaElement = javaElement;
    }

    public void refactor() throws IOException {
        readFileContent();
        refactorContent();
        write();
    }

    private void refactorContent() {
        String[] lines = javaElement.text.split("\n");
        String spaces = "";
        for (int j = 0; j < javaElement.beginColumn-1; j++) {
            spaces += " ";
        }
        for (int i = javaElement.beginLine; i <= javaElement.endLine ; i++) {
            content.remove(javaElement.beginLine - 1);
        }
        for (int i = javaElement.beginLine,index = 0; index < lines.length ; i++,index++) {
            content.add(i - 1, spaces + lines[index]);
        }
    }
    private void readFileContent() throws IOException {
        File file = javaElement.file;
        FileReader in = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(in);
        List<String> content = new ArrayList<String>();
        String currentLine = "";
        while ((currentLine = bufferedReader.readLine()) != null) {
            content.add(currentLine);
        }
        this.content = content;
    }
    public void write() throws IOException {
        FileOutputStream stream = new FileOutputStream(javaElement.file, false);
        StringBuilder content = new StringBuilder("");
        for (String line : this.content) {
            content.append(line).append("\n");
        }
        stream.write(content.toString().getBytes());
        stream.close();
    }
}