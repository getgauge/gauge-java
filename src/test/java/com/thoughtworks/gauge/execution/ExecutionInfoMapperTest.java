package com.thoughtworks.gauge.execution;

import com.thoughtworks.gauge.Scenario;
import com.thoughtworks.gauge.Specification;
import com.thoughtworks.gauge.SpecificationInfo;
import com.thoughtworks.gauge.StepDetails;
import gauge.messages.Messages;
import junit.framework.TestCase;

import java.util.List;

public class ExecutionInfoMapperTest extends TestCase {

    public void testStepDetailsCreation() throws Exception {
        Messages.ExecuteStepRequest step = Messages.ExecuteStepRequest.newBuilder().setActualStepText("hello world <a>").setParsedStepText("hello world {}").build();
        Messages.StepInfo stepInfo = Messages.StepInfo.newBuilder().setIsFailed(true).setStep(step).build();
        StepDetails stepDetails = new ExecutionInfoMapper().stepFrom(stepInfo);

        assertEquals("hello world <a>", stepDetails.getText());
        assertTrue(stepDetails.getIsFailing());
    }

    public void testScenarioDetailsCreation() throws Exception {
        Messages.ScenarioInfo scenarioInfo = Messages.ScenarioInfo.newBuilder().setIsFailed(false).setName("My scenario").addTags("tag1").addTags("tag2").build();
        Scenario scenario = new ExecutionInfoMapper().scenarioFrom(scenarioInfo);

        assertEquals("My scenario", scenario.getName());
        assertFalse(scenario.getIsFailing());
        List<String> tags = scenario.getTags();
        assertEquals(2, tags.size());
        assertTrue(tags.contains("tag1"));
        assertTrue(tags.contains("tag2"));
    }

    public void testSpecificationDetailsCreation() throws Exception {
        Messages.ScenarioInfo scenarioInfo = Messages.ScenarioInfo.newBuilder().setIsFailed(true).setName("a scenario").addTags("tag3").build();
        Messages.SpecInfo specInfo = Messages.SpecInfo.newBuilder().setFileName("hello.spec").setIsFailed(true).addTags("specTag").addTags("specTag2").setName("My spec").build();

        Messages.ExecuteStepRequest step = Messages.ExecuteStepRequest.newBuilder().setActualStepText("foo bar <a>").setParsedStepText("foo bar {}").build();
        Messages.StepInfo stepInfo = Messages.StepInfo.newBuilder().setIsFailed(true).setStep(step).build();

        Messages.ExecutionInfo executionInfo = Messages.ExecutionInfo.newBuilder().setCurrentSpec(specInfo).setCurrentScenario(scenarioInfo).setCurrentStep(stepInfo).build();
        SpecificationInfo specificationInfo = new ExecutionInfoMapper().executionInfoFrom(executionInfo);

        Specification currentSpecification = specificationInfo.getCurrentSpecification();
        assertEquals("My spec", currentSpecification.getName());
        assertTrue(currentSpecification.getIsFailing());
        assertEquals("hello.spec", currentSpecification.getFileName());
        List<String> specTags = currentSpecification.getTags();
        assertEquals(2, specTags.size());
        assertTrue(specTags.contains("specTag"));
        assertTrue(specTags.contains("specTag2"));


        Scenario currentScenario = specificationInfo.getCurrentScenario();
        assertEquals("a scenario", currentScenario.getName());
        assertTrue(currentScenario.getIsFailing());
        List<String> scenarioTags = currentScenario.getTags();
        assertEquals(1, scenarioTags.size());
        assertTrue(scenarioTags.contains("tag3"));

        StepDetails currentStep = specificationInfo.getCurrentStep();
        assertTrue(currentStep.getIsFailing());
        assertEquals("foo bar <a>", currentStep.getText());
    }
}