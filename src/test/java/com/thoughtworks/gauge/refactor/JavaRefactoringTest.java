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

import com.thoughtworks.gauge.StepRegistryEntry;
import com.thoughtworks.gauge.StepValue;
import com.thoughtworks.gauge.registry.StepRegistry;
import gauge.messages.Messages;
import junit.framework.TestCase;
import org.assertj.core.util.Lists;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.mockito.Mockito.*;


public class JavaRefactoringTest extends TestCase {

    private boolean saveChanges = true;

    public void testRefactoringWithAlias() {
        ArrayList<String> stringSet = Lists.newArrayList("anyString", "anyOtherString");

        StepRegistry registry = mock(StepRegistry.class);
        StepRegistryEntry entry = mock(StepRegistryEntry.class);
        when(registry.get("old step")).thenReturn(entry);
        when(registry.getFileName("old step")).thenReturn("foo");
        when(registry.getAllAliasAnnotationTextsFor("old step")).thenReturn(stringSet);

        StepValue oldStepValue = new StepValue("old step", "", new ArrayList<>());
        StepValue newStepValue = new StepValue("", "", new ArrayList<>());
        String parameterizedStepValue = "";
        RefactoringResult result = new JavaRefactoring(oldStepValue, newStepValue, new ArrayList<>(), registry, parameterizedStepValue, saveChanges).performRefactoring();
        assertFalse(result.passed());
        assertEquals("Refactoring for steps having aliases are not supported.", result.errorMessage());
    }

    public void testRefactoringWithDuplicateImplementations() {
        StepRegistryEntry entry = mock(StepRegistryEntry.class);
        StepRegistry registry = mock(StepRegistry.class);
        when(registry.hasMultipleImplementations("old step")).thenReturn(true);
        when(registry.get("old step")).thenReturn(entry);
        when(registry.getFileName("old step")).thenReturn("foo");
        when(entry.getHasAlias()).thenReturn(false);

        StepValue oldStepValue = new StepValue("old step", "", new ArrayList<>());
        StepValue newStepValue = new StepValue("", "", new ArrayList<>());
        String parameterizedStepValue = "";
        RefactoringResult result = new JavaRefactoring(oldStepValue, newStepValue, new ArrayList<>(), registry, parameterizedStepValue, saveChanges).performRefactoring();
        assertFalse(result.passed());
        assertEquals("Duplicate step implementation found.", result.errorMessage());
    }

    public void testJavaElementForSimpleRefactoring() throws Exception {
        StepRegistry registry = mock(StepRegistry.class);

        StepValue oldStepValue = new StepValue("A step with no params", "A step with no params", new ArrayList<>());
        StepValue newStepValue = new StepValue("step changed", "step changed", new ArrayList<>());
        String implFile = String.format("test%sfiles%sformatted%sStepImpl.java", File.separator, File.separator, File.separator);

        String parameterizedStepValue = "step changed";
        JavaRefactoring refactoring = new JavaRefactoring(oldStepValue, newStepValue, new ArrayList<>(), registry, parameterizedStepValue, saveChanges);
        JavaRefactoringElement element = refactoring.createJavaRefactoringElement(implFile);

        assertEquals(getImplFile(implFile).getName(), element.getFile().getName());
        assertTrue(element.getText().contains("    @Step(\"step changed\")" + System.getProperty("line.separator") +
                "    public void someStepStep() {" + System.getProperty("line.separator") +
                "    }"));
        assertFalse(element.getText().contains("A step with no params"));

    }

    public void testJavaElementForRefactoringWithNewParameter() throws Exception {
        StepRegistry registry = mock(StepRegistry.class);

        StepValue oldStepValue = new StepValue("A step with no params", "A step with no params", new ArrayList<>());
        StepValue newStepValue = new StepValue("step with {}", "step with <param 1>", Collections.singletonList("param 1"));
        String implFile = String.format("test%sfiles%sformatted%sStepImpl.java", File.separator, File.separator, File.separator);

        Messages.ParameterPosition parameterPosition = Messages.ParameterPosition.newBuilder().setOldPosition(-1).setNewPosition(0).build();
        ArrayList<Messages.ParameterPosition> parameterPositions = new ArrayList<>();
        parameterPositions.add(parameterPosition);
        String parameterizedStepValue = "step with <param 1>";
        JavaRefactoring refactoring = new JavaRefactoring(oldStepValue, newStepValue, parameterPositions, registry, parameterizedStepValue, saveChanges);
        JavaRefactoringElement element = refactoring.createJavaRefactoringElement(implFile);

        assertEquals(getImplFile(implFile).getName(), element.getFile().getName());
        assertTrue(element.getText().contains("    @Step(\"step with <param 1>\")" + System.getProperty("line.separator") +
                "    public void someStepStep(Object argParam1) {" + System.getProperty("line.separator") +
                "    }"));
        assertFalse(element.getText().contains("A step with no params"));
    }

    public void testJavaElementForRefactoringWithNewParametersWithSameName() throws Exception {
        StepRegistry registry = mock(StepRegistry.class);
        StepValue oldStepValue = new StepValue("A step with no params", "A step with no params", new ArrayList<>());
        StepValue newStepValue = new StepValue("step with {} {}", "step with <n> <n>", Arrays.asList("n", "n"));
        String implFile = String.format("test%sfiles%sformatted%sStepImpl.java", File.separator, File.separator, File.separator);

        Messages.ParameterPosition parameterPosition = Messages.ParameterPosition.newBuilder().setOldPosition(-1).setNewPosition(0).build();
        Messages.ParameterPosition parameterPosition1 = Messages.ParameterPosition.newBuilder().setOldPosition(-1).setNewPosition(1).build();
        ArrayList<Messages.ParameterPosition> parameterPositions = new ArrayList<>();
        parameterPositions.add(parameterPosition);
        parameterPositions.add(parameterPosition1);
        String parameterizedStepValue = "step with <n> <n>";
        JavaRefactoring refactoring = new JavaRefactoring(oldStepValue, newStepValue, parameterPositions, registry, parameterizedStepValue, saveChanges);
        JavaRefactoringElement element = refactoring.createJavaRefactoringElement(implFile);

        assertEquals(getImplFile(implFile).getName(), element.getFile().getName());
        assertTrue(element.getText().contains("    @Step(\"step with <n> <n>\")" + System.getProperty("line.separator") +
                "    public void someStepStep(Object argN, Object argN1) {" + System.getProperty("line.separator") +
                "    }"));
        assertFalse(element.getText().contains("A step with no params"));
    }

    public void testJavaElementForRefactoringWithNewParameterWithSameNameAsExisting() throws Exception {
        StepRegistry registry = mock(StepRegistry.class);
        StepValue oldStepValue = new StepValue("Tell {} {}", "Tell <greeting> <name>", Arrays.asList("greeting", "name"));
        StepValue newStepValue = new StepValue("Tell {} {} {}", "Tell <greeting> <name> <name>", Arrays.asList("greeting", "name", "name"));
        String implFile = String.format("test%sfiles%sformatted%sStepImpl.java", File.separator, File.separator, File.separator);

        Messages.ParameterPosition parameterPosition1 = Messages.ParameterPosition.newBuilder().setOldPosition(0).setNewPosition(0).build();
        Messages.ParameterPosition parameterPosition2 = Messages.ParameterPosition.newBuilder().setOldPosition(1).setNewPosition(1).build();
        Messages.ParameterPosition parameterPosition3 = Messages.ParameterPosition.newBuilder().setOldPosition(-1).setNewPosition(2).build();
        ArrayList<Messages.ParameterPosition> parameterPositions = new ArrayList<>();
        parameterPositions.add(parameterPosition1);
        parameterPositions.add(parameterPosition2);
        parameterPositions.add(parameterPosition3);

        String parameterizedStepValue = "Tell <greeting> <name> <name>";
        JavaRefactoring refactoring = new JavaRefactoring(oldStepValue, newStepValue, parameterPositions, registry, parameterizedStepValue, saveChanges);
        JavaRefactoringElement element = refactoring.createJavaRefactoringElement(implFile);
        assertEquals(getImplFile(implFile).getName(), element.getFile().getName());
        assertTrue(element.getText().contains("    @Step(\"Tell <greeting> <name> <name>\")" + System.getProperty("line.separator") +
                "    public void helloWorld(String greeting, String argName, Object argName2) {" + System.getProperty("line.separator") +
                "    }"));
        assertFalse(element.getText().contains("\"Tell <greeting> <name>\""));

    }

    public void testJavaElementForRefactoringWithNewParameterWhenParametersPresent() throws Exception {
        StepRegistry registry = mock(StepRegistry.class);
        StepValue oldStepValue = new StepValue("Tell {} to {}", "Tell <greeting> to <name>", Arrays.asList("greeting", "name"));
        StepValue newStepValue = new StepValue("Tell {} to {} {}", "Tell <greeting> to <name> <DD>", Arrays.asList("greeting", "name", "DD"));
        String implFile = String.format("test%sfiles%sformatted%sStepImpl.java", File.separator, File.separator, File.separator);

        Messages.ParameterPosition parameterPosition1 = Messages.ParameterPosition.newBuilder().setOldPosition(0).setNewPosition(0).build();
        Messages.ParameterPosition parameterPosition2 = Messages.ParameterPosition.newBuilder().setOldPosition(1).setNewPosition(1).build();
        Messages.ParameterPosition parameterPosition3 = Messages.ParameterPosition.newBuilder().setOldPosition(-1).setNewPosition(2).build();
        ArrayList<Messages.ParameterPosition> parameterPositions = new ArrayList<>();
        parameterPositions.add(parameterPosition1);
        parameterPositions.add(parameterPosition2);
        parameterPositions.add(parameterPosition3);

        String parameterizedStepValue = "Tell <greeting> to <name> <DD>";
        JavaRefactoring refactoring = new JavaRefactoring(oldStepValue, newStepValue, parameterPositions, registry, parameterizedStepValue, saveChanges);
        JavaRefactoringElement element = refactoring.createJavaRefactoringElement(implFile);
        assertEquals(getImplFile(implFile).getName(), element.getFile().getName());
        assertTrue(element.getText().contains("    @Step(\"Tell <greeting> to <name> <DD>\")" + System.getProperty("line.separator") +
                "    public void helloWorld(String greeting, String name, Object argDd) {" + System.getProperty("line.separator") +
                "        System.out.println(greeting + \", \" + name);" + System.getProperty("line.separator") +
                "    }"));
        assertFalse(element.getText().contains("\"Tell <greeting> to <name>\""));

    }

    private File getImplFile(String fileName) {
        return new File(String.format("src%stest%sresources", File.separator, File.separator), fileName);
    }

    public void testJavaElementForRefactoringWithParametersAdded() throws Exception {
        StepRegistry registry = mock(StepRegistry.class);
        StepValue oldStepValue = new StepValue("step {} and a table {}", "step <a> and a table <table>", Arrays.asList("a", "b"));
        StepValue newStepValue = new StepValue("{} changed {} and added {}", "<table> changed <c> and added <a>", Arrays.asList("b", "c", "a"));
        String implFile = String.format("test%sfiles%sformatted%sStepImpl.java", File.separator, File.separator, File.separator);

        Messages.ParameterPosition firstParameterPosition = Messages.ParameterPosition.newBuilder().setOldPosition(0).setNewPosition(2).build();
        Messages.ParameterPosition secondParameterPosition = Messages.ParameterPosition.newBuilder().setOldPosition(1).setNewPosition(0).build();
        Messages.ParameterPosition thirdParameterPosition = Messages.ParameterPosition.newBuilder().setOldPosition(-1).setNewPosition(1).build();
        ArrayList<Messages.ParameterPosition> parameterPositions = new ArrayList<>();
        parameterPositions.add(firstParameterPosition);
        parameterPositions.add(secondParameterPosition);
        parameterPositions.add(thirdParameterPosition);


        String parameterizedStepValue = "<table> changed <c> and added <a>";
        JavaRefactoring refactoring = new JavaRefactoring(oldStepValue, newStepValue, parameterPositions, registry, parameterizedStepValue, saveChanges);
        JavaRefactoringElement element = refactoring.createJavaRefactoringElement(implFile);

        assertEquals(getImplFile(implFile).getName(), element.getFile().getName());
        assertTrue(element.getText().contains("    @Step(\"<table> changed <c> and added <a>\")" + System.getProperty("line.separator") +
                "    public void stepWithTable(Table table, Object argC, float a) {" + System.getProperty("line.separator") +
                "    }"));
        assertFalse(element.getText().contains("step <a> and a table <table>"));
    }

    public void testJavaElementForRefactoringForStepWithUnicodeCharacters() throws Exception {
        StepRegistry registry = mock(StepRegistry.class);
        StepValue oldStepValue = new StepValue("† ‡ µ ¢ step with {} and {}", "† ‡ µ ¢ step with <Û> and <į>", Arrays.asList("Û", "į"));
        StepValue newStepValue = new StepValue("† ‡ µ ¢ step with {}", "† ‡ µ ¢ step with <Û>", Collections.singletonList("Û"));
        String implFile = String.format("test%sfiles%sformatted%sStepImpl.java", File.separator, File.separator, File.separator);

        Messages.ParameterPosition firstParameterPosition = Messages.ParameterPosition.newBuilder().setOldPosition(0).setNewPosition(0).build();
        ArrayList<Messages.ParameterPosition> parameterPositions = new ArrayList<>();
        parameterPositions.add(firstParameterPosition);

        String parameterizedStepValue = "† ‡ µ ¢ step with <Û>";
        JavaRefactoring refactoring = new JavaRefactoring(oldStepValue, newStepValue, parameterPositions, registry, parameterizedStepValue, saveChanges);
        JavaRefactoringElement element = refactoring.createJavaRefactoringElement(implFile);

        assertEquals(getImplFile(implFile).getName(), element.getFile().getName());
        assertTrue(element.getText().contains("    @Step(\"\\u2020 \\u2021 \\u00B5 \\u00A2 step with <\\u00DB>\")" + System.getProperty("line.separator") +
                "    public void stepWith(String a) {" + System.getProperty("line.separator") +
                "    }"));
        assertFalse(element.getText().contains("† ‡ µ ¢ step with <Û> and <į>"));
    }

    public void testJavaElementForRefactoringWithDualBackSlashes() throws Exception {
        StepRegistry registry = mock(StepRegistry.class);
        StepValue oldStepValue = new StepValue("A step with \\", "A step with \\", new ArrayList<>());
        StepValue newStepValue = new StepValue("step changed to \\", "step changed to \\", new ArrayList<>());
        String implFile = String.format("test%sfiles%sformatted%sStepImpl.java", File.separator, File.separator, File.separator);

        String parameterizedStepValue = "step changed to \\";
        JavaRefactoring refactoring = new JavaRefactoring(oldStepValue, newStepValue, new ArrayList<>(), registry, parameterizedStepValue, saveChanges);
        JavaRefactoringElement element = refactoring.createJavaRefactoringElement(implFile);

        assertEquals(getImplFile(implFile).getName(), element.getFile().getName());
        assertTrue(element.getText().contains("    @Step(\"step changed to \\\\\")" + System.getProperty("line.separator") +
                "    public void stepWithDualBackSlashes() {" + System.getProperty("line.separator") +
                "    }"));
        assertFalse(element.getText().contains("A step with \\"));
    }

    public void testJavaElementForRefactoringWithTab() throws Exception {
        StepRegistry registry = mock(StepRegistry.class);
        StepValue oldStepValue = new StepValue("A step 123", "A step 123", new ArrayList<>());
        StepValue newStepValue = new StepValue("step changed to \t", "step changed to \t", new ArrayList<>());
        String implFile = String.format("test%sfiles%sformatted%sStepImpl.java", File.separator, File.separator, File.separator);

        String parameterizedStepValue = "step changed to \t";
        JavaRefactoring refactoring = new JavaRefactoring(oldStepValue, newStepValue, new ArrayList<>(), registry, parameterizedStepValue, saveChanges);
        JavaRefactoringElement element = refactoring.createJavaRefactoringElement(implFile);

        assertEquals(getImplFile(implFile).getName(), element.getFile().getName());
        assertTrue(element.getText().contains("    @Step(\"step changed to \\t\")" + System.getProperty("line.separator") +
                "    public void stepWithTab() {" + System.getProperty("line.separator") +
                "    }"));
        assertFalse(element.getText().contains("A step 123"));
    }

    public void testJavaElementForRefactoringWithParametersRemovedAndAdded() throws Exception {
        StepRegistry registry = mock(StepRegistry.class);
        StepValue oldStepValue = new StepValue("step {} and a table {}", "step <a> and a table <table>", new ArrayList<>());
        StepValue newStepValue = new StepValue("{} changed {} and added {}", "<b> changed <a> and added <c>", Arrays.asList("b", "a", "c"));
        String implFile = String.format("test%sfiles%sformatted%sStepImpl.java", File.separator, File.separator, File.separator);

        Messages.ParameterPosition firstParameterPosition = Messages.ParameterPosition.newBuilder().setOldPosition(-1).setNewPosition(0).build();
        Messages.ParameterPosition secondParameterPosition = Messages.ParameterPosition.newBuilder().setOldPosition(0).setNewPosition(1).build();
        Messages.ParameterPosition thirdParameterPosition = Messages.ParameterPosition.newBuilder().setOldPosition(-1).setNewPosition(2).build();
        ArrayList<Messages.ParameterPosition> parameterPositions = new ArrayList<>();
        parameterPositions.add(firstParameterPosition);
        parameterPositions.add(secondParameterPosition);
        parameterPositions.add(thirdParameterPosition);


        String parameterizedStepValue = "<b> changed <a> and added <c>";
        JavaRefactoring refactoring = new JavaRefactoring(oldStepValue, newStepValue, parameterPositions, registry, parameterizedStepValue, saveChanges);
        JavaRefactoringElement element = refactoring.createJavaRefactoringElement(implFile);

        assertEquals(getImplFile(implFile).getName(), element.getFile().getName());
        assertTrue(element.getText().contains("    @Step(\"<b> changed <a> and added <c>\")" + System.getProperty("line.separator") +
                "    public void stepWithTable(Object argB, float a, Object argC) {" + System.getProperty("line.separator") +
                "    }"));
        assertFalse(element.getText().contains("step <a> and a table <table>"));

    }

    public void testResultForRefactoringWhenFileNotFound() {
        StepRegistry registry = mock(StepRegistry.class);
        StepRegistryEntry entry = mock(StepRegistryEntry.class);
        when(registry.get("A step with no params")).thenReturn(entry);
        when(entry.getFileName()).thenReturn("");
        StepValue oldStepValue = new StepValue("A step with no params", "A step with no params", new ArrayList<>());
        StepValue newStepValue = new StepValue("step changed", "step changed", new ArrayList<>());
        String parameterizedStepValue = "step changed";
        JavaRefactoring refactoring = new JavaRefactoring(oldStepValue, newStepValue, new ArrayList<>(), registry, parameterizedStepValue, saveChanges);
        RefactoringResult result = refactoring.performRefactoring();

        assertFalse(result.passed());
        assertEquals(result.errorMessage(), "Step Implementation Not Found: Unable to find a file Name to refactor");
        assertEquals(result.fileChanged(), "");
    }

    public void testResultForRefactoringWhenFileDoesNotExist() {

        StepValue oldStepValue = new StepValue("A step with no params", "A step with no params", new ArrayList<>());
        StepValue newStepValue = new StepValue("step changed", "step changed", new ArrayList<>());

        StepRegistry registry = mock(StepRegistry.class);
        StepRegistryEntry entry = mock(StepRegistryEntry.class);
        when(registry.get("A step with no params")).thenReturn(entry);
        when(registry.getFileName("A step with no params")).thenReturn("foobar");

        String parameterizedStepValue = "step changed";
        JavaRefactoring refactoring = new JavaRefactoring(oldStepValue, newStepValue, new ArrayList<>(), registry, parameterizedStepValue, saveChanges);
        RefactoringResult result = refactoring.performRefactoring();

        assertFalse(result.passed());
        assertEquals(result.errorMessage(), "Step Implementation Not Found: Unable to find file: foobar");
        assertEquals(result.fileChanged(), "");
    }

    public void testJavaElementForRefactoringWithMethodWithComments() throws Exception {
        StepRegistry registry = mock(StepRegistry.class);
        StepValue oldStepValue = new StepValue("A step with comments", "A step with comments", new ArrayList<>());
        StepValue newStepValue = new StepValue("with comments", "with comments", new ArrayList<>());
        String implFile = String.format("test%sfiles%sformatted%sStepImpl.java", File.separator, File.separator, File.separator);
        String parameterizedStepValue = "with comments";
        JavaRefactoring refactoring = new JavaRefactoring(oldStepValue, newStepValue, new ArrayList<>(), registry, parameterizedStepValue, saveChanges);
        JavaRefactoringElement element = refactoring.createJavaRefactoringElement(implFile);

        assertEquals(getImplFile(implFile).getName(), element.getFile().getName());
        String newLineChar = System.getProperty("line.separator");
        assertTrue(element.getText().contains("    @Step(\"with comments\")" + newLineChar +
                "    public void someStepWithComments() {" + newLineChar +
                "        //comment1" + newLineChar +
                "        //comment2" + newLineChar +
                "        /*" + newLineChar +
                "                    comment3" + newLineChar +
                "                    comment4" + newLineChar +
                "         */" + newLineChar +
                "        /*" + newLineChar +
                "                comment6" + newLineChar +
                "                    comment7" + newLineChar +
                "                        comment8" + newLineChar +
                "         */" + newLineChar +
                "        System.out.println(\"\");" + newLineChar +
                "    }"));
        assertFalse(element.getText().contains("A step with comments"));
    }

    public void testRefactoringWithOrphanComments() throws RefactoringException {
        StepRegistry registry = mock(StepRegistry.class);
        StepValue oldStepValue = new StepValue("A step with comments", "A step with comments", new ArrayList<>());
        StepValue newStepValue = new StepValue("with comments", "with comments", new ArrayList<>());
        String implFile = String.format("test%sfiles%sformatted%sStepImplWithComments.java", File.separator, File.separator, File.separator);

        String parameterizedStepValue = "with comments";
        JavaRefactoring refactoring = new JavaRefactoring(oldStepValue, newStepValue, new ArrayList<>(), registry, parameterizedStepValue, saveChanges);
        JavaRefactoringElement element = refactoring.createJavaRefactoringElement(implFile);

        String newLineChar = System.getProperty("line.separator");
        String expectedValue = "    @Step(\"with comments\")" + newLineChar +
                "    public void someStepWithComments() {" + newLineChar +
                "        //comment1" + newLineChar +
                "        //comment2" + newLineChar +
                "        /*" + newLineChar +
                "                    comment3" + newLineChar +
                "                    comment4" + newLineChar +
                "         */" + newLineChar +
                "        /*" + newLineChar +
                "                comment6" + newLineChar +
                "                    comment7" + newLineChar +
                "                        comment8" + newLineChar +
                "         */" + newLineChar +
                "        System.out.println(\"\");" + newLineChar +
                "    //comment9" + newLineChar +
                "    //comment10" + newLineChar +
                "    /*" + newLineChar +
                "                    comment11" + newLineChar +
                "                    comment12" + newLineChar +
                "         */" + newLineChar +
                "    /*" + newLineChar +
                "                comment13" + newLineChar +
                "                    comment14" + newLineChar +
                "                        comment15" + newLineChar +
                "         */" + newLineChar +
                "    }";
        String actualValue = element.getText();

        assertEquals(getImplFile(implFile).getName(), element.getFile().getName());
        assertTrue(actualValue.contains(expectedValue));
        assertFalse(actualValue.contains("A step with comments"));
    }

    public void testJavaElementForRefactoringWithUnFormattedMethod() throws Exception {
        StepRegistry registry = mock(StepRegistry.class);
        StepValue oldStepValue = new StepValue("A step with no params", "A step with no params", new ArrayList<>());
        StepValue newStepValue = new StepValue("A step with no paramss", "A step with no paramss", new ArrayList<>());
        String implFile = String.format("test%sfiles%sunformatted%sUnFormattedStepImpl.java", File.separator, File.separator, File.separator);
        String parameterizedStepValue = "A step with no paramss";
        JavaRefactoring refactoring = new JavaRefactoring(oldStepValue, newStepValue, new ArrayList<>(), registry, parameterizedStepValue, saveChanges);
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
        StepRegistry registry = mock(StepRegistry.class);
        StepValue oldStepValue = new StepValue("A step with newLine", "A step with newLine", new ArrayList<>());
        StepValue newStepValue = new StepValue("step changed", "step changed", new ArrayList<>());
        String implFile = String.format("test%sfiles%sformatted%sStepImpl.java", File.separator, File.separator, File.separator);
        String parameterizedStepValue = "step changed";
        JavaRefactoring refactoring = new JavaRefactoring(oldStepValue, newStepValue, new ArrayList<>(), registry, parameterizedStepValue, saveChanges);
        JavaRefactoringElement element = refactoring.createJavaRefactoringElement(implFile);

        assertEquals(getImplFile(implFile).getName(), element.getFile().getName());
        assertTrue(element.getText().contains("    @Step(\"step changed\")" + System.getProperty("line.separator") +
                "    public void someStepStep() {" + System.getProperty("line.separator") +
                "        System.out.println(\"\\n\");" + System.getProperty("line.separator") +
                "    }"));
        assertFalse(element.getText().contains("A step with newLine"));
    }

    public void testJavaElementForRefactoringWithStepHavingOneAlias() throws Exception {
        StepRegistry registry = mock(StepRegistry.class);
        StepValue oldStepValue = new StepValue("A step defined with alias syntax", "A step defined with alias syntax", new ArrayList<>());
        StepValue newStepValue = new StepValue("step changed", "step changed", new ArrayList<>());
        String implFile = String.format("test%sfiles%sformatted%sStepImpl.java", File.separator, File.separator, File.separator);

        String parameterizedStepValue = "step changed";
        JavaRefactoring refactoring = new JavaRefactoring(oldStepValue, newStepValue, new ArrayList<>(), registry, parameterizedStepValue, saveChanges);
        JavaRefactoringElement element = refactoring.createJavaRefactoringElement(implFile);

        assertEquals(getImplFile(implFile).getName(), element.getFile().getName());
        assertTrue(element.getText().contains("    @Step(\"step changed\")" + System.getProperty("line.separator") +
                "    public void stepDefinedWithAliasSyntax() {" + System.getProperty("line.separator") +
                "    }"));
        assertFalse(element.getText().contains("A step defined with alias syntax" +
                ""));

    }
}
