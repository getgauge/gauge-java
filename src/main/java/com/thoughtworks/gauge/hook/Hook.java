/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
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

    public boolean isTagged() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Hook hook = (Hook) o;
        return getMethod() != null ? getMethod().equals(hook.getMethod()) : hook.getMethod() == null;
    }

    @Override
    public int hashCode() {
        return getMethod() != null ? getMethod().hashCode() : 0;
    }
}
