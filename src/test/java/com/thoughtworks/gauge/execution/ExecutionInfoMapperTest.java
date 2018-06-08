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

package com.thoughtworks.gauge.execution;

import com.thoughtworks.gauge.ExecutionContext;
import com.thoughtworks.gauge.Scenario;
import com.thoughtworks.gauge.Specification;
import com.thoughtworks.gauge.StepDetails;
import gauge.messages.Messages;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ExecutionInfoMapperTest {

    private static final String ACTUAL_STEP_TEXT = "foo bar <a>";
    private static final String PARSED_STEP_TEXT = "foo bar {}";
    private static final String STACK_TRACE = "Stack trace";
    private static final String ERROR_MESSAGE = "Error message";
    private static final String SCENARIO_NAME = "Scenario name";
    private static final String SPEC_NAME = "Specification name";
    private static final String FILE_NAME = "hello.spec";
    private static final String SCENARIO_TAG_1 = "ScenarioTag1";
    private static final String SCENARIO_TAG_2 = "ScenarioTag2";
    private static final String SPEC_TAG_1 = "SpecTag1";
    private static final String SPEC_TAG_2 = "SpecTag2";

    @Test
    public void testStepDetailsCreation() {
        Messages.ExecuteStepRequest step = Messages.ExecuteStepRequest.newBuilder()
                .setActualStepText(ACTUAL_STEP_TEXT)
                .setParsedStepText(PARSED_STEP_TEXT)
                .build();

        Messages.StepInfo stepInfo = Messages.StepInfo.newBuilder()
                .setIsFailed(true)
                .setStep(step)
                .setStackTrace(STACK_TRACE)
                .setErrorMessage(ERROR_MESSAGE)
                .build();

        StepDetails stepDetails = new ExecutionInfoMapper().stepFrom(stepInfo);

        assertEquals(ACTUAL_STEP_TEXT, stepDetails.getText());
        assertEquals(STACK_TRACE, stepDetails.getStackTrace());
        assertEquals(ERROR_MESSAGE, stepDetails.getErrorMessage());
        assertTrue(stepDetails.getIsFailing());
    }

    @Test
    public void testScenarioDetailsCreation() {
        Messages.ScenarioInfo scenarioInfo = Messages.ScenarioInfo.newBuilder()
                .setIsFailed(false)
                .setName(SCENARIO_NAME)
                .addTags(SCENARIO_TAG_1)
                .addTags(SCENARIO_TAG_2)
                .build();

        Scenario scenario = new ExecutionInfoMapper().scenarioFrom(scenarioInfo);

        assertEquals(SCENARIO_NAME, scenario.getName());
        assertFalse(scenario.getIsFailing());
        List<String> tags = scenario.getTags();
        assertEquals(2, tags.size());
        assertTrue(tags.containsAll(asList(SCENARIO_TAG_1, SCENARIO_TAG_2)));
    }

    @Test
    public void testSpecificationDetailsCreation() {
        Messages.ScenarioInfo scenarioInfo = Messages.ScenarioInfo.newBuilder()
                .setIsFailed(true)
                .setName(SCENARIO_NAME)
                .addTags(SCENARIO_TAG_1)
                .build();

        Messages.SpecInfo specInfo = Messages.SpecInfo.newBuilder()
                .setFileName(FILE_NAME)
                .setIsFailed(true)
                .addTags(SPEC_TAG_1)
                .addTags(SPEC_TAG_2)
                .setName(SPEC_NAME)
                .build();

        Messages.ExecuteStepRequest step = Messages.ExecuteStepRequest.newBuilder()
                .setActualStepText(ACTUAL_STEP_TEXT)
                .setParsedStepText(PARSED_STEP_TEXT)
                .build();

        Messages.StepInfo stepInfo = Messages.StepInfo.newBuilder()
                .setIsFailed(true)
                .setStep(step)
                .setStackTrace(STACK_TRACE)
                .setErrorMessage(ERROR_MESSAGE)
                .build();

        Messages.ExecutionInfo executionInfo = Messages.ExecutionInfo.newBuilder()
                .setCurrentSpec(specInfo)
                .setCurrentScenario(scenarioInfo)
                .setCurrentStep(stepInfo)
                .build();

        ExecutionContext executionContext = new ExecutionInfoMapper().executionInfoFrom(executionInfo);

        Specification currentSpecification = executionContext.getCurrentSpecification();
        assertEquals(SPEC_NAME, currentSpecification.getName());
        assertTrue(currentSpecification.getIsFailing());
        assertEquals(FILE_NAME, currentSpecification.getFileName());

        List<String> specTags = currentSpecification.getTags();
        assertEquals(2, specTags.size());
        assertTrue(specTags.containsAll(asList(SPEC_TAG_1, SPEC_TAG_2)));

        Scenario currentScenario = executionContext.getCurrentScenario();
        assertEquals(SCENARIO_NAME, currentScenario.getName());
        assertTrue(currentScenario.getIsFailing());
        
        List<String> scenarioTags = currentScenario.getTags();
        assertEquals(1, scenarioTags.size());
        assertTrue(scenarioTags.contains(SCENARIO_TAG_1));

        StepDetails currentStep = executionContext.getCurrentStep();
        assertTrue(currentStep.getIsFailing());
        assertEquals(STACK_TRACE, currentStep.getStackTrace());
        assertEquals(ERROR_MESSAGE, currentStep.getErrorMessage());
        assertEquals(ACTUAL_STEP_TEXT, currentStep.getText());
    }
}
