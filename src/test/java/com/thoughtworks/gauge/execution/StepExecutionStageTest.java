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


    public void fooBar() {
        // Test methods checking methodExecutor
    }

    public Object fooBar(int i, String hello) {
        // Test methods checking methodExecutor with params
        return null;
    }
    
    public Object table(Object table) {
        // Test methods checking methodExecutor with params
        return null;
    }
}
