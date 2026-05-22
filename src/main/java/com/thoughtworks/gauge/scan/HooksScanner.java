/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge.scan;

import com.thoughtworks.gauge.*;
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
    }
}
