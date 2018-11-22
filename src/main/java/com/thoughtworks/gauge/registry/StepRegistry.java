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

import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.StepRegistryEntry;
import com.thoughtworks.gauge.StepValue;
import com.thoughtworks.gauge.connection.GaugeConnector;
import gauge.messages.Messages;
import gauge.messages.Spec;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.HashMap;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

public class StepRegistry {
    private HashMap<String, List<StepRegistryEntry>> registry = new HashMap<>();
    private GaugeConnector connector;

    public void addStepImplementation(StepValue stepValue, Method method) {
        String stepText = stepValue.getStepText();
        registry.putIfAbsent(stepText, new ArrayList<>());
        registry.get(stepText).add(new StepRegistryEntry(stepValue, method));
    }

    public boolean contains(String stepTemplateText) {
        return registry.containsKey(stepTemplateText);
    }

    public Method getMethod(String stepTemplateText) {
        return getFirstEntry(stepTemplateText).getMethodInfo();
    }

    public StepRegistryEntry get(String stepTemplateText) {
        return getFirstEntry(stepTemplateText);
    }

    private StepRegistryEntry getFirstEntry(String stepTemplateText) {
        return registry.getOrDefault(stepTemplateText, new ArrayList<>()).stream()
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
        Method method = getMethod(stepTemplateText);
        return registry.values().stream().flatMap(Collection::stream)
                .filter(registryEntry -> registryEntry.getMethodInfo().equals(method))
                .map(registryEntry -> registryEntry.getStepValue().getStepAnnotationText()).collect(toSet());
    }

    public boolean hasAlias(String stepTemplateText) {
        return getStepAnnotationFor(getAllAliasAnnotationTextsFor(stepTemplateText)).size() > 1;
    }

    void remove(String stepTemplateText) {
        registry.remove(stepTemplateText);
    }

    public Set<Method> getAllMethods(String stepText) {
        return registry.getOrDefault(stepText, new ArrayList<>()).stream()
                .map(StepRegistryEntry::getMethodInfo)
                .collect(toSet());
    }

    public List<StepRegistryEntry> getAllEntries(String stepText) {
        return registry.get(stepText);
    }

    public void removeSteps(String fileName) {
        HashMap<String, List<StepRegistryEntry>> newRegistry = new HashMap<>();
        for (Map.Entry<String, List<StepRegistryEntry>> entryList : newRegistry.entrySet()) {
            for (StepRegistryEntry entry : entryList.getValue()) {
                if (!entry.getFileName().equals(fileName)) {
                    newRegistry.put(entry.getStepText(), newRegistry.get(entry.getStepText()));
                }
            }
        }
        registry = newRegistry;
    }


    public void addStep(StepValue stepValue, StepRegistryEntry entry) {
        String stepText = stepValue.getStepText();
        registry.putIfAbsent(stepText, new ArrayList<>());
        registry.get(stepText).add(entry);
    }

    public void buildStepRegistry(Set<Method> stepImplementations, GaugeConnector gaugeConnector) {
        for (Method method : stepImplementations) {
            Step annotation = method.getAnnotation(Step.class);
            if (annotation != null) {
                for (String stepName : annotation.value()) {
                    StepValue stepValue = gaugeConnector.getGaugeApiConnection().getStepValue(stepName);
                    addStepImplementation(stepValue, method);
                }
            }
        }
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
