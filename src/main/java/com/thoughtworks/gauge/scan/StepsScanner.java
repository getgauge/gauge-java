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

package com.thoughtworks.gauge.scan;

import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.StepValue;
import com.thoughtworks.gauge.registry.StepRegistry;
import org.reflections.Reflections;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

/**
 * Scans for step implementations.
 */
public class StepsScanner implements IScanner {
    private StepRegistry registry;

    public StepsScanner(StepRegistry registry) {
        this.registry = registry;
    }

    public void scan(Reflections reflections) {
        Set<Method> stepImplementations = reflections.getMethodsAnnotatedWith(Step.class);
        buildStepRegistry(stepImplementations);
    }

    private void buildStepRegistry(Set<Method> stepImplementations) {
        StepsUtil stepsUtil = new StepsUtil();
        for (Method method : stepImplementations) {
            Step annotation = method.getAnnotation(Step.class);
            if (annotation != null) {
                for (String stepName : annotation.value()) {
                    String parameterizedStep = stepsUtil.trimQuotes(stepName);
                    String stepText = stepsUtil.getStepText(parameterizedStep);
                    List<String> parameters = stepsUtil.getParameters(parameterizedStep);
                    StepValue stepValue = new StepValue(stepText, parameterizedStep, parameters);
                    registry.addStepImplementation(stepValue, method);
                }
            }
        }
    }
}
