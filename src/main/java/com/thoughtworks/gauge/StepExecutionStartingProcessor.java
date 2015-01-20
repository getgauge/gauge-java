package com.thoughtworks.gauge;

import gauge.messages.Messages;

import java.lang.reflect.Method;
import java.util.Set;

public class StepExecutionStartingProcessor extends MethodExecutionMessageProcessor implements IMessageProcessor {
    @Override
    public Messages.Message process(Messages.Message message) {
        SpecificationInfo info = new ExecutionInfoMapper().executionInfoFrom(message.getStepExecutionStartingRequest().getCurrentExecutionInfo());
        Set<Method> beforeStepHooks = HooksRegistry.getBeforeStepHooks();
        return executeHooks(beforeStepHooks, message, info);
    }
}
