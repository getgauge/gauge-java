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

        HooksRegistry.setBeforeSuiteHooks(createSet(beforeSuite));
        HooksRegistry.setAfterSuiteHooks(createSet(afterSuite));

        Set<Hook> beforeSuiteHooks = HooksRegistry.getBeforeSuiteHooks();
        assertEquals(1, beforeSuiteHooks.size());
        assertEquals(beforeSuite, ((Hook) beforeSuiteHooks.toArray()[0]).getMethod());
        assertEquals(0, ((Hook) beforeSuiteHooks.toArray()[0]).getTags().size());

        Set<Hook> afterSuiteHooks = HooksRegistry.getAfterSuiteHooks();
        assertEquals(1, beforeSuiteHooks.size());
        assertEquals(afterSuite, ((Hook) afterSuiteHooks.toArray()[0]).getMethod());
        assertEquals(0, ((Hook) afterSuiteHooks.toArray()[0]).getTags().size());
    }

    public void testAddingHooksBeforeAndAfterSpecAndScenario() throws Exception {
        Method beforeSpec = TestHooksImplClass.class.getMethod("beforeSpec");
        Method afterSpec = TestHooksImplClass.class.getMethod("afterSpec");
        Method beforeScenario = TestHooksImplClass.class.getMethod("beforeScenario");
        Method afterScenario = TestHooksImplClass.class.getMethod("afterScenario");

        HooksRegistry.setBeforeScenarioHooks(createSet(beforeScenario));
        HooksRegistry.setAfterScenarioHooks(createSet(afterScenario));
        HooksRegistry.setBeforeSpecHooks(createSet(beforeSpec));
        HooksRegistry.setAfterSpecHooks(createSet(afterSpec));

        Set<Hook> beforeSpecHooks = HooksRegistry.getBeforeSpecHooks();
        assertEquals(1, beforeSpecHooks.size());
        assertEquals(beforeSpec, ((Hook) beforeSpecHooks.toArray()[0]).getMethod());
        List<String> beforeSpecTags = ((Hook) beforeSpecHooks.toArray()[0]).getTags();
        assertEquals(1, beforeSpecTags.size());
        assertTrue(beforeSpecTags.contains("specTag"));

        Set<Hook> afterSpecHooks = HooksRegistry.getAfterSpecHooks();
        assertEquals(1, afterSpecHooks.size());
        assertEquals(afterSpec, ((Hook) afterSpecHooks.toArray()[0]).getMethod());
        assertEquals(0, ((Hook) afterSpecHooks.toArray()[0]).getTags().size());

        Set<Hook> beforeScenarioHooks = HooksRegistry.getBeforeScenarioHooks();
        assertEquals(1, beforeScenarioHooks.size());
        assertEquals(beforeScenario, ((Hook) beforeScenarioHooks.toArray()[0]).getMethod());
        assertEquals(0, ((Hook) beforeScenarioHooks.toArray()[0]).getTags().size());
        assertEquals(Operator.AND, ((Hook) beforeScenarioHooks.toArray()[0]).getTagsAggregation());

        Set<Hook> afterScenarioHooks = HooksRegistry.getAfterScenarioHooks();
        assertEquals(1, afterScenarioHooks.size());
        assertEquals(afterScenario, ((Hook) afterScenarioHooks.toArray()[0]).getMethod());
        List<String> afterScenarioTags = ((Hook) afterScenarioHooks.toArray()[0]).getTags();
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

        HooksRegistry.setBeforeStepHooks(createSet(beforeStep1, beforeStep2));
        HooksRegistry.setAfterStepHooks(createSet(afterStep));

        Set<Hook> beforeStepHooks = HooksRegistry.getBeforeStepHooks();
        assertEquals(2, beforeStepHooks.size());

        Set<Hook> afterStepHooks = HooksRegistry.getAfterStepHooks();
        assertEquals(1, afterStepHooks.size());
        assertEquals(afterStep, ((Hook) afterStepHooks.toArray()[0]).getMethod());
        assertEquals(1, ((Hook) afterStepHooks.toArray()[0]).getTags().size());
        assertTrue(((Hook) afterStepHooks.toArray()[0]).getTags().contains("afterStep"));
        assertEquals(Operator.OR, ((Hook) afterStepHooks.toArray()[0]).getTagsAggregation());
    }

    public void testAddingBeforeAndAfterClassStepHooks() throws Exception {
        Method beforeClassSteps1 = TestHooksImplClass.class.getMethod("beforeClassSteps1");
        Method beforeClassSteps2 = TestHooksImplClass.class.getMethod("beforeClassSteps2");
        Method afterClassSteps = TestHooksImplClass.class.getMethod("afterClassSteps");
        HooksRegistry.setBeforeClassStepsHooks(createSet(beforeClassSteps1, beforeClassSteps2));
        HooksRegistry.setAfterClassStepsHooks(createSet(afterClassSteps));

        Set<Hook> beforeHooks = HooksRegistry.getBeforeClassStepsHooksOfClass(TestHooksImplClass.class);
        assertEquals(2, beforeHooks.size());
        Set<Method> beforeMethods = hooksMethodList(beforeHooks);
        assertTrue(beforeMethods.contains(beforeClassSteps1));
        assertTrue(beforeMethods.contains(beforeClassSteps2));

        HashSet<Hook> afterHooks = HooksRegistry.getAfterClassStepsHooksOfClass(TestHooksImplClass.class);
        Set<Method> afterHookMethods = hooksMethodList(afterHooks);
        assertEquals(1, afterHooks.size());
        assertTrue(afterHookMethods.contains(afterClassSteps));
    }

    private Set<Method> hooksMethodList(Set<Hook> hooks) {
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

        @AfterScenario(tags = {"scenarioTag1", "scenarioTag2", "scenarioTag3"}, tagAggregation = Operator.OR)
        public void afterScenario() {

        }

        @BeforeSpec(tags = {"specTag"})
        public void beforeSpec() {

        }

        @AfterSpec()
        public void afterSpec() {

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