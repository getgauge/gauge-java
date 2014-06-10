package com.thoughtworks.gauge;

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
}
