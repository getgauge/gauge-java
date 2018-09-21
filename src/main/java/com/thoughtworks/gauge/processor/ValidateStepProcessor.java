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
import com.thoughtworks.gauge.registry.StepRegistry;
import gauge.messages.Messages;
import gauge.messages.Messages.StepValidateResponse;
import gauge.messages.Messages.StepValidateResponse.ErrorType;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ValidateStepProcessor implements IMessageProcessor {
    private static Integer num = 1;
    private static final Integer MAX_LENGTH = 3;
    private final StepRegistry registry;

    public ValidateStepProcessor(ClassInstanceManager instanceManager, StepRegistry registry) {
        this.registry = registry;
    }

    public Messages.Message process(Messages.Message message) {
        StepValidateResponse stepValidationResponse = validateStep(message.getStepValidateRequest());
        return Messages.Message.newBuilder()
                .setMessageId(message.getMessageId())
                .setStepValidateResponse(stepValidationResponse)
                .setMessageType(Messages.Message.MessageType.StepValidateResponse)
                .build();
    }

    private StepValidateResponse validateStep(Messages.StepValidateRequest stepValidateRequest) {
        Set<Method> methodImplementations = registry.getAll(stepValidateRequest.getStepText());

        if (methodImplementations != null && methodImplementations.size() == 1) {
            return buildSuccessValidationResponse();
        } else if (methodImplementations.isEmpty()) {
            final StringBuilder suggestion = new StringBuilder(String.format("\n\t@Step(\"%s\")\n", stepValidateRequest.getStepValue().getParameterizedStepValue()));
            final String methodName = getMethodName(stepValidateRequest.getStepText());
            suggestion.append(String.format("\tpublic void %s(%s){\n\t\t", methodName, getParamList(stepValidateRequest.getStepValue().getParametersList())));
            suggestion.append("throw new UnsupportedOperationException(\"Provide custom implementation\");\n\t}");
            return buildFailureValidationResponse("Step implementation not found", ErrorType.STEP_IMPLEMENTATION_NOT_FOUND, suggestion.toString());
        } else {
            return buildFailureValidationResponse("Duplicate step implementation found", ErrorType.DUPLICATE_STEP_IMPLEMENTATION, "");
        }
    }

    private String getMethodName(String stepText) {
        final StringBuilder methodName = new StringBuilder();
        if (!stepText.equals("")) {
            String[] methodNameArray = stepText.split(" ");
            List<String> list = new ArrayList<>(Arrays.asList(methodNameArray));
            list.removeAll(Collections.singletonList("{}"));
            int length = list.size();
            if (length == 0) {
                methodName.append(String.format("implementation%s", (num++).toString()));
            } else {
                for (int i = 0; i < length; i++) {
                    String firstLetter = (i == 0) ? list.get(i).substring(0, 1).toLowerCase() : list.get(i).substring(0, 1).toUpperCase();
                    methodName.append(firstLetter);
                    methodName.append(list.get(i).substring(1).toLowerCase());
                }
            }
            return methodName.toString();
        }
        return methodName.append(String.format("implementation%s", (num++).toString())).toString();
    }

    private String getParamList(List<String> params) {
        StringBuilder paramlistBuilder = new StringBuilder();
        for (int i = 0; i < params.size(); i++) {
            paramlistBuilder.append("Object arg").append(i);
            if (i != params.size() - 1) {
                paramlistBuilder.append(", ");
            }
        }
        return paramlistBuilder.toString();
    }

    private StepValidateResponse buildFailureValidationResponse(String errorMessage, ErrorType errorType, String suggestion) {
        return StepValidateResponse.newBuilder()
                .setIsValid(false)
                .setErrorType(errorType)
                .setErrorMessage(errorMessage)
                .setSuggestion(suggestion)
                .build();
    }

    private StepValidateResponse buildSuccessValidationResponse() {
        return StepValidateResponse.newBuilder()
                .setIsValid(true)
                .build();
    }
}
