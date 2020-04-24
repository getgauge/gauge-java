/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge.tag;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;

public class AndMatcherTest extends TestCase {

    public void testIsMatch() throws Exception {
        SimpleTagMatcher matcher = new AndMatcher();
        assertTrue(matcher.isMatch(Arrays.asList("tag1", "tag2"), Arrays.asList("tag1", "tag3", "tag4", "tag2")));
        assertFalse(matcher.isMatch(Arrays.asList("tag1", "tag2"), Arrays.asList("tag5", "tag3", "tag4")));
        assertFalse(matcher.isMatch(Arrays.asList("tag1"), Arrays.asList("tag5", "tag3", "tag4")));
        assertFalse(matcher.isMatch(Arrays.asList("tag1", "tag4", "tag8", "tag9"), Arrays.asList("tag4", "tag8")));
        assertTrue(matcher.isMatch(Arrays.asList(""), Arrays.asList("")));
        assertTrue(matcher.isMatch(new ArrayList<String>(), new ArrayList<String>()));
        assertFalse(matcher.isMatch(Arrays.asList("tag1", "tag3"), new ArrayList<String>()));
        assertTrue(matcher.isMatch(new ArrayList<String>(), Arrays.asList("tag1", "tag2", "tag3")));
    }
}