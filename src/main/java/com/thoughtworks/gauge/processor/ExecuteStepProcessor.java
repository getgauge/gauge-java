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

import com.thoughtworks.gauge.HooksRegistry;
import com.thoughtworks.gauge.StepRegistry;
import com.thoughtworks.gauge.execution.ExecutionPipeline;
import com.thoughtworks.gauge.execution.HookExecutionStage;
import com.thoughtworks.gauge.execution.StepExecutionStage;
import gauge.messages.Messages;
import gauge.messages.Spec;

import java.lang.reflect.Method;

public class ExecuteStepProcessor extends MethodExecutionMessageProcessor implements IMessageProcessor {

    @Override
    public Messages.Message process(Messages.Message message) {
        Method method = StepRegistry.get(message.getExecuteStepRequest().getParsedStepText());
        ExecutionPipeline pipeline = new ExecutionPipeline(new HookExecutionStage(HooksRegistry.getBeforeClassStepsHooksOfClass(method.getDeclaringClass())));
        pipeline.addStages(new StepExecutionStage(message.getExecuteStepRequest()),
                           new HookExecutionStage(HooksRegistry.getAfterClassStepsHooksOfClass(method.getDeclaringClass())));

        Spec.ProtoExecutionResult protoExecutionResult = pipeline.start();
        return createMessageWithExecutionStatusResponse(message, protoExecutionResult);
    }
}
