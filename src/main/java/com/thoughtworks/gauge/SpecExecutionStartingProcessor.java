package com.thoughtworks.gauge;

import java.lang.reflect.Method;
import java.util.Set;

import static main.Messages.Message;

public class SpecExecutionStartingProcessor extends MethodExecutionMessageProcessor implements IMessageProcessor {
    @Override
    public Message process(Message message) {
        SpecificationInfo info = new ExecutionInfoMapper().executionInfoFrom(message.getSpecExecutionStartingRequest().getCurrentExecutionInfo());
        Set<Method> beforeSpecHooks = HooksRegistry.getBeforeSpecHooks();
        return executeHooks(beforeSpecHooks, message, info);
    }


}
