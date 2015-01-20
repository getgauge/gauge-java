package com.thoughtworks.gauge;

import gauge.messages.Messages;

import java.lang.reflect.Method;
import java.util.Set;

public class StepExecutionEndingProcessor extends MethodExecutionMessageProcessor implements IMessageProcessor {
    @Override
    public Messages.Message process(Messages.Message message) {
        SpecificationInfo info = new ExecutionInfoMapper().executionInfoFrom(message.getStepExecutionEndingRequest().getCurrentExecutionInfo());
        Set<Method> afterStepHooks = HooksRegistry.getAfterStepHooks();
        return executeHooks(afterStepHooks, message, info);
    }
}
