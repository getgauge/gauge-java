/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge.tag;

import com.thoughtworks.gauge.Operator;

import java.util.List;

public class TagMatcher {
    public boolean isMatch(List<String> tags, Operator operator, List<String> allTags) {
        return new TagMatcherFactory().matcherFor(operator).isMatch(tags, allTags);
    }

    private class TagMatcherFactory {
        public SimpleTagMatcher matcherFor(Operator operator) {
            switch (operator) {
                case OR:
                    return new OrMatcher();
                case AND:
                    return new AndMatcher();
                default:
                    return new AndMatcher();
            }
        }
    }
}
