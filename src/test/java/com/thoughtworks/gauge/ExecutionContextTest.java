package com.thoughtworks.gauge;

import junit.framework.TestCase;

import java.util.Arrays;
import java.util.List;

public class ExecutionContextTest extends TestCase {

    public void testGettingAllScenarioAndSpecTags() throws Exception {
        String tag1 = "tag1";
        String tag2 = "tag2";
        String tag3 = "tag3";
        String tag4 = "tag4";
        String tag5 = "tag5";

        ExecutionContext executionContext = new ExecutionContext(specWithTags(tag1, tag2), scenarioWithTags(tag3, tag4, tag5), new StepDetails());
        List<String> allTags = executionContext.getAllTags();
        assertEquals(5, allTags.size());
        assertTrue(allTags.contains(tag1));
        assertTrue(allTags.contains(tag2));
        assertTrue(allTags.contains(tag3));
        assertTrue(allTags.contains(tag4));
        assertTrue(allTags.contains(tag5));
    }

    public void testGettingAllScenarioAndSpecTagsWhenOnlySpecTagsPresent() throws Exception {
        String tag1 = "tag1";
        String tag2 = "tag2";
        String tag3 = "tag3";

        ExecutionContext executionContext = new ExecutionContext(specWithTags(tag1, tag2, tag3, tag3), new Scenario(), new StepDetails());
        List<String> allTags = executionContext.getAllTags();
        assertEquals(3, allTags.size());
        assertTrue(allTags.contains(tag1));
        assertTrue(allTags.contains(tag2));
        assertTrue(allTags.contains(tag3));
    }

    public void testGettingAllScenarioAndSpecTagsWhenOnlyScenarioTagsPresent() throws Exception {
        String tag2 = "tag2";
        String tag3 = "tag3";

        ExecutionContext executionContext = new ExecutionContext(new Specification(), scenarioWithTags(tag3, tag2, tag3), new StepDetails());
        List<String> allTags = executionContext.getAllTags();
        assertEquals(2, allTags.size());
        assertTrue(allTags.contains(tag2));
        assertTrue(allTags.contains(tag3));
    }

    private Specification specWithTags(String... tags) {
        List<String> tagsList = Arrays.asList(tags);
        return new Specification("foo", "foo.spec", false, tagsList);
    }

    private Scenario scenarioWithTags(String... tags) {
        List<String> tagsList = Arrays.asList(tags);
        return new Scenario("foo", false, tagsList);
    }
}