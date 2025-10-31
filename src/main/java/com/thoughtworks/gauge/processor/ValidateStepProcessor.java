/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge.processor;

import com.thoughtworks.gauge.registry.StepRegistry;
import gauge.messages.Messages;
import gauge.messages.Messages.StepValidateResponse;
import gauge.messages.Messages.StepValidateResponse.ErrorType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ValidateStepProcessor implements IMessageProcessor {
    private static Integer num = 1;
    private final StepRegistry registry;

    public ValidateStepProcessor(StepRegistry registry) {
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
        String stepToValidate = stepValidateRequest.getStepText();
        if (!registry.contains(stepToValidate)) {
            final StringBuilder suggestion = new StringBuilder(String.format("\n\t@Step(\"%s\")\n", stepValidateRequest.getStepValue().getParameterizedStepValue()));
            final String methodName = getMethodName(stepValidateRequest.getStepText());
            suggestion.append(String.format("\tpublic void %s(%s){\n\t\t", methodName, getParamList(stepValidateRequest.getStepValue().getParametersList())));
            suggestion.append("throw new UnsupportedOperationException(\"Provide custom implementation\");\n\t}");
            return buildFailureValidationResponse("Step implementation not found", ErrorType.STEP_IMPLEMENTATION_NOT_FOUND, suggestion.toString());
        } else if (registry.hasMultipleImplementations(stepToValidate)) {
            return buildFailureValidationResponse("Duplicate step implementation found", ErrorType.DUPLICATE_STEP_IMPLEMENTATION, "");
        } else {
            return buildSuccessValidationResponse();
        }
    }

    private String getMethodName(String stepText) {
        final StringBuilder methodName = new StringBuilder();
        if (!"".equals(stepText)) {
            String[] methodNameArray = stepText.split("\\s+");
            List<String> list = new ArrayList<>(Arrays.asList(methodNameArray));
            list.removeAll(Collections.singletonList("{}"));
            int length = list.size();
            if (length == 0) {
                methodName.append(String.format("implementation%s", num++));
            } else {
                for (int i = 0; i < length; i++) {
                    String firstLetter = i == 0 ? list.get(i).substring(0, 1).toLowerCase() : list.get(i).substring(0, 1).toUpperCase();
                    methodName.append(firstLetter);
                    methodName.append(list.get(i).substring(1).toLowerCase());
                }
            }
            return methodName.toString();
        }
        return methodName.append(String.format("implementation%s", num++)).toString();
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
