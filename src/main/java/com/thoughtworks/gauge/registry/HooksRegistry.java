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

package com.thoughtworks.gauge.registry;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class HooksRegistry {
    private static Set<Method> beforeSuiteHooks = new HashSet<Method>();
    private static Set<Method> afterSuiteHooks = new HashSet<Method>();
    private static Set<Method> beforeSpecHooks = new HashSet<Method>();
    private static Set<Method> afterSpecHooks = new HashSet<Method>();
    private static Set<Method> beforeScenarioHooks = new HashSet<Method>();
    private static Set<Method> afterScenarioHooks = new HashSet<Method>();
    private static Set<Method> beforeStepHooks = new HashSet<Method>();
    private static Set<Method> afterStepHooks = new HashSet<Method>();
    private static Set<Method> afterClassStepsHooks = new HashSet<Method>();
    private static Set<Method> beforeClassStepsHooks = new HashSet<Method>();

    public static Set<Method> getBeforeSpecHooks() {
        return beforeSpecHooks;
    }

    public static void setBeforeSpecHooks(Set<Method> beforeSpecHooks) {
        HooksRegistry.beforeSpecHooks = beforeSpecHooks;
    }

    public static Set<Method> getAfterSpecHooks() {
        return afterSpecHooks;
    }

    public static void setAfterSpecHooks(Set<Method> afterSpecHooks) {
        HooksRegistry.afterSpecHooks = afterSpecHooks;
    }

    public static Set<Method> getBeforeScenarioHooks() {
        return beforeScenarioHooks;
    }

    public static void setBeforeScenarioHooks(Set<Method> beforeScenarioHooks) {
        HooksRegistry.beforeScenarioHooks = beforeScenarioHooks;
    }

    public static Set<Method> getAfterScenarioHooks() {
        return afterScenarioHooks;
    }

    public static void setAfterScenarioHooks(Set<Method> afterScenarioHooks) {
        HooksRegistry.afterScenarioHooks = afterScenarioHooks;
    }

    public static Set<Method> getBeforeStepHooks() {
        return beforeStepHooks;
    }

    public static void setBeforeStepHooks(Set<Method> beforeStepHooks) {
        HooksRegistry.beforeStepHooks = beforeStepHooks;
    }

    public static Set<Method> getAfterStepHooks() {
        return afterStepHooks;
    }

    public static void setAfterStepHooks(Set<Method> afterStepHooks) {
        HooksRegistry.afterStepHooks = afterStepHooks;
    }

    public static Set<Method> getBeforeSuiteHooks() {
        return beforeSuiteHooks;
    }

    public static void setBeforeSuiteHooks(Set<Method> beforeSuiteHooks) {
        HooksRegistry.beforeSuiteHooks = beforeSuiteHooks;
    }

    public static Set<Method> getAfterSuiteHooks() {
        return afterSuiteHooks;
    }

    public static void setAfterSuiteHooks(Set<Method> afterSuiteHooks) {
        HooksRegistry.afterSuiteHooks = afterSuiteHooks;
    }

    public static void setAfterClassStepsHooks(Set<Method> afterClassStepsHooks) {
        HooksRegistry.afterClassStepsHooks = afterClassStepsHooks;
    }

    public static void setBeforeClassStepsHooks(Set<Method> beforeClassStepsHooks) {
        HooksRegistry.beforeClassStepsHooks = beforeClassStepsHooks;
    }

    public static HashSet<Method> getBeforeClassStepsHooksOfClass(Class<?> aClass) {
        HashSet<Method> beforeClassMethods = new HashSet<Method>();
        for (Method beforeClassStepsHook : beforeClassStepsHooks) {
            if (beforeClassStepsHook.getDeclaringClass().equals(aClass)){
                beforeClassMethods.add(beforeClassStepsHook);
            }
        }
        return beforeClassMethods;
    }

    public static HashSet<Method> getAfterClassStepsHooksOfClass(Class<?> aClass) {
        HashSet<Method> afterClassMethods = new HashSet<Method>();
        for (Method afterClassStepHook : afterClassStepsHooks) {
            if (afterClassStepHook.getDeclaringClass().equals(aClass)){
                afterClassMethods.add(afterClassStepHook);
            }
        }
        return afterClassMethods;
    }
}
