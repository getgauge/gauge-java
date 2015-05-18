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

package com.thoughtworks.gauge;

import java.io.File;
import java.lang.reflect.Method;
import java.util.*;

public class StepRegistry {

    private static HashMap<String, StepRegistryEntry> registry = new RegistryMap<String, StepRegistryEntry>();

    public static void addStepImplementation(StepValue stepValue, Method method) {
        registry.put(stepValue.getStepText(), new StepRegistryEntry(stepValue, method));
    }

    public static boolean contains(String stepTemplateText) {
        return registry.containsKey(stepTemplateText);
    }

    public static Method get(String stepTemplateText) {
        return registry.get(stepTemplateText).getMethod();
    }

    public static String getFileName(String stepTemplateText) {
        return registry.get(stepTemplateText).getFileName();
    }

    public static List<String> getAllStepAnnotationTexts() {
        List<String> stepTexts = new ArrayList<String>();
        for (StepRegistryEntry registryEntry : registry.values()) {
            stepTexts.add(registryEntry.getStepValue().getStepAnnotationText());
        }
        return stepTexts;
    }

    public static List<String> getStepAnnotationFor(Set<String> stepTexts) {
        List<String> stepValues = new ArrayList<String>();
        for (String stepText : stepTexts) {
            stepValues.add(getStepAnnotationFor(stepText));
        }
        return stepValues;
    }

    public static String getStepAnnotationFor(String stepTemplateText) {
        for (StepRegistryEntry entry : registry.values()) {
            if (entry.getStepValue().getStepText().equals(stepTemplateText)){
                return entry.getStepValue().getStepAnnotationText();
            }
        }
        return "";
    }

    public static Set<String> getAllAliasAnnotationTextsFor(String stepTemplateText) {
        Method method = get(stepTemplateText);
        HashSet<String> aliases = new HashSet<String>();
        for (Map.Entry<String, StepRegistryEntry> entry : registry.entrySet()) {
            if (entry.getValue().getMethod().equals(method)) {
                aliases.add(entry.getValue().getStepValue().getStepAnnotationText());
            }
        }
        return aliases;
    }

    public static boolean hasAlias(String stepTemplateText) {
        return getStepAnnotationFor(getAllAliasAnnotationTextsFor(stepTemplateText)).size() > 1;
    }

    public static void remove(String stepTemplateText) {
       registry.remove(stepTemplateText);
    }

    private static class StepRegistryEntry {

        private final StepValue stepValue;
        private final Method method;

        public StepRegistryEntry(StepValue stepValue, Method method) {
            this.stepValue = stepValue;
            this.method = method;
        }

        public StepRegistryEntry() {
            stepValue = new StepValue("", "", new ArrayList<String>());
            method = null;
        }

        public StepValue getStepValue() {
            return stepValue;
        }

        public Method getMethod() {
            return method;
        }

        public String getFileName() {
            if (method == null) {
                return "";
            }
            return method.getDeclaringClass().getCanonicalName().replace(".", File.separator) + ".java";
        }
    }

    private static class RegistryMap<T, T1> extends HashMap {

        public Object get(Object o) {
            if (super.get(o) == null) {
                return new StepRegistryEntry();
            }
            return super.get(o);
        }
    }
}
