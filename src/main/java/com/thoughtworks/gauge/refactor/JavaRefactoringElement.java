package com.thoughtworks.gauge.refactor;

import java.io.File;

public class JavaRefactoringElement {
    private int beginLine;
    private int endLine;
    private int beginColumn;
    private int endColumn;
    private String text;
    private File file;

    public JavaRefactoringElement(int beginLine, int endLine, int beginColumn, int endColumn, String text, File file) {
        this.beginLine = beginLine;
        this.endLine = endLine;
        this.beginColumn = beginColumn;
        this.endColumn = endColumn;
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

    public int getBeginColumn() {
        return beginColumn;
    }

    public int getEndColumn() {
        return endColumn;
    }

    public String getText() {
        return text;
    }
}
