/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge.processor;

import com.thoughtworks.gauge.ClassInstanceManager;
import com.thoughtworks.gauge.MessageCollector;
import com.thoughtworks.gauge.ScreenshotCollector;
import com.thoughtworks.gauge.execution.ExecutionInfoMapper;
import com.thoughtworks.gauge.registry.HooksRegistry;
import com.thoughtworks.gauge.ExecutionContext;
import gauge.messages.Messages;
import gauge.messages.Spec;

public class SpecExecutionStartingProcessor extends MethodExecutionMessageProcessor implements IMessageProcessor {

    public SpecExecutionStartingProcessor(ClassInstanceManager instanceManager) {
        super(instanceManager);
    }

    public Messages.Message process(Messages.Message message) {
        ExecutionContext info = new ExecutionInfoMapper().executionInfoFrom(message.getSpecExecutionStartingRequest().getCurrentExecutionInfo());
        Messages.Message result = executeHooks(HooksRegistry.getBeforeSpecHooks(), message, info);
        Spec.ProtoExecutionResult executionResult = result.getExecutionStatusResponse().getExecutionResult();
        Spec.ProtoExecutionResult protoExecutionResult = new MessageCollector().addPendingMessagesTo(executionResult);
        protoExecutionResult = new ScreenshotCollector().addPendingScreenshotTo(protoExecutionResult);
        return createMessageWithExecutionStatusResponse(message, protoExecutionResult);
    }
}
