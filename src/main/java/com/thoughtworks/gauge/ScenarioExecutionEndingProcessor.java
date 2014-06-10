package com.thoughtworks.gauge;

import main.Messages;

import java.lang.reflect.Method;
import java.util.Set;

public class ScenarioExecutionEndingProcessor extends MethodExecutionMessageProcessor implements IMessageProcessor {
    @Override
    public Messages.Message process(Messages.Message message) {
        SpecificationInfo info = new ExecutionInfoMapper().executionInfoFrom(message.getScenarioExecutionEndingRequest().getCurrentExecutionInfo());
        Set<Method> afterScenarioHooks = HooksRegistry.getAfterScenarioHooks();
        return executeHooks(afterScenarioHooks, message, info);
    }
}
