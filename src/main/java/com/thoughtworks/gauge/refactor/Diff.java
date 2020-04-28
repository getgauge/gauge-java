/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge.refactor;

import com.github.javaparser.Range;

public class Diff {
    private String text;
    private Range range;

    public Diff(String text, Range range) {
        this.text = text;
        this.range = range;
    }

    public String getText() {
        return text;
    }

    public Range getRange() {
        return range;
    }
}
