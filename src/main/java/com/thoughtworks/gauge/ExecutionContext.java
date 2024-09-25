/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Gives the information about the current execution at runtime - spec, scenario, step that is running.
 */
public class ExecutionContext {
    private final Specification currentSpecification;
    private final Scenario currentScenario;
    private final StepDetails currentStep;

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
        HashSet<String> specTags = new HashSet<>(currentSpecification.getTags());
        specTags.addAll(currentScenario.getTags());
        return new ArrayList<>(specTags);
    }
}
