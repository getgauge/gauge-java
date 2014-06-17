package com.thoughtworks.gauge;

import main.Messages;
import main.Spec;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class MethodExecutionMessageProcessor {

    public Messages.Message execute(Method method, Messages.Message message, Object... args) {
        HashSet<Method> methods = new HashSet<Method>();
        methods.add(method);
        return execute(methods, message, args);
    }

    public Messages.Message execute(Set<Method> methods, Messages.Message message, Object... args) {
        MethodExecutor methodExecutor = new MethodExecutor();
        for (Method method : methods) {
            Spec.ProtoExecutionResult result = methodExecutor.execute(method, args);
            if (result.getFailed()) {
                return createMessageWithExecutionStatusResponse(message, result);
            }
        }

        Spec.ProtoExecutionResult passingExecution = Spec.ProtoExecutionResult.newBuilder().setFailed(false).build();
        return createMessageWithExecutionStatusResponse(message, passingExecution);
    }

    public Messages.Message executeHooks(Set<Method> beforeSpecHooks, Messages.Message message, SpecificationInfo executionInfo) {
        MethodExecutor methodExecutor = new MethodExecutor();
        Spec.ProtoExecutionResult result;
        for (Method method : beforeSpecHooks) {
            if (methodHasArguments(method, executionInfo)) {
                result = methodExecutor.execute(method, executionInfo);
            } else {
                result = methodExecutor.execute(method);
            }
            if (result.getFailed()) {
                return createMessageWithExecutionStatusResponse(message, result);
            }
        }

        Spec.ProtoExecutionResult passingExecution = Spec.ProtoExecutionResult.newBuilder().setFailed(false).build();
        return createMessageWithExecutionStatusResponse(message, passingExecution);
    }

    private boolean methodHasArguments(Method method, Object... arg) {
       if (method.getParameterTypes().length != arg.length) {
           return false;
       }
        List<Class> argsClassList = createClassList(arg);
        Class<?>[] parameterTypes = method.getParameterTypes();
        boolean isValid = true;
        for (int i = 0; i < parameterTypes.length; i ++) {
            if (!(argsClassList.indexOf(parameterTypes[i]) == i)) {
                isValid = false;
            }
        }
        return isValid;
    }

    private List<Class> createClassList(Object[] objects) {
        ArrayList<Class> classes = new ArrayList<Class>();
        for (Object obj : objects) {
            classes.add(obj.getClass());
        }
        return classes;
    }


    private Messages.Message createMessageWithExecutionStatusResponse(Messages.Message receivedMessage, Spec.ProtoExecutionResult result) {
        return Messages.Message.newBuilder()
                .setMessageId(receivedMessage.getMessageId())
                .setMessageType(Messages.Message.MessageType.ExecutionStatusResponse)
                .setExecutionStatusResponse(Messages.ExecutionStatusResponse.newBuilder().setExecutionResult(result).build())
                .build();
    }
}
