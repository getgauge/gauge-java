// Copyright 2015 ThoughtWorks, Inc.

// This file is part of Gauge-Java.

// Gauge-Java is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.

// Gauge-Java is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.

// You should have received a copy of the GNU General Public License
// along with Gauge-Java.  If not, see <http://www.gnu.org/licenses/>.

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
