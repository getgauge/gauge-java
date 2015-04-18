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

import com.thoughtworks.gauge.StepRegistry;
import com.thoughtworks.gauge.StepValue;
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
        StepValue newStepValue = new StepValue("step with {}", "step with <param 1>", Arrays.asList(new String[]{"param 1"}));
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

    private File getImplFile() {
        return new File(String.format("src%stest%sresources", File.separator, File.separator), "StepImpl.java");
    }

    public void testJavaElementForRefactoringWithParametersRemovedAndAdded() throws Exception {
        StepValue oldStepValue = new StepValue("step {} and a table {}", "step <a> and a table <table>", new ArrayList<String>());
        StepValue newStepValue = new StepValue("{} changed {} and added {}", "<b> changed <a> and added <c>", Arrays.asList(new String[]{"b","a","c"}));
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

    public void testResultForRefactoringWHenFileNotFound() throws Exception {
        StepValue oldStepValue = new StepValue("A step with no params", "A step with no params", new ArrayList<String>());
        StepValue newStepValue = new StepValue("step changed", "step changed", new ArrayList<String>());
        JavaRefactoring refactoring = new JavaRefactoring(oldStepValue, newStepValue, new ArrayList<Messages.ParameterPosition>());
        RefactoringResult result = refactoring.performRefactoring();

        assertEquals(result.passed(), false);
        assertEquals(result.errorMessage(), "Step Implementation Not Found");
        assertEquals(result.fileChanged(), "");
    }
}