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

package com.thoughtworks.gauge.execution;

import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

import com.thoughtworks.gauge.ContinueOnFailure;
import com.thoughtworks.gauge.test.anEnum;
import gauge.messages.Messages;
import gauge.messages.Spec;

import java.lang.reflect.Method;

import junit.framework.TestCase;

import com.thoughtworks.gauge.Table;

public class StepExecutionStageTest extends TestCase {
    public void testStepMethodExecutionIsCalledWithoutParameters() throws Exception {
        Messages.ExecuteStepRequest executeStepRequest = Messages.ExecuteStepRequest.newBuilder().setParsedStepText("foo bar").setActualStepText("foo bar").build();
        StepExecutionStage executionStage = new StepExecutionStage(executeStepRequest);
        MethodExecutor methodExecutor = mock(MethodExecutor.class);
        Method fooBarMethod = this.getClass().getMethod("fooBar");
        executionStage.executeStepMethod(methodExecutor, fooBarMethod);

        verify(methodExecutor, times(1)).execute(fooBarMethod);

    }

    public void testStepMethodExecutionIsCalledWithParameters() throws Exception {
        Spec.Parameter param1 = Spec.Parameter.newBuilder().setValue("1").setName("number").setParameterType(Spec.Parameter.ParameterType.Static).build();
        Spec.Parameter param2 = Spec.Parameter.newBuilder().setValue("foo").setName("string").setParameterType(Spec.Parameter.ParameterType.Special_String).build();
        Messages.ExecuteStepRequest executeStepRequest = Messages.ExecuteStepRequest.newBuilder().setParsedStepText("hello {} world {}").setActualStepText("hello <a> world <b>").addParameters(param1).addParameters(param2).build();
        StepExecutionStage executionStage = new StepExecutionStage(executeStepRequest);
        MethodExecutor methodExecutor = mock(MethodExecutor.class);
        Method fooBarMethod = this.getClass().getMethod("fooBar", int.class, String.class);
        executionStage.executeStepMethod(methodExecutor, fooBarMethod);

        verify(methodExecutor, times(1)).execute(fooBarMethod, 1, "foo");

    }
    
    public void testStepMethodExecutionCanBeCalledAsObjectForSpecialTable() throws Exception {
        Spec.Parameter tableParameter = Spec.Parameter.newBuilder().setValue("table { headers {cells: \"Id\"}rows {cells: \"1\"}}").setName("table").setParameterType(Spec.Parameter.ParameterType.Special_Table).build();
        Messages.ExecuteStepRequest executeStepRequest = Messages.ExecuteStepRequest.newBuilder().setParsedStepText("hello {}").setActualStepText("hello <table>").addParameters(tableParameter).build();
        StepExecutionStage executionStage = new StepExecutionStage(executeStepRequest);
        MethodExecutor methodExecutor = mock(MethodExecutor.class);
        Method tableMethod = this.getClass().getMethod("table", Object.class);
        executionStage.executeStepMethod(methodExecutor, tableMethod);

        verify(methodExecutor, times(1)).execute(eq(tableMethod), isA(Table.class));

    }

    public void testStepMethodExecutionWithMethodHavingInvalidParamTypeConversion() throws Exception {
        Spec.Parameter param1 = Spec.Parameter.newBuilder().setValue("a").setName("number").setParameterType(Spec.Parameter.ParameterType.Static).build();
        Spec.Parameter param2 = Spec.Parameter.newBuilder().setValue("foo").setName("string").setParameterType(Spec.Parameter.ParameterType.Static).build();
        Messages.ExecuteStepRequest executeStepRequest = Messages.ExecuteStepRequest.newBuilder().setParsedStepText("hello {} world {}").setActualStepText("hello <a> world <b>").addParameters(param1).addParameters(param2).build();
        StepExecutionStage executionStage = new StepExecutionStage(executeStepRequest);
        MethodExecutor methodExecutor = mock(MethodExecutor.class);
        Method fooBarMethod = this.getClass().getMethod("fooBar", int.class, String.class);
        Spec.ProtoExecutionResult result = executionStage.executeStepMethod(methodExecutor, fooBarMethod);

        verify(methodExecutor, times(0)).execute(fooBarMethod, 1, "foo");
        assertEquals(result.getErrorMessage(), "Failed to convert argument from type String to type int. For input string: \"a\"");
    }

    public void testStepMethodExecutionWithCOFOnRuntimeException() throws Exception {
        Messages.ExecuteStepRequest executeStepRequest = Messages.ExecuteStepRequest.newBuilder().setParsedStepText("hello").setActualStepText("hello").build();
        StepExecutionStage executionStage = new StepExecutionStage(executeStepRequest);
        MethodExecutor methodExecutor = new MethodExecutor();
        Method fooMethod = this.getClass().getMethod("foo");
        Spec.ProtoExecutionResult result = executionStage.executeStepMethod(methodExecutor, fooMethod);

        assertEquals(result.getFailed(), true);
        assertEquals(result.getRecoverableError(), true);
        assertEquals(result.getErrorMessage(), "java.lang.RuntimeException: my exception");
    }

    public void testStepMethodExecutionWithCOFOnAssertionFailure() throws Exception {
        Messages.ExecuteStepRequest executeStepRequest = Messages.ExecuteStepRequest.newBuilder().setParsedStepText("hello").setActualStepText("hello").build();
        StepExecutionStage executionStage = new StepExecutionStage(executeStepRequest);
        MethodExecutor methodExecutor = new MethodExecutor();
        Method fooMethod = this.getClass().getMethod("bar");
        Spec.ProtoExecutionResult result = executionStage.executeStepMethod(methodExecutor, fooMethod);

        assertEquals(true, result.getFailed());
        assertEquals(true, result.getRecoverableError());
        assertEquals("java.lang.AssertionError: assertion failed", result.getErrorMessage());
    }

    public void testStepMethodExecutionWithCOFOnErrorNotWhitelisted() throws Exception {
        Messages.ExecuteStepRequest executeStepRequest = Messages.ExecuteStepRequest.newBuilder().setParsedStepText("hello").setActualStepText("hello").build();
        StepExecutionStage executionStage = new StepExecutionStage(executeStepRequest);
        MethodExecutor methodExecutor = new MethodExecutor();
        Method fooMethod = this.getClass().getMethod("barfoo");
        Spec.ProtoExecutionResult result = executionStage.executeStepMethod(methodExecutor, fooMethod);

        assertEquals(true, result.getFailed());
        assertEquals(false, result.getRecoverableError());
        assertEquals("java.lang.RuntimeException: not recoverable!", result.getErrorMessage());
    }

    public void testStepMethodExecutionWithEnumParamIsExecutingTheStep() throws Exception {
        Spec.Parameter anEnumParam = Spec.Parameter.newBuilder().setValue(anEnum.FIRST.name()).setName("enum").setParameterType(Spec.Parameter.ParameterType.Static).build();
        Messages.ExecuteStepRequest executeStepRequest = Messages.ExecuteStepRequest.newBuilder().setParsedStepText("Test an enum parameter: {}").setActualStepText("Test an enum parameter: <anEnumValue>").addParameters(anEnumParam).build();
        StepExecutionStage executionStage = new StepExecutionStage(executeStepRequest);
        MethodExecutor methodExecutor = mock(MethodExecutor.class);
        Method fooBarWithEnumMethod = this.getClass().getMethod("fooBarWithEnumParameter", anEnum.class);
        executionStage.executeStepMethod(methodExecutor, fooBarWithEnumMethod);

        verify(methodExecutor, times(1)).execute(fooBarWithEnumMethod, anEnum.FIRST);
    }

    public void testStepMethodExecutionWithCOFOnErrorWhitelisted() throws Exception {
        Messages.ExecuteStepRequest executeStepRequest = Messages.ExecuteStepRequest.newBuilder().setParsedStepText("hello").setActualStepText("hello").build();
        StepExecutionStage executionStage = new StepExecutionStage(executeStepRequest);
        MethodExecutor methodExecutor = new MethodExecutor();
        Method fooMethod = this.getClass().getMethod("ding");
        Spec.ProtoExecutionResult result = executionStage.executeStepMethod(methodExecutor, fooMethod);

        assertEquals(true, result.getFailed());
        assertEquals(true, result.getRecoverableError());
        assertEquals("java.lang.RuntimeException: recoverable!", result.getErrorMessage());
    }

    public void testFailingStepMethodExecutionWithNoCOF() throws Exception {
        Messages.ExecuteStepRequest executeStepRequest = Messages.ExecuteStepRequest.newBuilder().setParsedStepText("hello").setActualStepText("hello").build();
        StepExecutionStage executionStage = new StepExecutionStage(executeStepRequest);
        MethodExecutor methodExecutor = new MethodExecutor();
        Method fooMethod = this.getClass().getMethod("noCOF");
        Spec.ProtoExecutionResult result = executionStage.executeStepMethod(methodExecutor, fooMethod);

        assertEquals(true, result.getFailed());
        assertEquals(false, result.getRecoverableError());
        assertEquals("java.lang.RuntimeException: my exception", result.getErrorMessage());
    }

    @ContinueOnFailure
    public void foo() {
        throw new RuntimeException("my exception");
    }

    @ContinueOnFailure
    public void bar() {
        throw new AssertionError("assertion failed");
    }

    @ContinueOnFailure(AssertionError.class)
    public void barfoo(){
        throw new RuntimeException("not recoverable!");
    }

    @ContinueOnFailure(RuntimeException.class)
    public void ding(){
        throw new RuntimeException("recoverable!");
    }

    public void noCOF() {
        throw new RuntimeException("my exception");
    }

    public void fooBar() {
        // Test methods checking methodExecutor
    }

    public Object fooBar(int i, String hello) {
        // Test methods checking methodExecutor with params
        return null;
    }

    public void fooBarWithEnumParameter(anEnum anEnumValue) {
        // Implementation goes here
    }
    
    public Object table(Object table) {
        // Test methods checking methodExecutor with params
        return null;
    }
}
