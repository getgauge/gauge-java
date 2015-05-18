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

import com.thoughtworks.gauge.StepRegistry;
import gauge.messages.Messages;

import java.lang.reflect.Method;

public class ValidateStepProcessor implements IMessageProcessor {

    public Messages.Message process(Messages.Message message) {
        stepValidationResult validationResult = validateStep(message.getStepValidateRequest());
        Messages.StepValidateResponse stepValidationResponse = Messages.StepValidateResponse.newBuilder().setIsValid(validationResult.isValid())
                .setErrorMessage(validationResult.getErrorMessage())
                .build();
        return Messages.Message.newBuilder()
                .setMessageId(message.getMessageId())
                .setStepValidateResponse(stepValidationResponse)
                .setMessageType(Messages.Message.MessageType.StepValidateResponse)
                .build();
    }

    private stepValidationResult validateStep(Messages.StepValidateRequest stepValidateRequest) {
        Method methodImplementation = StepRegistry.get(stepValidateRequest.getStepText());
        if (methodImplementation != null) {
            int implementationParamCount = methodImplementation.getParameterTypes().length;
            int numberOfParameters = stepValidateRequest.getNumberOfParameters();
            if (implementationParamCount == numberOfParameters) {
                return new stepValidationResult(true);
            } else {
                return new stepValidationResult(false, String.format("Incorrect number of parameters in step implementation. Found [%d] expected [%d] parameters", implementationParamCount, numberOfParameters));
            }
        } else {
            return new stepValidationResult(false, "Step implementation not found");
        }
    }

    private class stepValidationResult {
        private boolean isValid;
        private String errorMessage;

        public stepValidationResult(boolean isValid, String errorMessage) {
            this.isValid = isValid;
            this.errorMessage = errorMessage;
        }

        public stepValidationResult(boolean isValid) {
            this.isValid = isValid;
            this.errorMessage = "";
        }

        public boolean isValid() {
            return isValid;
        }

        public String getErrorMessage() {
            return errorMessage;
        }
    }

}
