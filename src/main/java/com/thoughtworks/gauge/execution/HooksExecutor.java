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

package com.thoughtworks.gauge.execution;

import com.thoughtworks.gauge.ClassInstanceManager;
import com.thoughtworks.gauge.ExecutionContext;
import com.thoughtworks.gauge.Operator;
import com.thoughtworks.gauge.hook.Hook;
import com.thoughtworks.gauge.tag.TagMatcher;
import gauge.messages.Spec;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class HooksExecutor {
    private final List<Hook> hooks;
    private final ExecutionContext info;
    private ClassInstanceManager manager;

    public HooksExecutor(List<Hook> hooks, ExecutionContext executionInfo, ClassInstanceManager manager) {
        this.hooks = hooks;
        this.info = executionInfo;
        this.manager = manager;
    }

    public Spec.ProtoExecutionResult execute() {
        Spec.ProtoExecutionResult result;
        long totalHooksExecutionTime = 0;

        for (Hook hook : hooks) {
            result = new TaggedHookExecutor(hook, info).execute();
            totalHooksExecutionTime += result.getExecutionTime();
            if (result.getFailed()) {
                return Spec.ProtoExecutionResult.newBuilder(result).setExecutionTime(totalHooksExecutionTime).build();
            }
        }
        return Spec.ProtoExecutionResult.newBuilder().setFailed(false).setExecutionTime(totalHooksExecutionTime).build();
    }

    private boolean methodHasArguments(Method method, Object... arg) {
        if (method.getParameterTypes().length != arg.length) {
            return false;
        }
        List<Class> argsClassList = createClassList(arg);
        Class<?>[] parameterTypes = method.getParameterTypes();
        boolean isValid = true;
        for (int i = 0; i < parameterTypes.length; i++) {
            if (!(argsClassList.indexOf(parameterTypes[i]) == i)) {
                isValid = false;
            }
        }
        return isValid;
    }

    private List<Class> createClassList(Object[] objects) {
        ArrayList<Class> classes = new ArrayList<Class>();
        for (Object obj : objects) {
            classes.add(obj.getClass());
        }
        return classes;
    }

    private class TaggedHookExecutor {
        private final Hook hook;
        private final ExecutionContext info;

        TaggedHookExecutor(Hook hook, ExecutionContext info) {
            this.hook = hook;
            this.info = info;
        }

        public Spec.ProtoExecutionResult execute() {
            if (tagsMatch(hook.getTags(), hook.getTagsAggregation(), info.getAllTags())) {
                return executeHook();
            }
            return Spec.ProtoExecutionResult.newBuilder().setExecutionTime(0).setFailed(false).build();
        }

        private boolean tagsMatch(List<String> tags, Operator tagsAggregation, List<String> allTags) {
            return new TagMatcher().isMatch(tags, tagsAggregation, allTags);
        }

        private Spec.ProtoExecutionResult executeHook() {
            MethodExecutor methodExecutor = new MethodExecutor(manager);
            if (methodHasArguments(hook.getMethod(), info)) {
                return methodExecutor.execute(hook.getMethod(), info);
            }
            Spec.ProtoExecutionResult result = methodExecutor.execute(hook.getMethod());
            return result.toBuilder().setRecoverableError(false).build();
        }
    }
}
