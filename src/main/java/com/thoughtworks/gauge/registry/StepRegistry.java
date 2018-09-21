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
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;


import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

public class StepRegistry {
    private HashMap<String, Set<StepRegistryEntry>> registry = new HashMap<>();

    public void addStepImplementation(StepValue stepValue, Method method) {
        String stepText = stepValue.getStepText();
        registry.putIfAbsent(stepText, new HashSet<>());
        registry.get(stepText).add(new StepRegistryEntry(stepValue, method));
    }

    public boolean contains(String stepTemplateText) {
        return registry.containsKey(stepTemplateText);
    }

    public Method get(String stepTemplateText) {
        return getFirstEntry(stepTemplateText).getMethod();
    }

    private StepRegistryEntry getFirstEntry(String stepTemplateText) {
        return registry.getOrDefault(stepTemplateText, new HashSet<>()).stream()
                .findFirst()
                .orElse(new StepRegistryEntry());
    }

    public String getFileName(String stepTemplateText) {
        return getFirstEntry(stepTemplateText).getFileName();
    }

    public List<String> getAllStepAnnotationTexts() {
        return registry.values().stream().flatMap(Collection::stream)
                .map(entry -> entry.getStepValue().getStepAnnotationText())
                .collect(toList());
    }

    private List<String> getStepAnnotationFor(Set<String> stepTexts) {
        List<String> annotations = new ArrayList<>();
        for (String stepText : stepTexts) {
            annotations.add(this.getStepAnnotationFor(stepText));
        }
        return annotations;
    }

    String getStepAnnotationFor(String stepTemplateText) {
        return registry.values().stream().flatMap(Collection::stream).map(StepRegistryEntry::getStepValue)
                .filter(stepValue -> stepValue.getStepText().equals(stepTemplateText))
                .map(StepValue::getStepAnnotationText).findFirst().orElse("");
    }

    public Set<String> getAllAliasAnnotationTextsFor(String stepTemplateText) {
        Method method = get(stepTemplateText);
        return registry.values().stream().flatMap(Collection::stream)
                .filter(registryEntry -> registryEntry.getMethod().equals(method))
                .map(registryEntry -> registryEntry.getStepValue().getStepAnnotationText()).collect(toSet());
    }

    public boolean hasAlias(String stepTemplateText) {
        return getStepAnnotationFor(getAllAliasAnnotationTextsFor(stepTemplateText)).size() > 1;
    }

    void remove(String stepTemplateText) {
        registry.remove(stepTemplateText);
    }

    public Set<Method> getAll(String stepText) {
        return registry.getOrDefault(stepText, new HashSet<>()).stream()
                .map(StepRegistryEntry::getMethod)
                .collect(toSet());
    }

    private class StepRegistryEntry {
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
