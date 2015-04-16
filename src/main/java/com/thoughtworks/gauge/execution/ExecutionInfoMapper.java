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

package com.thoughtworks.gauge.execution;


import com.thoughtworks.gauge.Scenario;
import com.thoughtworks.gauge.Specification;
import com.thoughtworks.gauge.SpecificationInfo;
import com.thoughtworks.gauge.StepDetails;
import gauge.messages.Messages;

public class ExecutionInfoMapper {
    public SpecificationInfo executionInfoFrom(Messages.ExecutionInfo currentExecutionInfo) {
        if (!currentExecutionInfo.isInitialized()) {
            return  new SpecificationInfo();
        }
        return new SpecificationInfo(specificationFrom(currentExecutionInfo.getCurrentSpec()),
                                     scenarioFrom(currentExecutionInfo.getCurrentScenario()),
                                     stepFrom(currentExecutionInfo.getCurrentStep()));
    }

    public Specification specificationFrom(Messages.SpecInfo currentSpec) {
        if (currentSpec.isInitialized()) {
            return new Specification(currentSpec.getName(), currentSpec.getFileName(), currentSpec.getIsFailed(), currentSpec.getTagsList());
        }
        return null;
    }

    public Scenario scenarioFrom(Messages.ScenarioInfo currentScenario) {
        if (currentScenario.isInitialized()) {
            return new Scenario(currentScenario.getName(), currentScenario.getIsFailed(), currentScenario.getTagsList());
        }
        return null;
    }

    public StepDetails stepFrom(Messages.StepInfo currentStep) {
        if (currentStep.isInitialized())  {
            return new StepDetails(currentStep.getStep().getActualStepText(), currentStep.getIsFailed());
        }
        return null;

    }
}
