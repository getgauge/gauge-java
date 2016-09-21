// Copyright 2015 ThoughtWorks, Inc.

// This file is part of Gauge-Java.

// This program is free software.
//
// It is dual-licensed under:
// 1) the GNU General Public License as published by the Free Software Foundation,
// either version 3 of the License, or (at your option) any later version;
// or
// 2) the Eclipse Public License v1.0.
//
// You can redistribute it and/or modify it under the terms of either license.
// We would then provide copied of each license in a separate .txt file with the name of the license as the title of the file.

package com.thoughtworks.gauge.processor;

import com.thoughtworks.gauge.ClassInstanceManager;
import com.thoughtworks.gauge.ExecutionContext;
import com.thoughtworks.gauge.execution.HooksExecutor;
import com.thoughtworks.gauge.execution.MethodExecutor;
import com.thoughtworks.gauge.hook.Hook;
import gauge.messages.Messages;
import gauge.messages.Spec;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class MethodExecutionMessageProcessor {
    protected ClassInstanceManager instanceManager;

    public MethodExecutionMessageProcessor(ClassInstanceManager instanceManager) {
        this.instanceManager = instanceManager;
    }

    public Messages.Message execute(Method method, Messages.Message message, Object... args) {
        HashSet<Method> methods = new HashSet<Method>();
        methods.add(method);
        return execute(methods, message, args);
    }

    public Messages.Message execute(Set<Method> methods, Messages.Message message, Object... args) {
        MethodExecutor methodExecutor = new MethodExecutor(instanceManager);
        long totalExecutionTime = 0;
        for (Method method : methods) {
            Spec.ProtoExecutionResult result = methodExecutor.execute(method, args);
            totalExecutionTime += result.getExecutionTime();
            if (result.getFailed()) {
                return createMessageWithExecutionStatusResponse(message, result);
            }
        }

        Spec.ProtoExecutionResult passingExecution = Spec.ProtoExecutionResult.newBuilder().setFailed(false).setExecutionTime(totalExecutionTime).build();
        return createMessageWithExecutionStatusResponse(message, passingExecution);
    }

    public Messages.Message executeHooks(List<Hook> hooks, Messages.Message message, ExecutionContext executionInfo) {
        Spec.ProtoExecutionResult executionStatusResponse = new HooksExecutor(hooks, executionInfo, instanceManager).execute();
        return createMessageWithExecutionStatusResponse(message, executionStatusResponse);
    }

    public Messages.Message createMessageWithExecutionStatusResponse(Messages.Message receivedMessage, Spec.ProtoExecutionResult result) {
        return Messages.Message.newBuilder()
                .setMessageId(receivedMessage.getMessageId())
                .setMessageType(Messages.Message.MessageType.ExecutionStatusResponse)
                .setExecutionStatusResponse(Messages.ExecutionStatusResponse.newBuilder().setExecutionResult(result).build())
                .build();
    }
}
