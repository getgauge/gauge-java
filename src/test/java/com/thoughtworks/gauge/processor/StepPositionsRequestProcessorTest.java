/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge.processor;

import com.thoughtworks.gauge.Util;
import com.thoughtworks.gauge.scan.StaticScanner;
import gauge.messages.Messages;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class StepPositionsRequestProcessorTest {
    @Test
    public void ShouldProcessRequest() {
        StaticScanner staticScanner = new StaticScanner();
        String implFile = Util.workingDir() + File.separator + String.format("src%stest%sresources%stest%sfiles%sfoo.java", File.separator, File.separator, File.separator, File.separator, File.separator);
        String contents = staticScanner.readFile(implFile, StandardCharsets.UTF_8);
        staticScanner.addStepsFromFileContents(implFile, contents);
        StepPositionsRequestProcessor stepPositionsRequestProcessor = new StepPositionsRequestProcessor(staticScanner.getRegistry());
        Messages.StepPositionsRequest stepPositionRequest = Messages.StepPositionsRequest.newBuilder().setFilePath(implFile).build();
        Messages.Message request = Messages.Message.newBuilder().setMessageType(Messages.Message.MessageType.StepPositionsRequest).setMessageId(1l).setStepPositionsRequest(stepPositionRequest).build();
        Messages.StepPositionsResponse response = stepPositionsRequestProcessor.process(request).getStepPositionsResponse();

        assertEquals(response.getStepPositionsList().size(), 1);
        assertEquals(response.getStepPositionsList().get(0).getStepValue(), "new step");
        assertEquals(response.getStepPositionsList().get(0).getSpan().getStart(), 9);
    }

    @Test
    public void ShouldProcessRequestForAliases() {
        StaticScanner staticScanner = new StaticScanner();
        String implFile = Util.workingDir() + File.separator + String.format("src%stest%sresources%stest%sfiles%sfooAliases.java", File.separator, File.separator, File.separator, File.separator, File.separator);
        String contents = staticScanner.readFile(implFile, StandardCharsets.UTF_8);
        staticScanner.addStepsFromFileContents(implFile, contents);
        StepPositionsRequestProcessor stepPositionsRequestProcessor = new StepPositionsRequestProcessor(staticScanner.getRegistry());
        Messages.StepPositionsRequest stepPositionRequest = Messages.StepPositionsRequest.newBuilder().setFilePath(implFile).build();
        Messages.Message request = Messages.Message.newBuilder().setMessageType(Messages.Message.MessageType.StepPositionsRequest).setMessageId(1L).setStepPositionsRequest(stepPositionRequest).build();
        Messages.StepPositionsResponse response = stepPositionsRequestProcessor.process(request).getStepPositionsResponse();

        assertEquals(response.getStepPositionsList().size(), 2);
        assertEquals(response.getStepPositionsList().get(0).getStepValue(), "another step");
        assertEquals(response.getStepPositionsList().get(1).getStepValue(), "new step");
        assertEquals(response.getStepPositionsList().get(0).getSpan().getStart(), 9);
    }
}
