package com.thoughtworks.gauge;

import gauge.messages.Messages;

import java.lang.reflect.Method;

public class ValidateStepProcessor implements IMessageProcessor {

    @Override
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
