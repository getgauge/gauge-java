/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
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
        Method method = registry.get(message.getExecuteStepRequest().getParsedStepText()).getMethodInfo();
        ExecutionPipeline pipeline = new ExecutionPipeline(new HookExecutionStage(HooksRegistry.getBeforeClassStepsHooksOfClass(method.getDeclaringClass()), getInstanceManager()));
        pipeline.addStages(new StepExecutionStage(message.getExecuteStepRequest(), getInstanceManager(), chain, registry),
                new HookExecutionStage(HooksRegistry.getAfterClassStepsHooksOfClass(method.getDeclaringClass()), getInstanceManager()));
        Spec.ProtoExecutionResult executionResult = pipeline.start();
        Spec.ProtoExecutionResult protoExecutionResult = new MessageCollector().addPendingMessagesTo(executionResult);
        protoExecutionResult = new ScreenshotCollector().addPendingScreenshotTo(protoExecutionResult);
        return createMessageWithExecutionStatusResponse(message, protoExecutionResult);
    }
}
