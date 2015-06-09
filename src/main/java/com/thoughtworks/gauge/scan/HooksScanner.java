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
// We would then provide copied of each license in a separate .txt file with the name of the license as the title of the file.package com.thoughtworks.gauge.scan;

package com.thoughtworks.gauge.scan;

import com.thoughtworks.gauge.*;
import com.thoughtworks.gauge.registry.HooksRegistry;
import org.reflections.Reflections;

/**
 * Scans for all Execution Hooks
 */
public class HooksScanner implements IScanner {

    public void scan(Reflections reflections) {
        buildHooksRegistry(reflections);
    }

    private void buildHooksRegistry(Reflections reflections) {
        HooksRegistry.setBeforeSuiteHooks(reflections.getMethodsAnnotatedWith(BeforeSuite.class));
        HooksRegistry.setAfterSuiteHooks(reflections.getMethodsAnnotatedWith(AfterSuite.class));
        HooksRegistry.setBeforeSpecHooks(reflections.getMethodsAnnotatedWith(BeforeSpec.class));
        HooksRegistry.setAfterSpecHooks(reflections.getMethodsAnnotatedWith(AfterSpec.class));
        HooksRegistry.setBeforeScenarioHooks(reflections.getMethodsAnnotatedWith(BeforeScenario.class));
        HooksRegistry.setAfterScenarioHooks(reflections.getMethodsAnnotatedWith(AfterScenario.class));
        HooksRegistry.setBeforeStepHooks(reflections.getMethodsAnnotatedWith(BeforeStep.class));
        HooksRegistry.setAfterStepHooks(reflections.getMethodsAnnotatedWith(AfterStep.class));
        HooksRegistry.setBeforeClassStepsHooks(reflections.getMethodsAnnotatedWith(BeforeClassSteps.class));
        HooksRegistry.setAfterClassStepsHooks(reflections.getMethodsAnnotatedWith(AfterClassSteps.class));
    }
}
