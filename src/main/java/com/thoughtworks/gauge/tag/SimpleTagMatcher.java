/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge.tag;

import java.util.List;

public interface SimpleTagMatcher {
    boolean isMatch(List<String> tagsToMatch, List<String> tagsToMatchWith);
}
