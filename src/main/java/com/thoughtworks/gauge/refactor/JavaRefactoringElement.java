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

    public void setFile(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
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
