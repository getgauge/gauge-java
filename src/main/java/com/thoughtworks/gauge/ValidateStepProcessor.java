package com.thoughtworks.gauge;

import main.Messages;

import java.lang.reflect.Method;

import static main.Messages.Message;
import static main.Messages.Message.MessageType;
import static main.Messages.StepValidateResponse;

public class ValidateStepProcessor implements IMessageProcessor {

    @Override
    public Message process(Message message) {
        stepValidationResult validationResult = validateStep(message.getStepValidateRequest());
        StepValidateResponse stepValidationResponse = StepValidateResponse.newBuilder().setIsValid(validationResult.isValid())
                .setErrorMessage(validationResult.getErrorMessage())
                .build();
        return Message.newBuilder()
                .setMessageId(message.getMessageId())
                .setStepValidateResponse(stepValidationResponse)
                .setMessageType(MessageType.StepValidateResponse)
                .build();
    }

    private stepValidationResult validateStep(Messages.StepValidateRequest stepValidateRequest) {
        Method methodImplementation = StepRegistry.get(stepValidateRequest.getStepText());
        if (methodImplementation != null) {
            int implementationParamCount = methodImplementation.getParameterTypes().length;
            int argumentCount = stepValidateRequest.getNumberOfArguments();
            if (implementationParamCount == argumentCount) {
                return new stepValidationResult(true);
            } else {
                return new stepValidationResult(false, String.format("Incorrect number of parameters in step implementation. Found [%d] expected [%d] parameters", implementationParamCount, argumentCount));
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
