package com.thoughtworks.gauge.execution;

import gauge.messages.Messages;
import gauge.messages.Spec;
import junit.framework.TestCase;

import java.lang.reflect.Method;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

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


    public void fooBar() {
        // Test methods checking methodExecutor
    }

    public Object fooBar(int i, String hello) {
        // Test methods checking methodExecutor with params
        return null;
    }
}