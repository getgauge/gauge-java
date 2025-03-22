/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
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
import com.thoughtworks.gauge.registry.HooksRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;

/**
 * Scans for all Execution Hooks.
 */
public class HooksScanner implements IScanner {

    private static final Logger LOGGER = LogManager.getLogger(HooksScanner.class);

    public void scan(Reflections reflections) {
        LOGGER.debug("Scanning packages for hooks");
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
