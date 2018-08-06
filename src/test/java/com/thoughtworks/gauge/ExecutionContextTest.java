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