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

import com.thoughtworks.gauge.registry.StepRegistry;
import gauge.messages.Messages;
import gauge.messages.Messages.StepValidateResponse;
import gauge.messages.Messages.StepValidateResponse.ErrorType;

import java.lang.reflect.Method;

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
        Method methodImplementation = StepRegistry.get(stepValidateRequest.getStepText());
        if (methodImplementation != null) {
            return buildSuccessValidationResponse();
        } else {
            return buildFailureValidationResponse("Step implementation not found");
        }
    }

    private StepValidateResponse buildFailureValidationResponse(String errorMessage) {
        return StepValidateResponse.newBuilder()
                .setIsValid(false)
                .setErrorType(ErrorType.STEP_IMPLEMENTATION_NOT_FOUND)
                .setErrorMessage(errorMessage)
                .build();
    }

    private StepValidateResponse buildSuccessValidationResponse() {
        return StepValidateResponse.newBuilder()
                .setIsValid(true)
                .build();
    }
}
