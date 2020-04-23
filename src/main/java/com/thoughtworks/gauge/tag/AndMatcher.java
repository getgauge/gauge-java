/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge.tag;

import java.util.List;

public class AndMatcher implements SimpleTagMatcher {
    public boolean isMatch(List<String> tagsToMatch, List<String> tagsToMatchWith) {
        if (tagsToMatch.size() == 0) {
            return true;
        }
        for (String tag : tagsToMatch) {
            if (!tagsToMatchWith.contains(tag)) {
                return false;
            }
        }
        return true;
    }
}
