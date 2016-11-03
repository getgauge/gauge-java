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

import gauge.messages.Messages;
import gauge.messages.Messages.StepValidateResponse;
import gauge.messages.Messages.StepValidateResponse.ErrorType;

import java.lang.reflect.Method;
import java.util.Set;

import com.thoughtworks.gauge.registry.StepRegistry;

public class ValidateStepProcessor implements IMessageProcessor {

    public Messages.Message process(Messages.Message message) {
        StepValidateResponse stepValidationResponse = validateStep(message.getStepValidateRequest());
        return Messages.Message.newBuilder()
                .setMessageId(message.getMessageId())
                .setStepValidateResponse(stepValidationResponse)
                .setMessageType(Messages.Message.MessageType.StepValidateResponse)
                .build();
    }

    private StepValidateResponse validateStep(Messages.StepValidateRequest stepValidateRequest) {
        Set<Method> methodImplementations = StepRegistry.getAll(stepValidateRequest.getStepText());
        if (methodImplementations != null && methodImplementations.size() == 1) {
            return buildSuccessValidationResponse();
        } else if (methodImplementations.isEmpty()) {
            return buildFailureValidationResponse("Step implementation not found", ErrorType.STEP_IMPLEMENTATION_NOT_FOUND);
        } else {
            return buildFailureValidationResponse("Duplicate step implementation found", ErrorType.DUPLICATE_STEP_IMPLEMENTATION);
        }
    }

    private StepValidateResponse buildFailureValidationResponse(String errorMessage, ErrorType errorType) {
        return StepValidateResponse.newBuilder()
                .setIsValid(false)
                .setErrorType(errorType)
                .setErrorMessage(errorMessage)
                .build();
    }

    private StepValidateResponse buildSuccessValidationResponse() {
        return StepValidateResponse.newBuilder()
                .setIsValid(true)
                .build();
    }
}
