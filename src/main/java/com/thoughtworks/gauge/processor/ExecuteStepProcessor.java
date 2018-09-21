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

import com.thoughtworks.gauge.ClassInstanceManager;
import com.thoughtworks.gauge.MessageCollector;
import com.thoughtworks.gauge.ScreenshotCollector;
import com.thoughtworks.gauge.execution.ExecutionPipeline;
import com.thoughtworks.gauge.execution.HookExecutionStage;
import com.thoughtworks.gauge.execution.StepExecutionStage;
import com.thoughtworks.gauge.execution.parameters.parsers.base.ParameterParsingChain;
import com.thoughtworks.gauge.registry.HooksRegistry;
import com.thoughtworks.gauge.registry.StepRegistry;
import gauge.messages.Messages;
import gauge.messages.Spec;

import java.lang.reflect.Method;

public class ExecuteStepProcessor extends MethodExecutionMessageProcessor implements IMessageProcessor {

    private final ParameterParsingChain chain;
    private StepRegistry registry;

    public ExecuteStepProcessor(ClassInstanceManager instanceManager, ParameterParsingChain chain, StepRegistry stepRegistry) {
        super(instanceManager);
        this.chain = chain;
        this.registry = stepRegistry;
    }

    public Messages.Message process(Messages.Message message) {
        Method method = registry.get(message.getExecuteStepRequest().getParsedStepText());
        ExecutionPipeline pipeline = new ExecutionPipeline(new HookExecutionStage(HooksRegistry.getBeforeClassStepsHooksOfClass(method.getDeclaringClass()), getInstanceManager()));
        pipeline.addStages(new StepExecutionStage(message.getExecuteStepRequest(), getInstanceManager(), chain, registry),
                new HookExecutionStage(HooksRegistry.getAfterClassStepsHooksOfClass(method.getDeclaringClass()), getInstanceManager()));
        Spec.ProtoExecutionResult executionResult = pipeline.start();
        Spec.ProtoExecutionResult protoExecutionResult = new MessageCollector().addPendingMessagesTo(executionResult);
        protoExecutionResult = new ScreenshotCollector().addPendingScreenshotTo(protoExecutionResult);
        return createMessageWithExecutionStatusResponse(message, protoExecutionResult);
    }
}
