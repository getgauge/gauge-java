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
import junit.framework.TestCase;

import java.util.ArrayList;

public class HooksExecutorTest extends TestCase {

    public void testHookExecutor() throws Exception {
        final Hook hook1 = new Hook(HooksExecutorTest.TestHook.class.getMethod("foo"), new String[0], Operator.AND);
        HookExecutionStage hookExecutionStage = new HookExecutionStage(new ArrayList<Hook>() {{
            add(hook1);
        }}, new ClassInstanceManager());
        Spec.ProtoExecutionResult prevResult = Spec.ProtoExecutionResult.newBuilder().setFailed(false).setExecutionTime(0).build();
        Spec.ProtoExecutionResult result = hookExecutionStage.execute(prevResult);
        assertFalse(result.getRecoverableError());
    }

    private class TestHook {
        @ContinueOnFailure
        @BeforeScenario
        public void foo() {
        }
    }
}
