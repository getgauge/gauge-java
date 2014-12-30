package com.thoughtworks.gauge;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RefactorFile {
    private RefactorRequestProcessor.Element element;
    private List<String> content;

    public RefactorFile(RefactorRequestProcessor.Element element) {
        this.element = element;
    }

    public void refactor() throws IOException {
        readFileContent();
        refactorContent();
        write();
    }

    private void refactorContent() {
        String[] lines = element.text.split("\n");
        String spaces = "";
        for (int j = 0; j < element.beginColumn-1; j++) {
            spaces += " ";
        }
        for (int i = element.beginLine; i <= element.endLine ; i++) {
            content.remove(element.beginLine - 1);
        }
        for (int i = element.beginLine,index = 0; index < lines.length ; i++,index++) {
            content.add(i - 1, spaces + lines[index]);
        }
    }
    private void readFileContent() throws IOException {
        File file = element.file;
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
        FileOutputStream stream = new FileOutputStream(element.file, false);
        StringBuilder content = new StringBuilder("");
        for (String line : this.content) {
            content.append(line).append("\n");
        }
        stream.write(content.toString().getBytes());
        stream.close();
    }
}