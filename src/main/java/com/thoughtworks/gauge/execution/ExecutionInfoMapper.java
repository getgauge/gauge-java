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

import com.thoughtworks.gauge.Scenario;
import com.thoughtworks.gauge.Specification;
import com.thoughtworks.gauge.ExecutionContext;
import com.thoughtworks.gauge.StepDetails;
import gauge.messages.Messages;

public class ExecutionInfoMapper {
    public ExecutionContext executionInfoFrom(Messages.ExecutionInfo currentExecutionInfo) {
        if (!currentExecutionInfo.isInitialized()) {
            return new ExecutionContext();
        }
        return new ExecutionContext(specificationFrom(currentExecutionInfo.getCurrentSpec()), scenarioFrom(currentExecutionInfo.getCurrentScenario()),
                stepFrom(currentExecutionInfo.getCurrentStep()));
    }

    public Specification specificationFrom(Messages.SpecInfo currentSpec) {
        if (currentSpec.isInitialized()) {
            return new Specification(currentSpec.getName(), currentSpec.getFileName(), currentSpec.getIsFailed(), currentSpec.getTagsList());
        }
        return new Specification();
    }

    public Scenario scenarioFrom(Messages.ScenarioInfo currentScenario) {
        if (currentScenario.isInitialized()) {
            return new Scenario(currentScenario.getName(), currentScenario.getIsFailed(), currentScenario.getTagsList());
        }
        return new Scenario();
    }

    public StepDetails stepFrom(Messages.StepInfo currentStep) {
        if (currentStep.isInitialized()) {
            return new StepDetails(currentStep.getStep().getActualStepText(), currentStep.getIsFailed(), currentStep.getStackTrace());
        }
        return new StepDetails();

    }
}
