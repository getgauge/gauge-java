package com.thoughtworks.gauge.refactor;

import junit.framework.TestCase;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.apache.commons.io.FileUtils.copyFile;

public class FileModifierTest extends TestCase {

    private File file;

    protected void setUp() throws Exception {
        this.file = createFile();
    }

    private File createFile() throws IOException {
        File file = new File("src/test/resources/StepImpl.java");
        File destFile = new File(FileUtils.getTempDirectory(), file.getName());
        copyFile(file, destFile);
        return destFile;
    }

    public void testRefactorFileChange() throws Exception {
        String text = "New File \n Content \n in 3 lines";
        new FileModifier(new JavaRefactoringElement(3, 5, 0, text, file)).refactor();
        assertEquals(text, readFileLines(file, 3, 5));

        text = "@Step(\"step <abcd> and a table <table>\")\n" +
                "public void stepWithTable(float abcd, Table table) {\n" +
                "}";
        new FileModifier(new JavaRefactoringElement(13, 15, 5, text, file)).refactor();
        assertEquals("     @Step(\"step <abcd> and a table <table>\")\n" +
                "     public void stepWithTable(float abcd, Table table) {\n" +
                "     }", readFileLines(file, 13, 15));
    }

    private String readFileLines(File file, int startLine, int endLine) throws IOException {
        List<String> lines = FileUtils.readLines(file);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < lines.size(); i++) {
            int index = i + 1;
            if (index > startLine && index < endLine) {
                builder.append(lines.get(i)).append("\n");
            } else if (index == startLine) {
                builder.append(lines.get(i));
                if (endLine > startLine) builder.append("\n");
            } else if (index == endLine) {
                builder.append(lines.get(i));
            }
        }
        return builder.toString();
    }

    protected void tearDown() throws Exception {
        if (file != null && file.exists()) {
            file.delete();
        }
    }

}