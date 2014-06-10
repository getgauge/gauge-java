package com.thoughtworks.gauge;

import main.Messages;

public class ExecutionInfoMapper {
    public SpecificationInfo executionInfoFrom(Messages.ExecutionInfo currentExecutionInfo) {
        SpecificationInfo specificationInfo = new SpecificationInfo();
        if (!currentExecutionInfo.isInitialized()) {
            return  specificationInfo;
        }
        specificationInfo.setSpecification(specificationFrom(currentExecutionInfo.getCurrentSpec()));
        specificationInfo.setScenario(scenarioFrom(currentExecutionInfo.getCurrentScenario()));
        specificationInfo.setStep(stepFrom(currentExecutionInfo.getCurrentStep()));

        return specificationInfo;
    }

    private Specification specificationFrom(Messages.SpecInfo currentSpec) {
        if (currentSpec.isInitialized()) {
            return new Specification(currentSpec.getName(), currentSpec.getFileName(), currentSpec.getIsFailed(), currentSpec.getTagsList());
        }
        return null;
    }

    private Scenario scenarioFrom(Messages.ScenarioInfo currentScenario) {
        if (currentScenario.isInitialized()) {
            return new Scenario(currentScenario.getName(), currentScenario.getIsFailed(), currentScenario.getTagsList());
        }
        return null;
    }

    private StepDetails stepFrom(Messages.StepInfo currentStep) {
        if (currentStep.isInitialized())  {
            return new StepDetails(currentStep.getStep().getActualStepText(), currentStep.getIsFailed());
        }
        return null;

    }
}
