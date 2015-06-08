package com.thoughtworks.gauge.tag;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;

public class OrMatcherTest extends TestCase {

    public void testMatching() throws Exception {
        OrMatcher matcher = new OrMatcher();
        assertTrue(matcher.isMatch(Arrays.asList("tag1", "tag2"), Arrays.asList("tag1", "tag3", "tag4")));
        assertFalse(matcher.isMatch(Arrays.asList("tag1", "tag2"), Arrays.asList("tag5", "tag3", "tag4")));
        assertFalse(matcher.isMatch(Arrays.asList("tag1"), Arrays.asList("tag5", "tag3", "tag4")));
        assertTrue(matcher.isMatch(Arrays.asList("tag1", "tag4", "tag8", "tag9"), Arrays.asList("tag4")));
        assertTrue(matcher.isMatch(Arrays.asList(""), Arrays.asList("")));
        assertTrue(matcher.isMatch(new ArrayList<String>(), new ArrayList<String>()));
        assertFalse(matcher.isMatch(Arrays.asList("tag1"), Arrays.asList("")));
        assertTrue(matcher.isMatch(new ArrayList<String>(), Arrays.asList("tag1", "tag2", "tag3")));
    }
}