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
import java.util.ArrayList;
import java.util.Arrays;

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

    public void testJavaElementForSimpleRefactoring() throws Exception {
        StepValue oldStepValue = new StepValue("A step with no params", "A step with no params", new ArrayList<String>());
        StepValue newStepValue = new StepValue("step changed", "step changed", new ArrayList<String>());
        File javaFile = getImplFile();
        JavaRefactoring refactoring = new JavaRefactoring(oldStepValue, newStepValue, new ArrayList<Messages.ParameterPosition>());
        JavaRefactoringElement element = refactoring.createJavaRefactoringElement(javaFile.getName());

        assertEquals(javaFile.getName(), element.getFile().getName());
        assertEquals(17, element.getBeginLine());
        assertEquals(19, element.getEndLine());
        assertEquals(4, element.getIndentation());
        assertEquals("@Step(\"step changed\")\n" +
                "public void someStepStep() {\n" +
                "}", element.getText());

    }

    public void testJavaElementForRefactoringWithNewParameter() throws Exception {
        StepValue oldStepValue = new StepValue("A step with no params", "A step with no params", new ArrayList<String>());
        StepValue newStepValue = new StepValue("step with {}", "step with <param 1>", Arrays.asList("param 1"));
        File javaFile = getImplFile();
        Messages.ParameterPosition parameterPosition = Messages.ParameterPosition.newBuilder().setOldPosition(-1).setNewPosition(0).build();
        ArrayList<Messages.ParameterPosition> parameterPositions = new ArrayList<Messages.ParameterPosition>();
        parameterPositions.add(parameterPosition);
        JavaRefactoring refactoring = new JavaRefactoring(oldStepValue, newStepValue, parameterPositions);
        JavaRefactoringElement element = refactoring.createJavaRefactoringElement(javaFile.getName());

        assertEquals(javaFile.getName(), element.getFile().getName());
        assertEquals(17, element.getBeginLine());
        assertEquals(19, element.getEndLine());
        assertEquals(4, element.getIndentation());
        assertEquals("@Step(\"step with <param 1>\")\n" +
                "public void someStepStep(String param1) {\n" +
                "}", element.getText());

    }

    public void testJavaElementForRefactoringWithNewParameterWhenParametersPresent() throws Exception {
        StepValue oldStepValue = new StepValue("Tell {} to {}", "Tell <greeting> to <name>", Arrays.asList("greeting", "name"));
        StepValue newStepValue = new StepValue("Tell {} to {} {}", "Tell <greeting> to <name> <DD>", Arrays.asList("greeting", "name", "DD"));
        File javaFile = getImplFile();
        Messages.ParameterPosition parameterPosition1 = Messages.ParameterPosition.newBuilder().setOldPosition(0).setNewPosition(0).build();
        Messages.ParameterPosition parameterPosition2 = Messages.ParameterPosition.newBuilder().setOldPosition(1).setNewPosition(1).build();
        Messages.ParameterPosition parameterPosition3 = Messages.ParameterPosition.newBuilder().setOldPosition(-1).setNewPosition(2).build();
        ArrayList<Messages.ParameterPosition> parameterPositions = new ArrayList<Messages.ParameterPosition>();
        parameterPositions.add(parameterPosition1);
        parameterPositions.add(parameterPosition2);
        parameterPositions.add(parameterPosition3);

        JavaRefactoring refactoring = new JavaRefactoring(oldStepValue, newStepValue, parameterPositions);
        JavaRefactoringElement element = refactoring.createJavaRefactoringElement(javaFile.getName());
        assertEquals(javaFile.getName(), element.getFile().getName());
        assertEquals(21, element.getBeginLine());
        assertEquals(24, element.getEndLine());
        assertEquals(4, element.getIndentation());
        assertEquals("@Step(\"Tell <greeting> to <name> <DD>\")\n" +
                "public void helloWorld(String greeting, String name, String dd) {\n" +
                "    System.out.println(greeting + \", \" + name);\n" +
                "}", element.getText());

    }

    private File getImplFile() {
        return new File(String.format("src%stest%sresources", File.separator, File.separator), "StepImpl.java");
    }

    public void testJavaElementForRefactoringWithParametersRemoved() throws Exception {
        StepValue oldStepValue = new StepValue("step {} and a table {}", "step <a> and a table <table>", new ArrayList<String>());
        StepValue newStepValue = new StepValue("{} changed {} and added {}", "<table> changed <c> and added <a>", Arrays.asList("b","a","c"));
        File javaFile = getImplFile();

        Messages.ParameterPosition firstParameterPosition = Messages.ParameterPosition.newBuilder().setOldPosition(0).setNewPosition(2).build();
        Messages.ParameterPosition secondParameterPosition = Messages.ParameterPosition.newBuilder().setOldPosition(1).setNewPosition(0).build();
        Messages.ParameterPosition thirdParameterPosition = Messages.ParameterPosition.newBuilder().setOldPosition(-1).setNewPosition(1).build();
        ArrayList<Messages.ParameterPosition> parameterPositions = new ArrayList<Messages.ParameterPosition>();
        parameterPositions.add(firstParameterPosition);
        parameterPositions.add(secondParameterPosition);
        parameterPositions.add(thirdParameterPosition);


        JavaRefactoring refactoring = new JavaRefactoring(oldStepValue, newStepValue, parameterPositions);
        JavaRefactoringElement element = refactoring.createJavaRefactoringElement(javaFile.getName());

        assertEquals(javaFile.getName(), element.getFile().getName());
        assertEquals(13, element.getBeginLine());
        assertEquals(15, element.getEndLine());
        assertEquals(4, element.getIndentation());
        assertEquals("@Step(\"<table> changed <c> and added <a>\")\n" +
                "public void stepWithTable(Table table, String c, float a) {\n" +
                "}", element.getText());

    }

    public void testJavaElementForRefactoringForStepWithUnicodeCharacters() throws Exception {
        StepValue oldStepValue = new StepValue("† ‡ µ ¢ step with {} and {}", "† ‡ µ ¢ step with <Û> and <į>", Arrays.asList("Û", "į"));
        StepValue newStepValue = new StepValue("† ‡ µ ¢ step with {}", "† ‡ µ ¢ step with <Û>", Arrays.asList("Û"));
        File javaFile = getImplFile();

        Messages.ParameterPosition firstParameterPosition = Messages.ParameterPosition.newBuilder().setOldPosition(0).setNewPosition(0).build();
        ArrayList<Messages.ParameterPosition> parameterPositions = new ArrayList<Messages.ParameterPosition>();
        parameterPositions.add(firstParameterPosition);

        JavaRefactoring refactoring = new JavaRefactoring(oldStepValue, newStepValue, parameterPositions);
        JavaRefactoringElement element = refactoring.createJavaRefactoringElement(javaFile.getName());

        assertEquals(javaFile.getName(), element.getFile().getName());
        assertEquals(26, element.getBeginLine());
        assertEquals(28, element.getEndLine());
        assertEquals(4, element.getIndentation());
        assertEquals("@Step(\"† ‡ µ ¢ step with <Û>\")\n" +
                "public void stepWith(String a) {\n" +
                "}", element.getText());

    }

    public void testJavaElementForRefactoringWithParametersRemovedAndAdded() throws Exception {
        StepValue oldStepValue = new StepValue("step {} and a table {}", "step <a> and a table <table>", new ArrayList<String>());
        StepValue newStepValue = new StepValue("{} changed {} and added {}", "<b> changed <a> and added <c>", Arrays.asList("b","a","c"));
        File javaFile = getImplFile();

        Messages.ParameterPosition firstParameterPosition = Messages.ParameterPosition.newBuilder().setOldPosition(-1).setNewPosition(0).build();
        Messages.ParameterPosition secondParameterPosition = Messages.ParameterPosition.newBuilder().setOldPosition(0).setNewPosition(1).build();
        Messages.ParameterPosition thirdParameterPosition = Messages.ParameterPosition.newBuilder().setOldPosition(-1).setNewPosition(2).build();
        ArrayList<Messages.ParameterPosition> parameterPositions = new ArrayList<Messages.ParameterPosition>();
        parameterPositions.add(firstParameterPosition);
        parameterPositions.add(secondParameterPosition);
        parameterPositions.add(thirdParameterPosition);


        JavaRefactoring refactoring = new JavaRefactoring(oldStepValue, newStepValue, parameterPositions);
        JavaRefactoringElement element = refactoring.createJavaRefactoringElement(javaFile.getName());

        assertEquals(javaFile.getName(), element.getFile().getName());
        assertEquals(13, element.getBeginLine());
        assertEquals(15, element.getEndLine());
        assertEquals(4, element.getIndentation());
        assertEquals("@Step(\"<b> changed <a> and added <c>\")\n" +
                "public void stepWithTable(String b, float a, String c) {\n" +
                "}", element.getText());

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
}
