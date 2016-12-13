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

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import com.thoughtworks.gauge.StepValue;

public class StepRegistry {

    private static HashMap<String, Set<StepRegistryEntry>> registry = new RegistryMap<String, Set<StepRegistryEntry>>();

    public static void addStepImplementation(StepValue stepValue, Method method) {
        StepRegistryEntry stepRegistryEntry = new StepRegistryEntry(stepValue, method);
        String stepText = stepValue.getStepText();
        addToRegistry(stepRegistryEntry, stepText);
    }

    private static void addToRegistry(
            StepRegistryEntry stepRegistryEntry,
            String stepText) {

        if (!registry.containsKey(stepText)) {
            registry.put(stepText, new HashSet<StepRegistryEntry>());
        }
        registry.get(stepText).add(stepRegistryEntry);
    }

    public static boolean contains(String stepTemplateText) {
        return registry.containsKey(stepTemplateText);
    }

    public static Method get(String stepTemplateText) {
        return getFirstEntry(stepTemplateText).getMethod();
    }

    private static StepRegistryEntry getFirstEntry(String stepTemplateText) {
        Set<StepRegistryEntry> entries = registry.get(stepTemplateText);
        if (entries.isEmpty()) {
            return new StepRegistryEntry();
        }
        return entries.iterator().next();
    }

    public static String getFileName(String stepTemplateText) {
        return getFirstEntry(stepTemplateText).getFileName();
    }

    public static List<String> getAllStepAnnotationTexts() {
        List<String> stepTexts = new ArrayList<String>();
        for (Set<StepRegistryEntry> entries : registry.values()) {
            for (StepRegistryEntry entry : entries) {
                stepTexts.add(entry.getStepValue().getStepAnnotationText());
            }
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
        for (Set<StepRegistryEntry> entries : registry.values()) {
            for (StepRegistryEntry stepRegistryEntry : entries) {
                StepValue stepValue = stepRegistryEntry.getStepValue();
                if (stepValue.getStepText().equals(stepTemplateText)) {
                    return stepValue.getStepAnnotationText();
                }
            }
        }
        return "";
    }

    public static Set<String> getAllAliasAnnotationTextsFor(String stepTemplateText) {
        Method method = get(stepTemplateText);
        HashSet<String> aliases = new HashSet<String>();
        for (Entry<String, Set<StepRegistryEntry>> entry : registry.entrySet()) {
            Set<StepRegistryEntry> registryEntries = entry.getValue();
            for (StepRegistryEntry registryEntry : registryEntries) {
                if (registryEntry.getMethod().equals(method)) {
                    aliases.add(registryEntry.getStepValue().getStepAnnotationText());
                }
            }
        }
        return aliases;
    }

    public static boolean hasAlias(String stepTemplateText) {
        return getStepAnnotationFor(getAllAliasAnnotationTextsFor(stepTemplateText)).size() > 1;
    }

    static void remove(String stepTemplateText) {
        registry.remove(stepTemplateText);
    }

    public static Set<Method> getAll(String stepText) {
        Set<Method> methods = new HashSet<Method>();
        Set<StepRegistryEntry> entries = registry.get(stepText);
        for (StepRegistryEntry entry : entries) {
            methods.add(entry.getMethod());
        }
        return methods;
    }

    private static class StepRegistryEntry {

        private final StepValue stepValue;
        private final Method method;

        StepRegistryEntry(StepValue stepValue, Method method) {
            this.stepValue = stepValue;
            this.method = method;
        }

        StepRegistryEntry() {
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

    protected static class RegistryMap<T, T1> extends HashMap {

        public Object get(Object o) {
            if (super.get(o) == null) {
                return new HashSet<StepRegistryEntry>();
            }
            return super.get(o);
        }

    }

}
