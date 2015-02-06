// Copyright 2015 ThoughtWorks, Inc.

// This file is part of Gauge-Java.

// Gauge-Java is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.

// Gauge-Java is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.

// You should have received a copy of the GNU General Public License
// along with Gauge-Java.  If not, see <http://www.gnu.org/licenses/>.

package com.thoughtworks.gauge;


import gauge.messages.Messages;

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
