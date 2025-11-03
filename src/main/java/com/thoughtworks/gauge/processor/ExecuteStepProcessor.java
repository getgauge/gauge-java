/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge.processor;

import com.thoughtworks.gauge.ClassInstanceManager;
import com.thoughtworks.gauge.Logger;
import com.thoughtworks.gauge.MessageCollector;
import com.thoughtworks.gauge.ScreenshotCollector;
import com.thoughtworks.gauge.execution.ExecutionPipeline;
import com.thoughtworks.gauge.execution.StepExecutionStage;
import com.thoughtworks.gauge.execution.parameters.parsers.base.ParameterParsingChain;
import com.thoughtworks.gauge.registry.StepRegistry;
import gauge.messages.Messages;
import gauge.messages.Spec;

import java.lang.reflect.Method;

public class ExecuteStepProcessor extends MethodExecutionMessageProcessor implements IMessageProcessor {

    private final ParameterParsingChain chain;
    private final StepRegistry registry;

    public ExecuteStepProcessor(ClassInstanceManager instanceManager, ParameterParsingChain chain, StepRegistry stepRegistry) {
        super(instanceManager);
        this.chain = chain;
        this.registry = stepRegistry;
    }

    public Messages.Message process(Messages.Message message) {
        String stepText = message.getExecuteStepRequest().getParsedStepText();
        Method method = registry.get(stepText).getMethodInfo();
        if (method == null) {
            Logger.fatal("No step definition found. Try compiling the source before execution.");
        }
        Logger.debug("Executing '" + stepText + "' using '" + method.getDeclaringClass() + "." + method.getName());
        ExecutionPipeline pipeline = new ExecutionPipeline(new StepExecutionStage(message.getExecuteStepRequest(), getInstanceManager(), chain, registry));
        Spec.ProtoExecutionResult executionResult = pipeline.start();
        Spec.ProtoExecutionResult protoExecutionResult = new MessageCollector().addPendingMessagesTo(executionResult);
        protoExecutionResult = new ScreenshotCollector().addPendingScreenshotTo(protoExecutionResult);
        return createMessageWithExecutionStatusResponse(message, protoExecutionResult);
    }
}
