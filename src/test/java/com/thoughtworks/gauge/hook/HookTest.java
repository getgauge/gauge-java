/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
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
