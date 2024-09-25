/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge.tag;


import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrMatcherTest {

    @Test
    public void testMatching() throws Exception {
        OrMatcher matcher = new OrMatcher();
        assertTrue(matcher.isMatch(Arrays.asList("tag1", "tag2"), Arrays.asList("tag1", "tag3", "tag4")));
        assertFalse(matcher.isMatch(Arrays.asList("tag1", "tag2"), Arrays.asList("tag5", "tag3", "tag4")));
        assertFalse(matcher.isMatch(Arrays.asList("tag1"), Arrays.asList("tag5", "tag3", "tag4")));
        assertTrue(matcher.isMatch(Arrays.asList("tag1", "tag4", "tag8", "tag9"), Arrays.asList("tag4")));
        assertTrue(matcher.isMatch(Arrays.asList(""), Arrays.asList("")));
        assertTrue(matcher.isMatch(new ArrayList<>(), new ArrayList<>()));
        assertFalse(matcher.isMatch(Arrays.asList("tag1"), Arrays.asList("")));
        assertTrue(matcher.isMatch(new ArrayList<>(), Arrays.asList("tag1", "tag2", "tag3")));
    }
}