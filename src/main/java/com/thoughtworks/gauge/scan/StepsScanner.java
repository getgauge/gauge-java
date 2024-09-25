/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge.scan;

import com.thoughtworks.gauge.*;
import com.thoughtworks.gauge.registry.StepRegistry;
import org.reflections.Reflections;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

/**
 * Scans for step implementations.
 */
public class StepsScanner implements IScanner {
    private final StepRegistry registry;

    public StepsScanner(StepRegistry registry) {
        this.registry = registry;
    }

    public void scan(Reflections reflections) {
        Logger.debug("Scanning packages for steps");
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
                            Logger.debug("Found " + stepText + " in current project scope.");
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
        Logger.debug("Loading " + stepText + "via reflected sources.");
        List<String> parameters = stepsUtil.getParameters(parameterizedStep);
        StepValue stepValue = new StepValue(stepText, parameterizedStep, parameters);
        registry.addStepImplementation(stepValue, method, true);
    }
}
