package com.thoughtworks.gauge;

import main.Messages;

import java.lang.reflect.Method;
import java.util.Set;

public class SpecExecutionEndingProcessor extends MethodExecutionMessageProcessor implements IMessageProcessor {
    @Override
    public Messages.Message process(Messages.Message message) {
        SpecificationInfo info = new ExecutionInfoMapper().executionInfoFrom(message.getSpecExecutionEndingRequest().getCurrentExecutionInfo());
        Set<Method> afterSpecHooks = HooksRegistry.getAfterSpecHooks();
        return executeHooks(afterSpecHooks, message, info);
    }
}
