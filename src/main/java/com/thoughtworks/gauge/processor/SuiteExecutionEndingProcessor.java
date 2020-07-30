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
import com.thoughtworks.gauge.Logger;
import com.thoughtworks.gauge.execution.ExecutionInfoMapper;
import com.thoughtworks.gauge.registry.HooksRegistry;
import gauge.messages.Messages;
import gauge.messages.Spec;

public class SuiteExecutionEndingProcessor extends MethodExecutionMessageProcessor implements IMessageProcessor {

    public SuiteExecutionEndingProcessor(ClassInstanceManager instanceManager) {
        super(instanceManager);
    }

    public Messages.Message process(Messages.Message message) {
        ExecutionContext info = new ExecutionInfoMapper().executionInfoFrom(message.getExecutionEndingRequest().getCurrentExecutionInfo());
        Logger.debug("Executing afterSuite hooks ");
        Messages.Message result = executeHooks(HooksRegistry.getAfterSuiteHooks(), message, info);
        Logger.debug("AfterSuite hooks executed");
        ClearObjectCache.clear(ClearObjectCache.SUITE_LEVEL, getInstanceManager());
        Spec.ProtoExecutionResult executionResult = result.getExecutionStatusResponse().getExecutionResult();
        Spec.ProtoExecutionResult protoExecutionResult = new MessageCollector().addPendingMessagesTo(executionResult);
        Logger.debug("Messages collected");
        protoExecutionResult = new ScreenshotCollector().addPendingScreenshotTo(protoExecutionResult);
        Logger.debug("Screenshots collected");
        return createMessageWithExecutionStatusResponse(message, protoExecutionResult);
    }
}
