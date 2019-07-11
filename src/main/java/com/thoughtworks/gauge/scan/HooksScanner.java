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

import com.thoughtworks.gauge.AfterClassSteps;
import com.thoughtworks.gauge.AfterScenario;
import com.thoughtworks.gauge.AfterSpec;
import com.thoughtworks.gauge.AfterStep;
import com.thoughtworks.gauge.AfterSuite;
import com.thoughtworks.gauge.BeforeClassSteps;
import com.thoughtworks.gauge.BeforeScenario;
import com.thoughtworks.gauge.BeforeSpec;
import com.thoughtworks.gauge.BeforeStep;
import com.thoughtworks.gauge.BeforeSuite;
import com.thoughtworks.gauge.Logger;
import com.thoughtworks.gauge.registry.HooksRegistry;
import org.reflections.Reflections;

/**
 * Scans for all Execution Hooks.
 */
public class HooksScanner implements IScanner {

    public void scan(Reflections reflections) {
        Logger.debug("Scanning packages for hooks");
        buildHooksRegistry(reflections);
    }

    private void buildHooksRegistry(Reflections reflections) {
        HooksRegistry.addBeforeSuiteHooks(reflections.getMethodsAnnotatedWith(BeforeSuite.class));
        HooksRegistry.addAfterSuiteHooks(reflections.getMethodsAnnotatedWith(AfterSuite.class));
        HooksRegistry.addBeforeSpecHooks(reflections.getMethodsAnnotatedWith(BeforeSpec.class));
        HooksRegistry.addAfterSpecHooks(reflections.getMethodsAnnotatedWith(AfterSpec.class));
        HooksRegistry.addBeforeScenarioHooks(reflections.getMethodsAnnotatedWith(BeforeScenario.class));
        HooksRegistry.addAfterScenarioHooks(reflections.getMethodsAnnotatedWith(AfterScenario.class));
        HooksRegistry.addBeforeStepHooks(reflections.getMethodsAnnotatedWith(BeforeStep.class));
        HooksRegistry.setAfterStepHooks(reflections.getMethodsAnnotatedWith(AfterStep.class));
        HooksRegistry.addBeforeClassStepsHooks(reflections.getMethodsAnnotatedWith(BeforeClassSteps.class));
        HooksRegistry.addAfterClassStepsHooks(reflections.getMethodsAnnotatedWith(AfterClassSteps.class));
    }
}
