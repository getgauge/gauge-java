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
