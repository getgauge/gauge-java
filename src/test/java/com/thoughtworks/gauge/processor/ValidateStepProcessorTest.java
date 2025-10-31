/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge.processor;

import com.thoughtworks.gauge.registry.StepRegistry;
import gauge.messages.Messages;
import gauge.messages.Messages.Message;
import gauge.messages.Messages.Message.Builder;
import gauge.messages.Messages.Message.MessageType;
import gauge.messages.Messages.StepValidateRequest;
import gauge.messages.Messages.StepValidateResponse.ErrorType;
import gauge.messages.Spec;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ValidateStepProcessorTest {

    private static final String STEP_TEXT = "step    with arbitrary text ";

    private Message message;

    @BeforeEach
    public void setupMessage() {
        Builder messageBuilder = Messages.Message.newBuilder().setMessageType(MessageType.StepValidateRequest).setMessageId(1L);
        Spec.ProtoStepValue.Builder protoStep = Spec.ProtoStepValue.newBuilder().setStepValue(STEP_TEXT).setParameterizedStepValue(STEP_TEXT).addAllParameters(new ArrayList<>());
        StepValidateRequest stepValidationRequest = StepValidateRequest.newBuilder().setStepText(STEP_TEXT).setNumberOfParameters(1).setStepValue(protoStep).build();
        messageBuilder.setStepValidateRequest(stepValidationRequest);
        this.message = messageBuilder.build();
    }


    @Test
    public void shouldFailIfStepIsNotFoundAndShouldGiveSuggestion() {
        StepRegistry stepRegistry = mock(StepRegistry.class);
        ValidateStepProcessor stepProcessor = new ValidateStepProcessor(stepRegistry);

        Message outputMessage = stepProcessor.process(message);

        assertEquals(ErrorType.STEP_IMPLEMENTATION_NOT_FOUND, outputMessage.getStepValidateResponse().getErrorType());
        String suggestion = """
                
                	@Step("step    with arbitrary text ")
                	public void stepWithArbitraryText(){
                		\
                throw new UnsupportedOperationException("Provide custom implementation");
                	}\
                """;
        assertEquals(suggestion, outputMessage.getStepValidateResponse().getSuggestion());
        assertFalse(outputMessage.getStepValidateResponse().getIsValid());
    }

    @Test
    public void ShouldGiveSuggestionWithMethodNameimplementation1() {
        Builder messageBuilder = Messages.Message.newBuilder().setMessageType(MessageType.StepValidateRequest).setMessageId(1L);
        Spec.ProtoStepValue.Builder protoStep = Spec.ProtoStepValue.newBuilder()
                .setStepValue("<abc> <xyz>")
                .setParameterizedStepValue("<abc> <xyz>")
                .addAllParameters(Arrays.asList("abc", "xyz"));
        StepValidateRequest stepValidationRequest = StepValidateRequest.newBuilder().setStepText("{} {}").setNumberOfParameters(1).setStepValue(protoStep).build();
        messageBuilder.setStepValidateRequest(stepValidationRequest);
        this.message = messageBuilder.build();
        StepRegistry stepRegistry = mock(StepRegistry.class);
        ValidateStepProcessor stepProcessor = new ValidateStepProcessor(stepRegistry);

        Message outputMessage = stepProcessor.process(message);

        assertEquals(ErrorType.STEP_IMPLEMENTATION_NOT_FOUND, outputMessage.getStepValidateResponse().getErrorType());
        String suggestion = """
                
                	@Step("<abc> <xyz>")
                	public void implementation1(Object arg0, Object arg1){
                		\
                throw new UnsupportedOperationException("Provide custom implementation");
                	}\
                """;
        assertEquals(suggestion, outputMessage.getStepValidateResponse().getSuggestion());
        assertFalse(outputMessage.getStepValidateResponse().getIsValid());
    }

    @Test
    public void shouldNotFailIfStepIsFoundAndShouldNotGiveSuggestion() {
        StepRegistry stepRegistry = mock(StepRegistry.class);
        when(stepRegistry.contains(STEP_TEXT)).thenReturn(true);
        when(stepRegistry.hasMultipleImplementations(STEP_TEXT)).thenReturn(false);
        ValidateStepProcessor stepProcessor = new ValidateStepProcessor(stepRegistry);

        Message outputMessage = stepProcessor.process(message);

        assertTrue(outputMessage.getStepValidateResponse().getIsValid());
        assertEquals("", outputMessage.getStepValidateResponse().getSuggestion());
    }

    @Test
    public void shouldFailIfStepIsDefinedTwice() {
        StepRegistry stepRegistry = mock(StepRegistry.class);
        when(stepRegistry.contains(STEP_TEXT)).thenReturn(true);
        when(stepRegistry.hasMultipleImplementations(STEP_TEXT)).thenReturn(true);
        ValidateStepProcessor stepProcessor = new ValidateStepProcessor(stepRegistry);

        Message outputMessage = stepProcessor.process(message);

        assertEquals(ErrorType.DUPLICATE_STEP_IMPLEMENTATION, outputMessage.getStepValidateResponse().getErrorType());
        assertFalse(outputMessage.getStepValidateResponse().getIsValid());
    }
}
