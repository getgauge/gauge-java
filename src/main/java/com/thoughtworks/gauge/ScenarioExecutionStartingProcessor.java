package com.thoughtworks.gauge;

import main.Messages;

import java.lang.reflect.Method;
import java.util.Set;

public class ScenarioExecutionStartingProcessor extends MethodExecutionMessageProcessor implements IMessageProcessor {
    @Override
    public Messages.Message process(Messages.Message message) {
        SpecificationInfo info = new ExecutionInfoMapper().executionInfoFrom(message.getScenarioExecutionStartingRequest().getCurrentExecutionInfo());
        Set<Method> beforeScenarioHooks = HooksRegistry.getBeforeScenarioHooks();
        return executeHooks(beforeScenarioHooks, message, info);
    }
}
