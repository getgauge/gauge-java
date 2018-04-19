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

import com.thoughtworks.gauge.StepValue;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

public class StepRegistry {
    private static HashMap<String, Set<StepRegistryEntry>> registry = new HashMap<>();

    public static void addStepImplementation(StepValue stepValue, Method method) {
        String stepText = stepValue.getStepText();
        registry.putIfAbsent(stepText, new HashSet<>());
        registry.get(stepText).add(new StepRegistryEntry(stepValue, method));
    }

    public static boolean contains(String stepTemplateText) {
        return registry.containsKey(stepTemplateText);
    }

    public static Method get(String stepTemplateText) {
        return getFirstEntry(stepTemplateText).getMethod();
    }

    private static StepRegistryEntry getFirstEntry(String stepTemplateText) {
        return registry.getOrDefault(stepTemplateText, new HashSet<>()).stream()
                .findFirst()
                .orElse(new StepRegistryEntry());
    }

    public static String getFileName(String stepTemplateText) {
        return getFirstEntry(stepTemplateText).getFileName();
    }

    public static List<String> getAllStepAnnotationTexts() {
        return registry.values().stream().flatMap(entries -> entries.stream())
                .map(entry -> entry.getStepValue().getStepAnnotationText())
                .collect(toList());
    }

    public static List<String> getStepAnnotationFor(Set<String> stepTexts) {
        return stepTexts.stream().map(StepRegistry::getStepAnnotationFor).collect(toList());
    }

    public static String getStepAnnotationFor(String stepTemplateText) {
        return registry.values().stream().flatMap(entries -> entries.stream()).map(StepRegistryEntry::getStepValue)
                .filter(stepValue -> stepValue.getStepText().equals(stepTemplateText))
                .map(StepValue::getStepAnnotationText).findFirst().orElse("");
    }

    public static Set<String> getAllAliasAnnotationTextsFor(String stepTemplateText) {
        Method method = get(stepTemplateText);
        return registry.values().stream().flatMap(entries -> entries.stream())
                .filter(registryEntry -> registryEntry.getMethod().equals(method))
                .map(registryEntry -> registryEntry.getStepValue().getStepAnnotationText()).collect(toSet());
    }

    public static boolean hasAlias(String stepTemplateText) {
        return getStepAnnotationFor(getAllAliasAnnotationTextsFor(stepTemplateText)).size() > 1;
    }

    static void remove(String stepTemplateText) {
        registry.remove(stepTemplateText);
    }

    public static Set<Method> getAll(String stepText) {
        return registry.getOrDefault(stepText, new HashSet<>()).stream()
                .map(StepRegistryEntry::getMethod)
                .collect(toSet());
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
}
