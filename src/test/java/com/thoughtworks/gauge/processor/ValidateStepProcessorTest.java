package com.thoughtworks.gauge.processor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import gauge.messages.Messages;
import gauge.messages.Messages.Message;
import gauge.messages.Messages.Message.Builder;
import gauge.messages.Messages.Message.MessageType;
import gauge.messages.Messages.StepValidateRequest;
import gauge.messages.Messages.StepValidateResponse.ErrorType;

import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.thoughtworks.gauge.registry.StepRegistry;


@RunWith(PowerMockRunner.class)
@PrepareForTest(StepRegistry.class)
public class ValidateStepProcessorTest {

    private static final String STEP_TEXT = "stepText";

    private Message message;
    
    private ValidateStepProcessor stepProcessor;

    @Before
    public void setupMessage(){
        Builder messageBuilder=Messages.Message.newBuilder().setMessageType(MessageType.StepValidateRequest).setMessageId(1l);
        StepValidateRequest stepValidationRequest = StepValidateRequest.newBuilder().setStepText(STEP_TEXT).setNumberOfParameters(1).build();
        messageBuilder.setStepValidateRequest(stepValidationRequest);
        this.message=messageBuilder.build();
    }
    
    @Before
    public void setupStepProcessor(){
        stepProcessor = new ValidateStepProcessor();
    }
    
    @Test
    public void shouldFailIfStepIsNotFound(){
        
        Message outputMessage=stepProcessor.process(message);
        
        assertEquals(ErrorType.STEP_IMPLEMENTATION_NOT_FOUND,outputMessage.getStepValidateResponse().getErrorType());
        assertFalse(outputMessage.getStepValidateResponse().getIsValid());
    }
    
    @Test
    public void shouldNotFailIfStepIsFound(){
        mockStepRegistry();
        
        Message outputMessage=stepProcessor.process(message);
        
        assertTrue(outputMessage.getStepValidateResponse().getIsValid());
    }

    private void mockStepRegistry(){
        try {
            mockStatic(StepRegistry.class);
            Method method = String.class.getMethod("toString");
            when(StepRegistry.get(STEP_TEXT)).thenReturn(method);
        } catch (NoSuchMethodException exception) {
            throw new RuntimeException(exception);
        } catch (SecurityException exception) {
            // TODO Auto-generated catch block
            throw new RuntimeException(exception);
        }
        
    }
}
