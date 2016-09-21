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

package com.thoughtworks.gauge.hook;

import com.thoughtworks.gauge.BeforeScenario;
import com.thoughtworks.gauge.Operator;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class HookTest extends TestCase {

    public void testSortingOfHookCollection() throws NoSuchMethodException {
        final Hook hook1 = new Hook(TestHook.class.getMethod("foo"), new String[0], Operator.AND);
        final Hook hook2 = new Hook(TestHook.class.getMethod("barfoo"), new String[]{"hello"}, Operator.AND);
        final Hook hook3 = new Hook(TestHook.class.getMethod("bar"), new String[0], Operator.AND);
        final Hook hook4 = new Hook(TestHook.class.getMethod("foobar"), new String[]{"hello"}, Operator.AND);

        Set<Hook> hooks = new HashSet<Hook>() {{
            add(hook1);
            add(hook2);
            add(hook3);
            add(hook4);
        }};
        ArrayList<Hook> hooksList = new ArrayList<Hook>(hooks);
        Collections.sort(hooksList);
        assertEquals("bar", hooksList.get(0).getMethod().getName());
        assertEquals("foo", hooksList.get(1).getMethod().getName());
        assertEquals("barfoo", hooksList.get(2).getMethod().getName());
        assertEquals("foobar", hooksList.get(3).getMethod().getName());
    }

    private class TestHook {
        @BeforeScenario
        public void foo() {
        }

        @BeforeScenario
        public void bar() {
        }

        @BeforeScenario(tags = "hello")
        public void foobar() {
        }

        @BeforeScenario(tags = "hello")
        public void barfoo() {
        }
    }
}
