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

package com.thoughtworks.gauge.processor;

import com.thoughtworks.gauge.execution.ExecutionInfoMapper;
import com.thoughtworks.gauge.HooksRegistry;
import com.thoughtworks.gauge.SpecificationInfo;
import gauge.messages.Messages;

import java.lang.reflect.Method;
import java.util.Set;

public class ScenarioExecutionEndingProcessor extends MethodExecutionMessageProcessor implements IMessageProcessor {
    public Messages.Message process(Messages.Message message) {
        SpecificationInfo info = new ExecutionInfoMapper().executionInfoFrom(message.getScenarioExecutionEndingRequest().getCurrentExecutionInfo());
        Set<Method> afterScenarioHooks = HooksRegistry.getAfterScenarioHooks();
        Messages.Message result = executeHooks(afterScenarioHooks, message, info);
        ClearState.clear(ClearState.SCENARIO_LEVEL);
        return result;
    }
}
