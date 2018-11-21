package com.thoughtworks.gauge.processor;

import com.thoughtworks.gauge.registry.StepRegistry;
import gauge.messages.Messages;

public class StepPositionsRequestProcessor implements IMessageProcessor {
    private StepRegistry stepRegistry;

    public StepPositionsRequestProcessor(StepRegistry stepRegistry) {
        this.stepRegistry = stepRegistry;
    }

    @Override
    public Messages.Message process(Messages.Message message) {

//        stepRegistry.getStepPositions(message.getStepPositionsRequest().getFilePath());
        return null;
    }
}
