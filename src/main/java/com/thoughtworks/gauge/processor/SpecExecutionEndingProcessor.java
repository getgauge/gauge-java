/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge.processor;

import com.thoughtworks.gauge.ClassInstanceManager;
import com.thoughtworks.gauge.ExecutionContext;
import com.thoughtworks.gauge.MessageCollector;
import com.thoughtworks.gauge.ScreenshotCollector;
import com.thoughtworks.gauge.execution.ExecutionInfoMapper;
import com.thoughtworks.gauge.registry.HooksRegistry;
import gauge.messages.Messages;
import gauge.messages.Spec;

public class SpecExecutionEndingProcessor extends MethodExecutionMessageProcessor implements IMessageProcessor {

    public SpecExecutionEndingProcessor(ClassInstanceManager instanceManager) {
        super(instanceManager);
    }

    public Messages.Message process(Messages.Message message) {
        ExecutionContext info = new ExecutionInfoMapper().executionInfoFrom(message.getSpecExecutionEndingRequest().getCurrentExecutionInfo());
        Messages.Message result = executeHooks(HooksRegistry.getAfterSpecHooks(), message, info);
        ClearObjectCache.clear(ClearObjectCache.SPEC_LEVEL, getInstanceManager());
        Spec.ProtoExecutionResult executionResult = result.getExecutionStatusResponse().getExecutionResult();
        Spec.ProtoExecutionResult protoExecutionResult = new MessageCollector().addPendingMessagesTo(executionResult);
        protoExecutionResult = new ScreenshotCollector().addPendingScreenshotTo(protoExecutionResult);
        return createMessageWithExecutionStatusResponse(message, protoExecutionResult);
    }
}
