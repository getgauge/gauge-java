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

    public Messages.Message process(Messages.Message message) {
        Method method = StepRegistry.get(message.getExecuteStepRequest().getParsedStepText());
        ExecutionPipeline pipeline = new ExecutionPipeline(new HookExecutionStage(HooksRegistry.getBeforeClassStepsHooksOfClass(method.getDeclaringClass())));
        pipeline.addStages(new StepExecutionStage(message.getExecuteStepRequest()),
                           new HookExecutionStage(HooksRegistry.getAfterClassStepsHooksOfClass(method.getDeclaringClass())));

        Spec.ProtoExecutionResult protoExecutionResult = pipeline.start();
        return createMessageWithExecutionStatusResponse(message, protoExecutionResult);
    }
}
