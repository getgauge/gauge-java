/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge.execution;

import com.thoughtworks.gauge.ClassInstanceManager;
import com.thoughtworks.gauge.ContinueOnFailure;
import com.thoughtworks.gauge.Table;
import com.thoughtworks.gauge.execution.parameters.parsers.base.ParameterParsingChain;
import com.thoughtworks.gauge.execution.parameters.parsers.types.EnumParameterParser;
import com.thoughtworks.gauge.registry.StepRegistry;
import com.thoughtworks.gauge.test.AnEnum;
import gauge.messages.Messages;
import gauge.messages.Spec;
import gauge.messages.Spec.ProtoExecutionResult;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class StepExecutionStageTest {
    private static final boolean STEP_FAILED = true;
    private static final String NON_EXISTING_VALUE = "nonExistingValue";

    @Test
    public void testStepMethodExecutionIsCalledWithoutParameters() throws Exception {
        Messages.ExecuteStepRequest executeStepRequest = Messages.ExecuteStepRequest.newBuilder().setParsedStepText("foo bar").setActualStepText("foo bar").build();
        StepExecutionStage executionStage = new StepExecutionStage(executeStepRequest, new ClassInstanceManager(), new ParameterParsingChain(), mock(StepRegistry.class));
        MethodExecutor methodExecutor = mock(MethodExecutor.class);
        Method fooBarMethod = this.getClass().getMethod("fooBar");
        executionStage.executeStepMethod(methodExecutor, fooBarMethod);

        verify(methodExecutor, times(1)).execute(fooBarMethod);

    }

    @Test
    public void testStepMethodExecutionIsCalledWithParameters() throws Exception {
        Spec.Parameter param1 = Spec.Parameter.newBuilder().setValue("1").setName("number").setParameterType(Spec.Parameter.ParameterType.Static).build();
        Spec.Parameter param2 = Spec.Parameter.newBuilder().setValue("foo").setName("string").setParameterType(Spec.Parameter.ParameterType.Special_String).build();
        Messages.ExecuteStepRequest executeStepRequest = Messages.ExecuteStepRequest.newBuilder().setParsedStepText("hello {} world {}").setActualStepText("hello <a> world <b>").addParameters(param1).addParameters(param2).build();
        StepExecutionStage executionStage = new StepExecutionStage(executeStepRequest, new ClassInstanceManager(), new ParameterParsingChain(), mock(StepRegistry.class));
        MethodExecutor methodExecutor = mock(MethodExecutor.class);
        Method fooBarMethod = this.getClass().getMethod("fooBar", int.class, String.class);
        executionStage.executeStepMethod(methodExecutor, fooBarMethod);

        verify(methodExecutor, times(1)).execute(fooBarMethod, 1, "foo");

    }

    @Test
    public void testStepMethodExecutionCanBeCalledAsObjectForSpecialTable() throws Exception {
        Spec.Parameter tableParameter = Spec.Parameter.newBuilder().setValue("table { headers {cells: \"Id\"}rows {cells: \"1\"}}").setName("table").setParameterType(Spec.Parameter.ParameterType.Special_Table).build();
        Messages.ExecuteStepRequest executeStepRequest = Messages.ExecuteStepRequest.newBuilder().setParsedStepText("hello {}").setActualStepText("hello <table>").addParameters(tableParameter).build();
        StepExecutionStage executionStage = new StepExecutionStage(executeStepRequest, new ClassInstanceManager(), new ParameterParsingChain(), mock(StepRegistry.class));
        MethodExecutor methodExecutor = mock(MethodExecutor.class);
        Method tableMethod = this.getClass().getMethod("table", Object.class);
        executionStage.executeStepMethod(methodExecutor, tableMethod);

        verify(methodExecutor, times(1)).execute(eq(tableMethod), isA(Table.class));

    }

    @Test
    public void testStepMethodExecutionWithMethodHavingInvalidParamTypeConversion() throws Exception {
        Spec.Parameter param1 = Spec.Parameter.newBuilder().setValue("a").setName("number").setParameterType(Spec.Parameter.ParameterType.Static).build();
        Spec.Parameter param2 = Spec.Parameter.newBuilder().setValue("foo").setName("string").setParameterType(Spec.Parameter.ParameterType.Static).build();
        Messages.ExecuteStepRequest executeStepRequest = Messages.ExecuteStepRequest.newBuilder().setParsedStepText("hello {} world {}").setActualStepText("hello <a> world <b>").addParameters(param1).addParameters(param2).build();
        StepExecutionStage executionStage = new StepExecutionStage(executeStepRequest, new ClassInstanceManager(), new ParameterParsingChain(), mock(StepRegistry.class));
        MethodExecutor methodExecutor = mock(MethodExecutor.class);
        Method fooBarMethod = this.getClass().getMethod("fooBar", int.class, String.class);
        Spec.ProtoExecutionResult result = executionStage.executeStepMethod(methodExecutor, fooBarMethod);

        verify(methodExecutor, times(0)).execute(fooBarMethod, 1, "foo");
        assertEquals(result.getErrorMessage(), "Failed to convert argument from type String to type int. For input string: \"a\"");
    }

    @Test
    public void testStepMethodExecutionWithCOFOnRuntimeException() throws Exception {
        Messages.ExecuteStepRequest executeStepRequest = Messages.ExecuteStepRequest.newBuilder().setParsedStepText("hello").setActualStepText("hello").build();
        StepExecutionStage executionStage = new StepExecutionStage(executeStepRequest, new ClassInstanceManager(), new ParameterParsingChain(), mock(StepRegistry.class));
        MethodExecutor methodExecutor = new MethodExecutor(new ClassInstanceManager());
        Method fooMethod = this.getClass().getMethod("foo");
        Spec.ProtoExecutionResult result = executionStage.executeStepMethod(methodExecutor, fooMethod);

        assertTrue(result.getFailed());
        assertTrue(result.getRecoverableError());
        assertEquals(result.getErrorMessage(), "java.lang.RuntimeException: my exception");
    }

    @Test
    public void testStepMethodExecutionWithCOFOnAssertionFailure() throws Exception {
        Messages.ExecuteStepRequest executeStepRequest = Messages.ExecuteStepRequest.newBuilder().setParsedStepText("hello").setActualStepText("hello").build();
        StepExecutionStage executionStage = new StepExecutionStage(executeStepRequest, new ClassInstanceManager(), new ParameterParsingChain(), mock(StepRegistry.class));
        MethodExecutor methodExecutor = new MethodExecutor(new ClassInstanceManager());
        Method fooMethod = this.getClass().getMethod("bar");
        Spec.ProtoExecutionResult result = executionStage.executeStepMethod(methodExecutor, fooMethod);

        assertTrue(result.getFailed());
        assertTrue(result.getRecoverableError());
        assertEquals("java.lang.AssertionError: assertion failed", result.getErrorMessage());
    }

    @Test
    public void testStepMethodExecutionWithCOFOnErrorNotWhitelisted() throws Exception {
        Messages.ExecuteStepRequest executeStepRequest = Messages.ExecuteStepRequest.newBuilder().setParsedStepText("hello").setActualStepText("hello").build();
        StepExecutionStage executionStage = new StepExecutionStage(executeStepRequest, new ClassInstanceManager(), new ParameterParsingChain(), mock(StepRegistry.class));
        MethodExecutor methodExecutor = new MethodExecutor(new ClassInstanceManager());
        Method fooMethod = this.getClass().getMethod("barfoo");
        Spec.ProtoExecutionResult result = executionStage.executeStepMethod(methodExecutor, fooMethod);

        assertTrue(result.getFailed());
        assertFalse(result.getRecoverableError());
        assertEquals("java.lang.RuntimeException: not recoverable!", result.getErrorMessage());
    }

    @Test
    public void testStepMethodExecutionWithWrongInputToAnEnumParamIsFailingCorrectly()
            throws Exception {
        Spec.Parameter anEnumParam = Spec.Parameter.newBuilder().setValue(NON_EXISTING_VALUE)
                .setName("enum").setParameterType(Spec.Parameter.ParameterType.Static).build();
        Messages.ExecuteStepRequest executeStepRequest = Messages.ExecuteStepRequest.newBuilder()
                .setParsedStepText("Test an enum parameter: {}")
                .setActualStepText("Test an enum parameter: <anEnumValue>")
                .addParameters(anEnumParam).build();
        StepExecutionStage executionStage = new StepExecutionStage(executeStepRequest, new ClassInstanceManager(), new ParameterParsingChain(), mock(StepRegistry.class));
        MethodExecutor methodExecutor = mock(MethodExecutor.class);
        Method fooBarWithEnumMethod = this.getClass().getMethod("fooBarWithEnumParameter",
                AnEnum.class);
        ProtoExecutionResult result = executionStage.executeStepMethod(methodExecutor,
                fooBarWithEnumMethod);

        assertEquals(STEP_FAILED, result.getFailed());
        assertEquals(String.format(EnumParameterParser.ENUM_VALUE_NOT_FOUND_MESSAGE,
                NON_EXISTING_VALUE, AnEnum.class.getSimpleName()), result.getErrorMessage());
    }

    @Test
    public void testStepMethodExecutionWithEnumParamIsExecutingTheStep() throws Exception {
        Spec.Parameter anEnumParam = Spec.Parameter.newBuilder().setValue(AnEnum.FIRST.name()).setName("enum").setParameterType(Spec.Parameter.ParameterType.Static).build();
        Messages.ExecuteStepRequest executeStepRequest = Messages.ExecuteStepRequest.newBuilder().setParsedStepText("Test an enum parameter: {}").setActualStepText("Test an enum parameter: <anEnumValue>").addParameters(anEnumParam).build();
        StepExecutionStage executionStage = new StepExecutionStage(executeStepRequest, new ClassInstanceManager(), new ParameterParsingChain(), mock(StepRegistry.class));
        MethodExecutor methodExecutor = mock(MethodExecutor.class);
        Method fooBarWithEnumMethod = this.getClass().getMethod("fooBarWithEnumParameter", AnEnum.class);
        executionStage.executeStepMethod(methodExecutor, fooBarWithEnumMethod);

        verify(methodExecutor, times(1)).execute(fooBarWithEnumMethod, AnEnum.FIRST);
    }

    @Test
    public void testStepMethodExecutionWithCOFOnErrorWhitelisted() throws Exception {
        Messages.ExecuteStepRequest executeStepRequest = Messages.ExecuteStepRequest.newBuilder().setParsedStepText("hello").setActualStepText("hello").build();
        StepExecutionStage executionStage = new StepExecutionStage(executeStepRequest, new ClassInstanceManager(), new ParameterParsingChain(), mock(StepRegistry.class));
        MethodExecutor methodExecutor = new MethodExecutor(new ClassInstanceManager());
        Method fooMethod = this.getClass().getMethod("ding");
        Spec.ProtoExecutionResult result = executionStage.executeStepMethod(methodExecutor, fooMethod);

        assertTrue(result.getFailed());
        assertTrue(result.getRecoverableError());
        assertEquals("java.lang.RuntimeException: recoverable!", result.getErrorMessage());
    }

    @Test
    public void testFailingStepMethodExecutionWithNoCOF() throws Exception {
        Messages.ExecuteStepRequest executeStepRequest = Messages.ExecuteStepRequest.newBuilder().setParsedStepText("hello").setActualStepText("hello").build();
        StepExecutionStage executionStage = new StepExecutionStage(executeStepRequest, new ClassInstanceManager(), new ParameterParsingChain(), mock(StepRegistry.class));
        MethodExecutor methodExecutor = new MethodExecutor(new ClassInstanceManager());
        Method fooMethod = this.getClass().getMethod("noCOF");
        Spec.ProtoExecutionResult result = executionStage.executeStepMethod(methodExecutor, fooMethod);

        assertTrue(result.getFailed());
        assertFalse(result.getRecoverableError());
        assertEquals("java.lang.RuntimeException: my exception", result.getErrorMessage());
    }

    @Test
    public void testStepMethodExecutionWithSkipScenarioException() throws Exception {
        Messages.ExecuteStepRequest executeStepRequest = Messages.ExecuteStepRequest.newBuilder()
                .setParsedStepText("skip me")
                .setActualStepText("skip me")
                .build();

        StepExecutionStage executionStage = new StepExecutionStage(
                executeStepRequest,
                new ClassInstanceManager(),
                new ParameterParsingChain(),
                mock(StepRegistry.class)
        );

        MethodExecutor methodExecutor = new MethodExecutor(new ClassInstanceManager());
        Method skipMethod = this.getClass().getMethod("skipScenarioStep");

        Spec.ProtoExecutionResult result = executionStage.executeStepMethod(methodExecutor, skipMethod);

        assertFalse(result.getFailed());
        assertTrue(result.getSkipScenario());
        assertThat(result.getMessageList()).containsExactly("skipping this scenario due to unmet condition");
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
    public void barfoo() {
        throw new RuntimeException("not recoverable!");
    }

    @ContinueOnFailure(RuntimeException.class)
    public void ding() {
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

    public void fooBarWithEnumParameter(AnEnum anEnumValue) {
        // Implementation goes here
    }

    public Object table(Object table) {
        // Test methods checking methodExecutor with params
        return null;
    }

    public void skipScenarioStep() {
        throw new com.thoughtworks.gauge.SkipScenarioException("skipping this scenario due to unmet condition");
    }
}
