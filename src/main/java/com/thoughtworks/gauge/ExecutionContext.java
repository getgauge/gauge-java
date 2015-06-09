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

package com.thoughtworks.gauge;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Gives the information about the current execution at runtime - spec, scenario, step that is running.
 */
public class ExecutionContext {
    private Specification currentSpecification;
    private Scenario currentScenario;
    private StepDetails currentStep;

    public ExecutionContext(Specification specification, Scenario scenario, StepDetails stepDetails) {
        this.currentSpecification = specification;
        this.currentScenario = scenario;
        this.currentStep = stepDetails;
    }

    public ExecutionContext() {
        this.currentSpecification = new Specification();
        this.currentScenario = new Scenario();
        this.currentStep = new StepDetails();
    }

    /**
     * @return - The Current Specification that is executing.
     * Returns null in BeforeSuite and AfterSuite levels as no spec is executing then.
     */
    public Specification getCurrentSpecification() {
        return currentSpecification;
    }

    /**
     * @return - The Current Scenario that is executing.
     * Returns null in BeforeSuite, AfterSuite, BeforeSpec levels as no scenario is executing then.
     */
    public Scenario getCurrentScenario() {
        return currentScenario;
    }

    /**
     * @return - The Current Step that is executing.
     * Returns null in BeforeSuite, AfterSuite, BeforeSpec, AfterSpec, BeforeScenario levels as no step is executing then.
     */
    public StepDetails getCurrentStep() {
        return currentStep;
    }

    /**
     * @return - All the valid tags (including scenario and spec tags) at the execution level.
     */
    public List<String> getAllTags() {
        HashSet<String> specTags = new HashSet<String>(currentSpecification.getTags());
        specTags.addAll(currentScenario.getTags());
        return new ArrayList<String>(specTags);
    }
}
