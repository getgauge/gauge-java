/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge.scan;

import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.StepRegistryEntry;
import com.thoughtworks.gauge.StepValue;
import com.thoughtworks.gauge.Util;
import com.thoughtworks.gauge.registry.StepRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

/**
 * Scans for step implementations.
 */
public class StepsScanner implements IScanner {

    private static final Logger LOGGER = LogManager.getLogger(StepsScanner.class);
    private final StepRegistry registry;

    public StepsScanner(StepRegistry registry) {
        this.registry = registry;
    }

    public void scan(Reflections reflections) {
        LOGGER.debug("Scanning packages for steps");
        Set<Method> stepImplementations = reflections.getMethodsAnnotatedWith(Step.class);
        buildStepRegistry(stepImplementations);
    }

    private void buildStepRegistry(Set<Method> stepImplementations) {
        StepsUtil stepsUtil = new StepsUtil();
        for (Method method : stepImplementations) {
            Step annotation = method.getAnnotation(Step.class);
            if (annotation != null) {
                for (String stepName : annotation.value()) {
                    String parameterizedStep = Util.trimQuotes(stepName);
                    String stepText = stepsUtil.getStepText(parameterizedStep);
                    if (registry.contains(stepText)) {
                        StepRegistryEntry entry = registry.getForCurrentProject(stepText, method);
                        if (entry != null) {
                            LOGGER.trace("Found step '{}' in current project scope.", parameterizedStep);
                            entry.setMethodInfo(method);
                        } else {
                            addExternalStepEntryToRegistry(stepsUtil, method, parameterizedStep, stepText);
                        }
                    } else {
                        addExternalStepEntryToRegistry(stepsUtil, method, parameterizedStep, stepText);
                    }
                }
            }
        }
    }

    private void addExternalStepEntryToRegistry(StepsUtil stepsUtil, Method method, String parameterizedStep, String stepText) {
        LOGGER.trace("Loading step '{}' via reflected sources.", parameterizedStep);
        List<String> parameters = stepsUtil.getParameters(parameterizedStep);
        StepValue stepValue = new StepValue(stepText, parameterizedStep, parameters);
        registry.addStepImplementation(stepValue, method, true);
    }
}
