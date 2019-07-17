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

import com.thoughtworks.gauge.AfterClassSteps;
import com.thoughtworks.gauge.AfterScenario;
import com.thoughtworks.gauge.AfterSpec;
import com.thoughtworks.gauge.AfterStep;
import com.thoughtworks.gauge.AfterSuite;
import com.thoughtworks.gauge.BeforeClassSteps;
import com.thoughtworks.gauge.BeforeScenario;
import com.thoughtworks.gauge.BeforeSpec;
import com.thoughtworks.gauge.BeforeStep;
import com.thoughtworks.gauge.BeforeSuite;
import com.thoughtworks.gauge.Logger;
import com.thoughtworks.gauge.Operator;
import com.thoughtworks.gauge.hook.Hook;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class HooksRegistry {
    // Names of methods defined in each Hook annotation. Do not rename these methods in any Hook Class.
    public static final String TAGS_METHOD = "tags";
    public static final String TAG_AGGREGATION_METHOD = "tagAggregation";

    private static HashMap<Class, HashSet<Hook>> registryMap = new HashMap<>();

    public static List<Hook> getBeforeSpecHooks() {
        return sort(registryMap.get(BeforeSpec.class));
    }

    public static void addBeforeSpecHooks(Set<Method> methods) {
        addHooksWithTags(methods, BeforeSpec.class);
    }

    public static List<Hook> getAfterSpecHooks() {
        return sortReverse(registryMap.get(AfterSpec.class));
    }

    public static void addAfterSpecHooks(Set<Method> methods) {
        addHooksWithTags(methods, AfterSpec.class);
    }

    public static List<Hook> getBeforeScenarioHooks() {
        return sort(registryMap.get(BeforeScenario.class));
    }

    private static List<Hook> sort(Set<Hook> hooks) {
        return hooks.stream().sorted().collect(toList());
    }

    private static List<Hook> sortReverse(Set<Hook> hooks) {
        return hooks.stream().sorted(Comparator.reverseOrder()).collect(toList());
    }

    public static void addBeforeScenarioHooks(Set<Method> methods) {
        addHooksWithTags(methods, BeforeScenario.class);
    }

    public static List<Hook> getAfterScenarioHooks() {
        return sortReverse(registryMap.get(AfterScenario.class));
    }

    public static void addAfterScenarioHooks(Set<Method> methods) {
        addHooksWithTags(methods, AfterScenario.class);
    }

    public static List<Hook> getBeforeStepHooks() {
        return sort(registryMap.get(BeforeStep.class));
    }

    public static void addBeforeStepHooks(Set<Method> methods) {
        addHooksWithTags(methods, BeforeStep.class);
    }

    public static List<Hook> getAfterStepHooks() {
        return sortReverse(registryMap.get(AfterStep.class));
    }

    public static void setAfterStepHooks(Set<Method> methods) {
        addHooksWithTags(methods, AfterStep.class);
    }

    public static List<Hook> getBeforeSuiteHooks() {
        return sort(registryMap.get(BeforeSuite.class));
    }

    public static void addBeforeSuiteHooks(Set<Method> methods) {
        addHooks(methods, BeforeSuite.class);
    }

    public static List<Hook> getAfterSuiteHooks() {
        return sortReverse(registryMap.get(AfterSuite.class));
    }

    public static void addAfterSuiteHooks(Set<Method> methods) {
        addHooks(methods, AfterSuite.class);
    }

    public static void addAfterClassStepsHooks(Set<Method> methods) {
        addHooksWithTags(methods, AfterClassSteps.class);
    }

    public static void addBeforeClassStepsHooks(Set<Method> methods) {
        addHooksWithTags(methods, BeforeClassSteps.class);
    }

    public static List<Hook> getBeforeClassStepsHooksOfClass(Class<?> aClass) {
        return sort(findClassHooksForClass(getBeforeClassHooks(), aClass));
    }

    public static List<Hook> getAfterClassStepsHooksOfClass(Class<?> aClass) {
        return sortReverse(findClassHooksForClass(getAfterClassHooks(), aClass));
    }

    private static Set<Hook> findClassHooksForClass(List<Hook> allClassHooks, Class<?> aClass) {
        return allClassHooks.stream().filter(hook -> hook.getMethod().getDeclaringClass().equals(aClass)).collect(Collectors.toSet());
    }

    private static void addHooks(Set<Method> methods, Class hookClass) {
        registryMap.putIfAbsent(hookClass, new HashSet<>());
        registryMap.get(hookClass).addAll(methods.stream().map(Hook::new).collect(toList()));
    }

    private static void addHooksWithTags(Set<Method> methods, Class hookClass) {
        registryMap.putIfAbsent(hookClass, new HashSet<>());
        for (Method method : methods) {
            Annotation annotation = method.getAnnotation(hookClass);
            try {
                //Hack: Invoking methods on the annotation to avoid repeating logic. There is no hierarchy possible in annotations
                String[] tags = (String[]) annotation.getClass().getMethod(TAGS_METHOD).invoke(annotation);
                Operator tagsAggregation = (Operator) annotation.getClass().getMethod(TAG_AGGREGATION_METHOD).invoke(annotation);
                registryMap.get(hookClass).add(new Hook(method, tags, tagsAggregation));
            } catch (Exception e) {
                Logger.warning("Unable to add hooks", e);
                continue;
            }
        }
    }

    private static List<Hook> getBeforeClassHooks() {
        return sort(registryMap.get(BeforeClassSteps.class));
    }

    private static List<Hook> getAfterClassHooks() {
        return sortReverse(registryMap.get(AfterClassSteps.class));
    }

    static void remove(Class hookType) {
        registryMap.remove(hookType);
    }
}
