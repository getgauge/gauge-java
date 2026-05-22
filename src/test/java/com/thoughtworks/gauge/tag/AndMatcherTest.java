/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge.tag;


import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AndMatcherTest {

    @Test
    public void testIsMatch() throws Exception {
        SimpleTagMatcher matcher = new AndMatcher();
        assertTrue(matcher.isMatch(List.of("tag1", "tag2"), List.of("tag1", "tag3", "tag4", "tag2")));
        assertFalse(matcher.isMatch(List.of("tag1", "tag2"), List.of("tag5", "tag3", "tag4")));
        assertFalse(matcher.isMatch(List.of("tag1"), List.of("tag5", "tag3", "tag4")));
        assertFalse(matcher.isMatch(List.of("tag1", "tag4", "tag8", "tag9"), List.of("tag4", "tag8")));
        assertTrue(matcher.isMatch(List.of(""), List.of("")));
        assertTrue(matcher.isMatch(new ArrayList<>(), new ArrayList<>()));
        assertFalse(matcher.isMatch(List.of("tag1", "tag3"), new ArrayList<>()));
        assertTrue(matcher.isMatch(new ArrayList<>(), List.of("tag1", "tag2", "tag3")));
    }
}