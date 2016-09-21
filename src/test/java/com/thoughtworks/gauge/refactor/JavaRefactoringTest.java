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

import com.thoughtworks.gauge.StepValue;
import com.thoughtworks.gauge.registry.StepRegistry;
import gauge.messages.Messages;
import junit.framework.TestCase;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import static org.mockito.Mockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(StepRegistry.class)
public class JavaRefactoringTest extends TestCase {

    public void testRefactoringWithAlias() throws Exception {
        mockStatic(StepRegistry.class);
        when(StepRegistry.hasAlias("old step")).thenReturn(true);
        when(StepRegistry.getFileName("old step")).thenReturn("foo");

        StepValue oldStepValue = new StepValue("old step", "", new ArrayList<String>());
        StepValue newStepValue = new StepValue("", "", new ArrayList<String>());
        RefactoringResult result = new JavaRefactoring(oldStepValue, newStepValue, new ArrayList<Messages.ParameterPosition>()).performRefactoring();
        assertEquals(false, result.passed());
        assertEquals("Refactoring for steps having aliases are not supported.", result.errorMessage());
    }

    public void testRefactoringWithDuplicateImplementations() throws Exception {
        HashSet<Method> set = mock(HashSet.class);
        mockStatic(StepRegistry.class);
        when(StepRegistry.getAll("old step")).thenReturn(set);
        when(set.size()).thenReturn(2);
        when(StepRegistry.getFileName("old step")).thenReturn("foo");

        StepValue oldStepValue = new StepValue("old step", "", new ArrayList<String>());
        StepValue newStepValue = new StepValue("", "", new ArrayList<String>());
        RefactoringResult result = new JavaRefactoring(oldStepValue, newStepValue, new ArrayList<Messages.ParameterPosition>()).performRefactoring();
        assertEquals(false, result.passed());
        assertEquals("Duplicate step implementation found.", result.errorMessage());
    }

    public void testJavaElementForSimpleRefactoring() throws Exception {
        StepValue oldStepValue = new StepValue("A step with no params", "A step with no params", new ArrayList<String>());
        StepValue newStepValue = new StepValue("step changed", "step changed", new ArrayList<String>());
        String implFile = String.format("test%sfiles%sformatted%sStepImpl.java", File.separator, File.separator, File.separator);

        JavaRefactoring refactoring = new JavaRefactoring(oldStepValue, newStepValue, new ArrayList<Messages.ParameterPosition>());
        JavaRefactoringElement element = refactoring.createJavaRefactoringElement(implFile);

        assertEquals(getImplFile(implFile).getName(), element.getFile().getName());
        assertTrue(element.getText().contains("    @Step(\"step changed\")" + System.getProperty("line.separator") +
                "    public void someStepStep() {" + System.getProperty("line.separator") +
                "    }"));
        assertFalse(element.getText().contains("A step with no params"));

    }

    public void testJavaElementForRefactoringWithNewParameter() throws Exception {
        StepValue oldStepValue = new StepValue("A step with no params", "A step with no params", new ArrayList<String>());
        StepValue newStepValue = new StepValue("step with {}", "step with <param 1>", Arrays.asList("param 1"));
        String implFile = String.format("test%sfiles%sformatted%sStepImpl.java", File.separator, File.separator, File.separator);

        Messages.ParameterPosition parameterPosition = Messages.ParameterPosition.newBuilder().setOldPosition(-1).setNewPosition(0).build();
        ArrayList<Messages.ParameterPosition> parameterPositions = new ArrayList<Messages.ParameterPosition>();
        parameterPositions.add(parameterPosition);
        JavaRefactoring refactoring = new JavaRefactoring(oldStepValue, newStepValue, parameterPositions);
        JavaRefactoringElement element = refactoring.createJavaRefactoringElement(implFile);

        assertEquals(getImplFile(implFile).getName(), element.getFile().getName());
        assertTrue(element.getText().contains("    @Step(\"step with <param 1>\")" + System.getProperty("line.separator") +
                "    public void someStepStep(String argParam1) {" + System.getProperty("line.separator") +
                "    }"));
        assertFalse(element.getText().contains("A step with no params"));
    }

    public void testJavaElementForRefactoringWithNewParameterWhenParametersPresent() throws Exception {
        StepValue oldStepValue = new StepValue("Tell {} to {}", "Tell <greeting> to <name>", Arrays.asList("greeting", "name"));
        StepValue newStepValue = new StepValue("Tell {} to {} {}", "Tell <greeting> to <name> <DD>", Arrays.asList("greeting", "name", "DD"));
        String implFile = String.format("test%sfiles%sformatted%sStepImpl.java", File.separator, File.separator, File.separator);

        Messages.ParameterPosition parameterPosition1 = Messages.ParameterPosition.newBuilder().setOldPosition(0).setNewPosition(0).build();
        Messages.ParameterPosition parameterPosition2 = Messages.ParameterPosition.newBuilder().setOldPosition(1).setNewPosition(1).build();
        Messages.ParameterPosition parameterPosition3 = Messages.ParameterPosition.newBuilder().setOldPosition(-1).setNewPosition(2).build();
        ArrayList<Messages.ParameterPosition> parameterPositions = new ArrayList<Messages.ParameterPosition>();
        parameterPositions.add(parameterPosition1);
        parameterPositions.add(parameterPosition2);
        parameterPositions.add(parameterPosition3);

        JavaRefactoring refactoring = new JavaRefactoring(oldStepValue, newStepValue, parameterPositions);
        JavaRefactoringElement element = refactoring.createJavaRefactoringElement(implFile);
        assertEquals(getImplFile(implFile).getName(), element.getFile().getName());
        assertTrue(element.getText().contains("    @Step(\"Tell <greeting> to <name> <DD>\")" + System.getProperty("line.separator") +
                "    public void helloWorld(String greeting, String name, String argDd) {" + System.getProperty("line.separator") +
                "        System.out.println(greeting + \", \" + name);" + System.getProperty("line.separator") +
                "    }"));
        assertFalse(element.getText().contains("\"Tell <greeting> to <name>\""));

    }

    private File getImplFile(String fileName) {
        return new File(String.format("src%stest%sresources", File.separator, File.separator), fileName);
    }

    public void testJavaElementForRefactoringWithParametersRemoved() throws Exception {
        StepValue oldStepValue = new StepValue("step {} and a table {}", "step <a> and a table <table>", new ArrayList<String>());
        StepValue newStepValue = new StepValue("{} changed {} and added {}", "<table> changed <c> and added <a>", Arrays.asList("b", "a", "c"));
        String implFile = String.format("test%sfiles%sformatted%sStepImpl.java", File.separator, File.separator, File.separator);

        Messages.ParameterPosition firstParameterPosition = Messages.ParameterPosition.newBuilder().setOldPosition(0).setNewPosition(2).build();
        Messages.ParameterPosition secondParameterPosition = Messages.ParameterPosition.newBuilder().setOldPosition(1).setNewPosition(0).build();
        Messages.ParameterPosition thirdParameterPosition = Messages.ParameterPosition.newBuilder().setOldPosition(-1).setNewPosition(1).build();
        ArrayList<Messages.ParameterPosition> parameterPositions = new ArrayList<Messages.ParameterPosition>();
        parameterPositions.add(firstParameterPosition);
        parameterPositions.add(secondParameterPosition);
        parameterPositions.add(thirdParameterPosition);


        JavaRefactoring refactoring = new JavaRefactoring(oldStepValue, newStepValue, parameterPositions);
        JavaRefactoringElement element = refactoring.createJavaRefactoringElement(implFile);

        assertEquals(getImplFile(implFile).getName(), element.getFile().getName());
        assertTrue(element.getText().contains("    @Step(\"<table> changed <c> and added <a>\")" + System.getProperty("line.separator") +
                "    public void stepWithTable(Table table, String argC, float a) {" + System.getProperty("line.separator") +
                "    }"));
        assertFalse(element.getText().contains("step <a> and a table <table>"));

    }

    public void testJavaElementForRefactoringForStepWithUnicodeCharacters() throws Exception {
        StepValue oldStepValue = new StepValue("† ‡ µ ¢ step with {} and {}", "† ‡ µ ¢ step with <Û> and <į>", Arrays.asList("Û", "į"));
        StepValue newStepValue = new StepValue("† ‡ µ ¢ step with {}", "† ‡ µ ¢ step with <Û>", Arrays.asList("Û"));
        String implFile = String.format("test%sfiles%sformatted%sStepImpl.java", File.separator, File.separator, File.separator);

        Messages.ParameterPosition firstParameterPosition = Messages.ParameterPosition.newBuilder().setOldPosition(0).setNewPosition(0).build();
        ArrayList<Messages.ParameterPosition> parameterPositions = new ArrayList<Messages.ParameterPosition>();
        parameterPositions.add(firstParameterPosition);

        JavaRefactoring refactoring = new JavaRefactoring(oldStepValue, newStepValue, parameterPositions);
        JavaRefactoringElement element = refactoring.createJavaRefactoringElement(implFile);

        assertEquals(getImplFile(implFile).getName(), element.getFile().getName());
        assertTrue(element.getText().contains("    @Step(\"† ‡ µ ¢ step with <Û>\")" + System.getProperty("line.separator") +
                "    public void stepWith(String a) {" + System.getProperty("line.separator") +
                "    }"));
        assertFalse(element.getText().contains("† ‡ µ ¢ step with <Û> and <į>"));

    }

    public void testJavaElementForRefactoringWithParametersRemovedAndAdded() throws Exception {
        StepValue oldStepValue = new StepValue("step {} and a table {}", "step <a> and a table <table>", new ArrayList<String>());
        StepValue newStepValue = new StepValue("{} changed {} and added {}", "<b> changed <a> and added <c>", Arrays.asList("b", "a", "c"));
        String implFile = String.format("test%sfiles%sformatted%sStepImpl.java", File.separator, File.separator, File.separator);

        Messages.ParameterPosition firstParameterPosition = Messages.ParameterPosition.newBuilder().setOldPosition(-1).setNewPosition(0).build();
        Messages.ParameterPosition secondParameterPosition = Messages.ParameterPosition.newBuilder().setOldPosition(0).setNewPosition(1).build();
        Messages.ParameterPosition thirdParameterPosition = Messages.ParameterPosition.newBuilder().setOldPosition(-1).setNewPosition(2).build();
        ArrayList<Messages.ParameterPosition> parameterPositions = new ArrayList<Messages.ParameterPosition>();
        parameterPositions.add(firstParameterPosition);
        parameterPositions.add(secondParameterPosition);
        parameterPositions.add(thirdParameterPosition);


        JavaRefactoring refactoring = new JavaRefactoring(oldStepValue, newStepValue, parameterPositions);
        JavaRefactoringElement element = refactoring.createJavaRefactoringElement(implFile);

        assertEquals(getImplFile(implFile).getName(), element.getFile().getName());
        assertTrue(element.getText().contains("    @Step(\"<b> changed <a> and added <c>\")" + System.getProperty("line.separator") +
                "    public void stepWithTable(String argB, float a, String argC) {" + System.getProperty("line.separator") +
                "    }"));
        assertFalse(element.getText().contains("step <a> and a table <table>"));

    }

    public void testResultForRefactoringWhenFileNotFound() throws Exception {
        StepValue oldStepValue = new StepValue("A step with no params", "A step with no params", new ArrayList<String>());
        StepValue newStepValue = new StepValue("step changed", "step changed", new ArrayList<String>());
        JavaRefactoring refactoring = new JavaRefactoring(oldStepValue, newStepValue, new ArrayList<Messages.ParameterPosition>());
        RefactoringResult result = refactoring.performRefactoring();

        assertEquals(result.passed(), false);
        assertEquals(result.errorMessage(), "Step Implementation Not Found: Unable to find a file Name to refactor");
        assertEquals(result.fileChanged(), "");
    }

    public void testResultForRefactoringWhenFileDoesNotExist() throws Exception {
        StepValue oldStepValue = new StepValue("A step with no params", "A step with no params", new ArrayList<String>());
        StepValue newStepValue = new StepValue("step changed", "step changed", new ArrayList<String>());

        mockStatic(StepRegistry.class);
        when(StepRegistry.getFileName("A step with no params")).thenReturn("foobar");

        JavaRefactoring refactoring = new JavaRefactoring(oldStepValue, newStepValue, new ArrayList<Messages.ParameterPosition>());
        RefactoringResult result = refactoring.performRefactoring();

        assertEquals(result.passed(), false);
        assertEquals(result.errorMessage(), "Step Implementation Not Found: Unable to find file: foobar");
        assertEquals(result.fileChanged(), "");
    }

    public void testJavaElementForRefactoringWithMethodWithComments() throws Exception {
        StepValue oldStepValue = new StepValue("A step with comments", "A step with comments", new ArrayList<String>());
        StepValue newStepValue = new StepValue("with comments", "with comments", new ArrayList<String>());
        String implFile = String.format("test%sfiles%sformatted%sStepImpl.java", File.separator, File.separator, File.separator);
        JavaRefactoring refactoring = new JavaRefactoring(oldStepValue, newStepValue, new ArrayList<Messages.ParameterPosition>());
        JavaRefactoringElement element = refactoring.createJavaRefactoringElement(implFile);

        assertEquals(getImplFile(implFile).getName(), element.getFile().getName());
        assertTrue(element.getText().contains("    @Step(\"with comments\")" + System.getProperty("line.separator") +
                "    public void someStepWithComments() {" + System.getProperty("line.separator") +
                "        //comment1" + System.getProperty("line.separator") +
                "        //comment2" + System.getProperty("line.separator") +
                "        /*\n" +
                "                    comment3\n" +
                "                    comment4\n" +
                "         */" + System.getProperty("line.separator") +
                "        /*\n" +
                "                comment6\n" +
                "                    comment7\n" +
                "                        comment8\n" +
                "         */" + System.getProperty("line.separator") +
                "        System.out.println(\"\");" + System.getProperty("line.separator") +
                "    }"));
        assertFalse(element.getText().contains("A step with comments"));
    }

    public void testRefactoringWithOrphanComments() throws RefactoringException {
        StepValue oldStepValue = new StepValue("A step with comments", "A step with comments", new ArrayList<String>());
        StepValue newStepValue = new StepValue("with comments", "with comments", new ArrayList<String>());
        String implFile = String.format("test%sfiles%sformatted%sStepImplWithComments.java", File.separator, File.separator, File.separator);

        JavaRefactoring refactoring = new JavaRefactoring(oldStepValue, newStepValue, new ArrayList<Messages.ParameterPosition>());
        JavaRefactoringElement element = refactoring.createJavaRefactoringElement(implFile);

        String expectedValue = "    @Step(\"with comments\")" + System.getProperty("line.separator") +
                "    public void someStepWithComments() {" + System.getProperty("line.separator") +
                "        //comment1" + System.getProperty("line.separator") +
                "        //comment2" + System.getProperty("line.separator") +
                "        /*\n" +
                "                    comment3\n" +
                "                    comment4\n" +
                "         */" + System.getProperty("line.separator") +
                "        /*\n" +
                "                comment6\n" +
                "                    comment7\n" +
                "                        comment8\n" +
                "         */" + System.getProperty("line.separator") +
                "        System.out.println(\"\");" + System.getProperty("line.separator") +
                "    //comment9" + System.getProperty("line.separator") +
                "    //comment10" + System.getProperty("line.separator") +
                "    /*\n" +
                "                    comment11\n" +
                "                    comment12\n" +
                "         */" + System.getProperty("line.separator") +
                "    /*\n" +
                "                comment13\n" +
                "                    comment14\n" +
                "                        comment15\n" +
                "         */" + System.getProperty("line.separator") +
                "    }";
        String actualValue = element.getText();

        assertEquals(getImplFile(implFile).getName(), element.getFile().getName());
        assertTrue(actualValue.contains(expectedValue));
        assertFalse(actualValue.contains("A step with comments"));
    }

    public void testJavaElementForRefactoringWithUnFormattedMethod() throws Exception {
        StepValue oldStepValue = new StepValue("A step with no params", "A step with no params", new ArrayList<String>());
        StepValue newStepValue = new StepValue("A step with no paramss", "A step with no paramss", new ArrayList<String>());
        String implFile = String.format("test%sfiles%sunformatted%sUnFormattedStepImpl.java", File.separator, File.separator, File.separator);
        JavaRefactoring refactoring = new JavaRefactoring(oldStepValue, newStepValue, new ArrayList<Messages.ParameterPosition>());
        JavaRefactoringElement element = refactoring.createJavaRefactoringElement(implFile);

        assertEquals(getImplFile(implFile).getName(), element.getFile().getName());
        assertTrue(element.getText().contains("public class StepImpl {" + System.getProperty("line.separator") +
                "" + System.getProperty("line.separator") +
                "    @Step(\"A step with no paramss\")" + System.getProperty("line.separator") +
                "    public void someStepStep() {" + System.getProperty("line.separator") +
                "    }" + System.getProperty("line.separator") +
                "}" + System.getProperty("line.separator")));
    }

    public void testJavaElementForRefactoringWithMethodHavingNewLineCharInString() throws Exception {
        StepValue oldStepValue = new StepValue("A step with newLine", "A step with newLine", new ArrayList<String>());
        StepValue newStepValue = new StepValue("step changed", "step changed", new ArrayList<String>());
        String implFile = String.format("test%sfiles%sformatted%sStepImpl.java", File.separator, File.separator, File.separator);
        JavaRefactoring refactoring = new JavaRefactoring(oldStepValue, newStepValue, new ArrayList<Messages.ParameterPosition>());
        JavaRefactoringElement element = refactoring.createJavaRefactoringElement(implFile);

        assertEquals(getImplFile(implFile).getName(), element.getFile().getName());
        assertTrue(element.getText().contains("    @Step(\"step changed\")" + System.getProperty("line.separator") +
                "    public void someStepStep() {" + System.getProperty("line.separator") +
                "        System.out.println(\"\\n\");" + System.getProperty("line.separator") +
                "    }"));
        assertFalse(element.getText().contains("A step with newLine"));
    }
}
