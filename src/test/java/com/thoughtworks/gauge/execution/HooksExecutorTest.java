/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge.execution;

import com.thoughtworks.gauge.BeforeScenario;
import com.thoughtworks.gauge.ClassInstanceManager;
import com.thoughtworks.gauge.ContinueOnFailure;
import com.thoughtworks.gauge.ExecutionContext;
import com.thoughtworks.gauge.Operator;
import com.thoughtworks.gauge.SkipScenarioException;
import com.thoughtworks.gauge.hook.Hook;
import gauge.messages.Spec;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test
    public void shouldShortCircuitOnSkipScenario() throws Exception {
        Hook skipHook = new Hook(HooksExecutorTest.class.getMethod("hookThatSkips"), new String[0], Operator.AND);
        Hook shouldNotRunHook = new Hook(HooksExecutorTest.class.getMethod("hookThatFails"), new String[0], Operator.AND);
        HooksExecutor executor = new HooksExecutor(
                List.of(skipHook, shouldNotRunHook), new ExecutionContext(), new ClassInstanceManager());
        Spec.ProtoExecutionResult result = executor.execute();
        assertTrue(result.getSkipScenario());
        assertFalse(result.getFailed());
    }

    public void hookThatSkips() {
        throw new SkipScenarioException("skip this scenario");
    }

    public void hookThatFails() {
        throw new RuntimeException("second hook must not run after skip");
    }

    private static class TestHook {
        @ContinueOnFailure
        @BeforeScenario
        public void foo() {
        }
    }
}
