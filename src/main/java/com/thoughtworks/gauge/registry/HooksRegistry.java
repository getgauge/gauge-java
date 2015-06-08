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

import com.thoughtworks.gauge.*;
import com.thoughtworks.gauge.hook.Hook;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class HooksRegistry {
    // Names of methods defined in each Hook annotation. Do not rename these methods in any Hook Class.
    public static final String TAGS_METHOD = "tags";
    public static final String TAG_AGGREGATION_METHOD = "tagAggregation";

    private static HashMap<Class, HashSet<Hook>> registryMap = new HashMap<Class, HashSet<Hook>>();

    public static HashSet<Hook> getBeforeSpecHooks() {
        return registryMap.get(BeforeSpec.class);
    }

    public static void setBeforeSpecHooks(Set<Method> methods) {
        addHooksWithTags(methods, BeforeSpec.class);
    }

    public static Set<Hook> getAfterSpecHooks() {
        return registryMap.get(AfterSpec.class);
    }

    public static void setAfterSpecHooks(Set<Method> methods) {
        addHooksWithTags(methods, AfterSpec.class);
    }

    public static Set<Hook> getBeforeScenarioHooks() {
        return registryMap.get(BeforeScenario.class);
    }

    public static void setBeforeScenarioHooks(Set<Method> methods) {
        addHooksWithTags(methods, BeforeScenario.class);
    }

    public static Set<Hook> getAfterScenarioHooks() {
        return registryMap.get(AfterScenario.class);
    }

    public static void setAfterScenarioHooks(Set<Method> methods) {
        addHooksWithTags(methods, AfterScenario.class);
    }

    public static Set<Hook> getBeforeStepHooks() {
        return registryMap.get(BeforeStep.class);
    }

    public static void setBeforeStepHooks(Set<Method> methods) {
        addHooksWithTags(methods, BeforeStep.class);
    }

    public static Set<Hook> getAfterStepHooks() {
        return registryMap.get(AfterStep.class);
    }

    public static void setAfterStepHooks(Set<Method> methods) {
        addHooksWithTags(methods, AfterStep.class);
    }

    public static Set<Hook> getBeforeSuiteHooks() {
        return registryMap.get(BeforeSuite.class);
    }

    public static void setBeforeSuiteHooks(Set<Method> methods) {
        addHooks(methods, BeforeSuite.class);
    }

    public static Set<Hook> getAfterSuiteHooks() {
        return registryMap.get(AfterSuite.class);
    }

    public static void setAfterSuiteHooks(Set<Method> methods) {
        addHooks(methods, AfterSuite.class);
    }

    public static void setAfterClassStepsHooks(Set<Method> methods) {
        addHooksWithTags(methods, AfterClassSteps.class);
    }

    public static void setBeforeClassStepsHooks(Set<Method> methods) {
        addHooksWithTags(methods, BeforeClassSteps.class);
    }

    public static HashSet<Method> getBeforeClassStepsHooksOfClass(Class<?> aClass) {
        HashSet<Method> beforeClassMethods = new HashSet<Method>();
        for (Hook beforeClassStepsHook : getBeforeClassHooks()) {
            if (beforeClassStepsHook.getMethod().getDeclaringClass().equals(aClass)) {
                beforeClassMethods.add(beforeClassStepsHook.getMethod());
            }
        }
        return beforeClassMethods;
    }

    public static HashSet<Method> getAfterClassStepsHooksOfClass(Class<?> aClass) {
        HashSet<Method> afterClassMethods = new HashSet<Method>();
        for (Hook afterClassStepHook : getAfterClassHooks()) {
            if (afterClassStepHook.getMethod().getDeclaringClass().equals(aClass)) {
                afterClassMethods.add(afterClassStepHook.getMethod());
            }
        }
        return afterClassMethods;
    }

    private static void addHooks(Set<Method> methods, Class hookClass) {
        if (!registryMap.containsKey(hookClass)) {
            registryMap.put(hookClass, new HashSet<Hook>());
        }
        for (Method method : methods) {
            registryMap.get(hookClass).add(new Hook(method));
        }
    }

    private static void addHooksWithTags(Set<Method> methods, Class hookClass) {
        if (!registryMap.containsKey(hookClass)) {
            registryMap.put(hookClass, new HashSet<Hook>());
        }
        for (Method method : methods) {
            Annotation annotation = method.getAnnotation(hookClass);
            try {
                //Hack: Invoking methods on the annotation to avoid repeating logic. There is no hierarchy possible in annotations
                String[] tags = (String[]) annotation.getClass().getMethod(TAGS_METHOD).invoke(annotation);
                Operator tagsAggregation = (Operator) annotation.getClass().getMethod(TAG_AGGREGATION_METHOD).invoke(annotation);
                registryMap.get(hookClass).add(new Hook(method, tags, tagsAggregation));
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    private static Set<Hook> getBeforeClassHooks() {
        return registryMap.get(BeforeClassSteps.class);
    }

    private static Set<Hook> getAfterClassHooks() {
        return registryMap.get(AfterClassSteps.class);
    }

    static void remove(Class hookType) {
        registryMap.remove(hookType);
    }
}
