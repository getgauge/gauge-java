package com.thoughtworks.gauge;

import gauge.messages.Messages;

import java.lang.reflect.Method;
import java.util.Set;

public class SpecExecutionStartingProcessor extends MethodExecutionMessageProcessor implements IMessageProcessor {
    @Override
    public Messages.Message process(Messages.Message message) {
        SpecificationInfo info = new ExecutionInfoMapper().executionInfoFrom(message.getSpecExecutionStartingRequest().getCurrentExecutionInfo());
        Set<Method> beforeSpecHooks = HooksRegistry.getBeforeSpecHooks();
        return executeHooks(beforeSpecHooks, message, info);
    }


}
