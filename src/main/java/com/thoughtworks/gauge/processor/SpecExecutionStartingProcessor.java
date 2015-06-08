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

import com.thoughtworks.gauge.execution.ExecutionInfoMapper;
import com.thoughtworks.gauge.HooksRegistry;
import com.thoughtworks.gauge.ExecutionContext;
import gauge.messages.Messages;

import java.lang.reflect.Method;
import java.util.Set;

public class SpecExecutionStartingProcessor extends MethodExecutionMessageProcessor implements IMessageProcessor {
    public Messages.Message process(Messages.Message message) {
        ExecutionContext info = new ExecutionInfoMapper().executionInfoFrom(message.getSpecExecutionStartingRequest().getCurrentExecutionInfo());
        Set<Method> beforeSpecHooks = HooksRegistry.getBeforeSpecHooks();
        return executeHooks(beforeSpecHooks, message, info);
    }


}
