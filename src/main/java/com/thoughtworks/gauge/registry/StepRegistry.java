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

import com.thoughtworks.gauge.StepRegistryEntry;
import com.thoughtworks.gauge.StepValue;
import gauge.messages.Messages;
import gauge.messages.Spec;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.HashMap;

import static java.util.stream.Collectors.toList;

public class StepRegistry {
    private HashMap<String, List<StepRegistryEntry>> registry;

    public StepRegistry() {
        registry = new HashMap<>();
    }

    public void addStepImplementation(StepValue stepValue, Method method) {
        String stepText = stepValue.getStepText();
        registry.putIfAbsent(stepText, new ArrayList<>());
        registry.get(stepText).add(new StepRegistryEntry(stepValue, method));
    }

    public boolean contains(String stepTemplateText) {
        return registry.containsKey(stepTemplateText);
    }

    public StepRegistryEntry get(String stepTemplateText) {
        return getFirstEntry(stepTemplateText);
    }

    private StepRegistryEntry getFirstEntry(String stepTemplateText) {
        return registry.getOrDefault(stepTemplateText, new ArrayList<>()).stream()
                .findFirst()
                .orElse(new StepRegistryEntry());
    }

    public List<String> getAllStepAnnotationTexts() {
        return registry.values().stream().flatMap(Collection::stream)
                .map(entry -> entry.getStepValue().getStepAnnotationText())
                .collect(toList());
    }

    String getStepAnnotationFor(String stepTemplateText) {
        return registry.values().stream().flatMap(Collection::stream).map(StepRegistryEntry::getStepValue)
                .filter(stepValue -> stepValue.getStepText().equals(stepTemplateText))
                .map(StepValue::getStepAnnotationText).findFirst().orElse("");
    }

    public void remove(String stepTemplateText) {
        registry.remove(stepTemplateText);
    }

    List<StepRegistryEntry> getAllEntries(String stepText) {
        return registry.get(stepText);
    }

    public void removeSteps(String fileName) {
        HashMap<String, List<StepRegistryEntry>> newRegistry = new HashMap<>();
        for (String key : registry.keySet()) {
            List<StepRegistryEntry> newEntryList = registry.get(key).stream().filter(entry -> !entry.getFileName().equals(fileName)).collect(toList());
            if (newEntryList.size() > 0) {
                newRegistry.put(key, newEntryList);
            }
        }
        registry = newRegistry;
    }


    public void addStep(StepValue stepValue, StepRegistryEntry entry) {
        String stepText = stepValue.getStepText();
        registry.putIfAbsent(stepText, new ArrayList<>());
        registry.get(stepText).add(entry);
    }

    public boolean hasMultipleImplementations(String stepToValidate) {
        return getAllEntries(stepToValidate).size() > 1;
    }

    public List<Messages.StepPositionsResponse.StepPosition> getStepPositions(String filePath) {
        List<Messages.StepPositionsResponse.StepPosition> stepPositionsList = new ArrayList<>();

        for (Map.Entry<String, List<StepRegistryEntry>> entryList : registry.entrySet()) {
            for (StepRegistryEntry entry : entryList.getValue()) {
                if (entry.getFileName().equals(filePath)) {
                    Messages.StepPositionsResponse.StepPosition stepPosition = Messages.StepPositionsResponse.StepPosition.newBuilder()
                            .setStepValue(entryList.getKey())
                            .setSpan(Spec.Span.newBuilder()
                                    .setStart(entry.getSpan().begin.line)
                                    .setStartChar(entry.getSpan().begin.column)
                                    .setEnd(entry.getSpan().end.line)
                                    .setEndChar(entry.getSpan().end.column).build())
                            .build();
                    stepPositionsList.add(stepPosition);
                }
            }
        }

        return stepPositionsList;
    }
}
