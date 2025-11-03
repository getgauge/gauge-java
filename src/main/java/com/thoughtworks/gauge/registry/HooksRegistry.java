/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge.registry;

import com.thoughtworks.gauge.*;
import com.thoughtworks.gauge.hook.Hook;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class HooksRegistry {
    // Names of methods defined in each Hook annotation. Do not rename these methods in any Hook Class.
    private static final String TAGS_METHOD = "tags";
    private static final String TAG_AGGREGATION_METHOD = "tagAggregation";

    private static final Map<Class<?>, HashSet<Hook>> REGISTRY_MAP = new ConcurrentHashMap<>();

    public static List<Hook> getBeforeSpecHooks() {
        return sort(REGISTRY_MAP.get(BeforeSpec.class));
    }

    public static void addBeforeSpecHooks(Set<Method> methods) {
        addHooksWithTags(methods, BeforeSpec.class);
    }

    public static List<Hook> getAfterSpecHooks() {
        return sortReverse(REGISTRY_MAP.get(AfterSpec.class));
    }

    public static void addAfterSpecHooks(Set<Method> methods) {
        addHooksWithTags(methods, AfterSpec.class);
    }

    public static List<Hook> getBeforeScenarioHooks() {
        return sort(REGISTRY_MAP.get(BeforeScenario.class));
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
        return sortReverse(REGISTRY_MAP.get(AfterScenario.class));
    }

    public static void addAfterScenarioHooks(Set<Method> methods) {
        addHooksWithTags(methods, AfterScenario.class);
    }

    public static List<Hook> getBeforeStepHooks() {
        return sort(REGISTRY_MAP.get(BeforeStep.class));
    }

    public static void addBeforeStepHooks(Set<Method> methods) {
        addHooksWithTags(methods, BeforeStep.class);
    }

    public static List<Hook> getAfterStepHooks() {
        return sortReverse(REGISTRY_MAP.get(AfterStep.class));
    }

    public static void setAfterStepHooks(Set<Method> methods) {
        addHooksWithTags(methods, AfterStep.class);
    }

    public static List<Hook> getBeforeSuiteHooks() {
        return sort(REGISTRY_MAP.get(BeforeSuite.class));
    }

    public static void addBeforeSuiteHooks(Set<Method> methods) {
        addHooks(methods, BeforeSuite.class);
    }

    public static List<Hook> getAfterSuiteHooks() {
        return sortReverse(REGISTRY_MAP.get(AfterSuite.class));
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

    private static void addHooks(Set<Method> methods, Class<?> hookClass) {
        REGISTRY_MAP.putIfAbsent(hookClass, new HashSet<>());
        REGISTRY_MAP.get(hookClass).addAll(methods.stream().map(Hook::new).toList());
    }

    private static void addHooksWithTags(Set<Method> methods, Class<? extends Annotation> hookClass) {
        REGISTRY_MAP.putIfAbsent(hookClass, new HashSet<>());
        for (Method method : methods) {
            Annotation annotation = method.getAnnotation(hookClass);
            try {
                //Hack: Invoking methods on the annotation to avoid repeating logic. There is no hierarchy possible in annotations
                String[] tags = (String[]) annotation.getClass().getMethod(TAGS_METHOD).invoke(annotation);
                Operator tagsAggregation = (Operator) annotation.getClass().getMethod(TAG_AGGREGATION_METHOD).invoke(annotation);
                REGISTRY_MAP.get(hookClass).add(new Hook(method, tags, tagsAggregation));
            } catch (Exception e) {
                Logger.warning("Unable to add hooks", e);
            }
        }
    }

    private static List<Hook> getBeforeClassHooks() {
        return sort(REGISTRY_MAP.get(BeforeClassSteps.class));
    }

    private static List<Hook> getAfterClassHooks() {
        return sortReverse(REGISTRY_MAP.get(AfterClassSteps.class));
    }

    static void remove(Class<?> hookType) {
        REGISTRY_MAP.remove(hookType);
    }
}
