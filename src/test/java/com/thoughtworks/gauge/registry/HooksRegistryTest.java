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

package com.thoughtworks.gauge.registry;

import com.thoughtworks.gauge.*;
import com.thoughtworks.gauge.hook.Hook;
import junit.framework.TestCase;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HooksRegistryTest extends TestCase {

    public void testAddingHooksBeforeAndAfterSuite() throws Exception {
        Method beforeSuite = TestHooksImplClass.class.getMethod("beforeSuite");
        Method afterSuite = TestHooksImplClass.class.getMethod("afterSuite");

        HooksRegistry.addBeforeSuiteHooks(createSet(beforeSuite));
        HooksRegistry.addAfterSuiteHooks(createSet(afterSuite));

        List<Hook> beforeSuiteHooks = HooksRegistry.getBeforeSuiteHooks();
        assertEquals(1, beforeSuiteHooks.size());
        assertEquals(beforeSuite, beforeSuiteHooks.get(0).getMethod());
        assertEquals(0, beforeSuiteHooks.get(0).getTags().size());

        List<Hook> afterSuiteHooks = HooksRegistry.getAfterSuiteHooks();
        assertEquals(1, beforeSuiteHooks.size());
        assertEquals(afterSuite, afterSuiteHooks.get(0).getMethod());
        assertEquals(0, afterSuiteHooks.get(0).getTags().size());
    }

    public void testAddingHooksBeforeAndAfterSpecAndScenario() throws Exception {
        Method beforeSpec = TestHooksImplClass.class.getMethod("beforeSpec");
        Method afterSpec = TestHooksImplClass.class.getMethod("afterSpec", ExecutionContext.class);
        Method beforeScenario = TestHooksImplClass.class.getMethod("beforeScenario");
        Method afterScenario = TestHooksImplClass.class.getMethod("taggedAfterScenario");

        HooksRegistry.addBeforeScenarioHooks(createSet(beforeScenario));
        HooksRegistry.addAfterScenarioHooks(createSet(afterScenario));
        HooksRegistry.addBeforeSpecHooks(createSet(beforeSpec));
        HooksRegistry.addAfterSpecHooks(createSet(afterSpec));

        List<Hook> beforeSpecHooks = HooksRegistry.getBeforeSpecHooks();
        assertEquals(1, beforeSpecHooks.size());
        assertEquals(beforeSpec, beforeSpecHooks.get(0).getMethod());
        assertEquals(1, beforeSpecHooks.get(0).getTags().size());
        assertTrue(beforeSpecHooks.get(0).getTags().contains("specTag"));

        List<Hook> afterSpecHooks = HooksRegistry.getAfterSpecHooks();
        assertEquals(1, afterSpecHooks.size());
        assertEquals(afterSpec, afterSpecHooks.get(0).getMethod());
        assertEquals(0, afterSpecHooks.get(0).getTags().size());

        List<Hook> beforeScenarioHooks = HooksRegistry.getBeforeScenarioHooks();
        assertEquals(1, beforeScenarioHooks.size());
        assertEquals(beforeScenario, beforeScenarioHooks.get(0).getMethod());
        assertEquals(0, beforeScenarioHooks.get(0).getTags().size());
        assertEquals(Operator.AND, beforeScenarioHooks.get(0).getTagsAggregation());

        List<Hook> afterScenarioHooks = HooksRegistry.getAfterScenarioHooks();
        assertEquals(1, afterScenarioHooks.size());
        assertEquals(afterScenario, afterScenarioHooks.get(0).getMethod());
        List<String> afterScenarioTags = afterScenarioHooks.get(0).getTags();
        assertEquals(3, afterScenarioTags.size());
        assertTrue(afterScenarioTags.contains("scenarioTag1"));
        assertTrue(afterScenarioTags.contains("scenarioTag2"));
        assertTrue(afterScenarioTags.contains("scenarioTag3"));
        assertEquals(Operator.OR, ((Hook) afterScenarioHooks.toArray()[0]).getTagsAggregation());
    }

    public void testAddingBeforeAndAfterStepHooks() throws Exception {
        Method beforeStep1 = TestHooksImplClass.class.getMethod("beforeStep1");
        Method beforeStep2 = TestHooksImplClass.class.getMethod("beforeStep2");
        Method afterStep = TestHooksImplClass.class.getMethod("afterStep");

        HooksRegistry.addBeforeStepHooks(createSet(beforeStep1, beforeStep2));
        HooksRegistry.setAfterStepHooks(createSet(afterStep));

        List<Hook> beforeStepHooks = HooksRegistry.getBeforeStepHooks();
        assertEquals(2, beforeStepHooks.size());

        List<Hook> afterStepHooks = HooksRegistry.getAfterStepHooks();
        assertEquals(1, afterStepHooks.size());
        assertEquals(afterStep, afterStepHooks.get(0).getMethod());
        assertEquals(1, afterStepHooks.get(0).getTags().size());
        assertTrue(((Hook) afterStepHooks.toArray()[0]).getTags().contains("afterStep"));
        assertEquals(Operator.OR, ((Hook) afterStepHooks.toArray()[0]).getTagsAggregation());
    }

    public void testAddingBeforeAndAfterClassStepHooks() throws Exception {
        Method beforeClassSteps1 = TestHooksImplClass.class.getMethod("beforeClassSteps1");
        Method beforeClassSteps2 = TestHooksImplClass.class.getMethod("beforeClassSteps2");
        Method afterClassSteps = TestHooksImplClass.class.getMethod("afterClassSteps");
        HooksRegistry.addBeforeClassStepsHooks(createSet(beforeClassSteps1, beforeClassSteps2));
        HooksRegistry.addAfterClassStepsHooks(createSet(afterClassSteps));

        List<Hook> beforeHooks = HooksRegistry.getBeforeClassStepsHooksOfClass(TestHooksImplClass.class);
        assertEquals(2, beforeHooks.size());
        Set<Method> beforeMethods = hooksMethodList(beforeHooks);
        assertTrue(beforeMethods.contains(beforeClassSteps1));
        assertTrue(beforeMethods.contains(beforeClassSteps2));

        List<Hook> afterHooks = HooksRegistry.getAfterClassStepsHooksOfClass(TestHooksImplClass.class);
        Set<Method> afterHookMethods = hooksMethodList(afterHooks);
        assertEquals(1, afterHooks.size());
        assertTrue(afterHookMethods.contains(afterClassSteps));
    }

    public void testSortingOfPreHooks() throws NoSuchMethodException {
        Method beforeScenario = TestHooksImplClass.class.getMethod("beforeScenario");
        Method aBeforeScenario = TestHooksImplClass.class.getMethod("aBeforeScenario");
        Method taggedBeforeScenario = TestHooksImplClass.class.getMethod("taggedBeforeScenario");
        HooksRegistry.addBeforeScenarioHooks(createSet(beforeScenario, aBeforeScenario));
        HooksRegistry.addBeforeScenarioHooks(createSet(taggedBeforeScenario));

        List<Hook> sortedPreHooks = HooksRegistry.getBeforeScenarioHooks();
        assertEquals("aBeforeScenario", sortedPreHooks.get(0).getMethod().getName());
        assertEquals("beforeScenario", sortedPreHooks.get(1).getMethod().getName());
        assertEquals("taggedBeforeScenario", sortedPreHooks.get(2).getMethod().getName());
    }

    public void testSortingOfPostHooks() throws NoSuchMethodException {
        Method afterScenario = TestHooksImplClass.class.getMethod("afterScenario");
        Method taggedAfterScenario = TestHooksImplClass.class.getMethod("taggedAfterScenario");
        Method secondAfterScenario = TestHooksImplClass.class.getMethod("secondAfterScenario");
        HooksRegistry.addAfterScenarioHooks(createSet(afterScenario, secondAfterScenario));
        HooksRegistry.addAfterScenarioHooks(createSet(taggedAfterScenario));

        List<Hook> sortedPostHooks = HooksRegistry.getAfterScenarioHooks();
        assertEquals("taggedAfterScenario", sortedPostHooks.get(0).getMethod().getName());
        assertEquals("secondAfterScenario", sortedPostHooks.get(1).getMethod().getName());
        assertEquals("afterScenario", sortedPostHooks.get(2).getMethod().getName());
    }

    private Set<Method> hooksMethodList(List<Hook> hooks) {
        HashSet<Method> methods = new HashSet<Method>();
        for (Hook hook : hooks) {
            methods.add(hook.getMethod());
        }
        return methods;
    }

    protected void tearDown() throws Exception {
        HooksRegistry.remove(BeforeStep.class);
        HooksRegistry.remove(AfterStep.class);
        HooksRegistry.remove(BeforeScenario.class);
        HooksRegistry.remove(AfterScenario.class);
        HooksRegistry.remove(BeforeSuite.class);
        HooksRegistry.remove(AfterSuite.class);
        HooksRegistry.remove(BeforeSpec.class);
        HooksRegistry.remove(AfterSpec.class);
        HooksRegistry.remove(AfterClassSteps.class);
        HooksRegistry.remove(BeforeClassSteps.class);
    }

    private HashSet<Method> createSet(Method... methods) {
        HashSet<Method> methodSet = new HashSet<Method>();
        methodSet.addAll(Arrays.asList(methods));
        return methodSet;
    }

    public class TestHooksImplClass {

        @BeforeStep
        public void beforeStep1() {

        }

        @BeforeStep
        public void beforeStep2() {

        }

        @AfterStep(tags = "afterStep", tagAggregation = Operator.OR)
        public void afterStep() {

        }

        @BeforeScenario
        public void beforeScenario() {

        }

        @BeforeScenario
        public void aBeforeScenario() {

        }

        @BeforeScenario(tags = {"tag1"})
        public void taggedBeforeScenario() {

        }

        @AfterScenario(tags = {"scenarioTag1", "scenarioTag2", "scenarioTag3"}, tagAggregation = Operator.OR)
        public void taggedAfterScenario() {

        }

        @AfterScenario
        public void afterScenario() {

        }

        @AfterScenario
        public void secondAfterScenario() {

        }

        @BeforeSpec(tags = {"specTag"})
        public void beforeSpec() {

        }

        @AfterSpec()
        public void afterSpec(ExecutionContext context) {
        }

        @BeforeSuite
        public void beforeSuite() {

        }

        @AfterSuite
        public void afterSuite() {

        }

        @BeforeClassSteps(tags = {"tag1", "tag2"})
        public void beforeClassSteps1() {

        }

        @BeforeClassSteps
        public void beforeClassSteps2() {

        }

        @AfterClassSteps(tags = {"tag3", "tag4"}, tagAggregation = Operator.OR)
        public void afterClassSteps() {

        }
    }

}