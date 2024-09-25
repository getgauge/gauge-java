/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge.execution;

import com.thoughtworks.gauge.BeforeScenario;
import com.thoughtworks.gauge.ClassInstanceManager;
import com.thoughtworks.gauge.ContinueOnFailure;
import com.thoughtworks.gauge.Operator;
import com.thoughtworks.gauge.hook.Hook;
import gauge.messages.Spec;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class HooksExecutorTest {

    @Test
    public void testHookExecutor() throws Exception {
        final Hook hook1 = new Hook(HooksExecutorTest.TestHook.class.getMethod("foo"), new String[0], Operator.AND);
        HookExecutionStage hookExecutionStage = new HookExecutionStage(new ArrayList<>() {{
            add(hook1);
        }}, new ClassInstanceManager());
        Spec.ProtoExecutionResult prevResult = Spec.ProtoExecutionResult.newBuilder().setFailed(false).setExecutionTime(0).build();
        Spec.ProtoExecutionResult result = hookExecutionStage.execute(prevResult);
        assertFalse(result.getRecoverableError());
    }

    private static class TestHook {
        @ContinueOnFailure
        @BeforeScenario
        public void foo() {
        }
    }
}
