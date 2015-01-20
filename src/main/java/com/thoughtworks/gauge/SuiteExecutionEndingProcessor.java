package com.thoughtworks.gauge;

import gauge.messages.Messages;

import java.lang.reflect.Method;
import java.util.Set;

public class SuiteExecutionEndingProcessor extends MethodExecutionMessageProcessor implements IMessageProcessor {
    @Override
    public Messages.Message process(Messages.Message message) {
        SpecificationInfo info = new ExecutionInfoMapper().executionInfoFrom(message.getExecutionEndingRequest().getCurrentExecutionInfo());
        Set<Method> afterSuiteHooks = HooksRegistry.getAfterSuiteHooks();
        return executeHooks(afterSuiteHooks, message, info);
    }
}

