package com.thoughtworks.gauge;

import main.Messages;

import java.lang.reflect.Method;
import java.util.Set;

public class SuiteExecutionStartingProcessor extends MethodExecutionMessageProcessor implements IMessageProcessor {
    @Override
    public Messages.Message process(Messages.Message message) {
        SpecificationInfo info = new ExecutionInfoMapper().executionInfoFrom(message.getExecutionStartingRequest().getCurrentExecutionInfo());
        Set<Method> beforeSuiteHooks = HooksRegistry.getBeforeSuiteHooks();
        return executeHooks(beforeSuiteHooks, message, info);
    }
}

