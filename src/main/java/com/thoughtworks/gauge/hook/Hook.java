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

package com.thoughtworks.gauge.hook;

import com.thoughtworks.gauge.Operator;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Hook implements Comparable<Hook> {
    private Method method;
    private List<String> tags = new ArrayList<String>();
    private Operator operator = Operator.AND;

    public Hook(Method method, String[] tags, Operator operator) {
        this.method = method;
        this.tags = Arrays.asList(tags);
        this.operator = operator;
    }

    public Hook(Method method) {
        this.method = method;
    }

    public Method getMethod() {
        return method;
    }

    public List<String> getTags() {
        return tags;
    }

    public Operator getTagsAggregation() {
        return operator;
    }

    public boolean isTagged(){
        return !tags.isEmpty();
    }

    @Override
    public int compareTo(Hook h) {
        if (this.isTagged() && !h.isTagged()) {
            return 1;
        }
        if (!this.isTagged() && h.isTagged()) {
            return -1;
        }
        return this.getMethod().getName().compareTo(h.getMethod().getName());
    }
}
