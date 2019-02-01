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

import com.google.common.base.Charsets;
import com.thoughtworks.gauge.Util;
import com.thoughtworks.gauge.scan.StaticScanner;
import gauge.messages.Messages;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class StepPositionsRequestProcessorTest {
    @Test
    public void ShouldProcessRequest() {
        StaticScanner staticScanner = new StaticScanner();
        String implFile = Util.workingDir() + File.separator + String.format("src%stest%sresources%stest%sfiles%sfoo.java", File.separator, File.separator, File.separator, File.separator, File.separator);
        String contents = staticScanner.readFile(implFile, Charsets.UTF_8);
        staticScanner.addStepsFromFileContents(implFile, contents);
        StepPositionsRequestProcessor stepPositionsRequestProcessor = new StepPositionsRequestProcessor(staticScanner.getRegistry());
        Messages.StepPositionsRequest stepPositionRequest = Messages.StepPositionsRequest.newBuilder().setFilePath(implFile).build();
        Messages.Message request = Messages.Message.newBuilder().setMessageType(Messages.Message.MessageType.StepPositionsRequest).setMessageId(1l).setStepPositionsRequest(stepPositionRequest).build();
        Messages.StepPositionsResponse response = stepPositionsRequestProcessor.process(request).getStepPositionsResponse();

        assertEquals(response.getStepPositionsList().size(), 1);
        assertEquals(response.getStepPositionsList().get(0).getStepValue(), "new step");
        assertEquals(response.getStepPositionsList().get(0).getSpan().getStart(), 6);
    }

    @Test
    public void ShouldProcessRequestForAliases() {
        StaticScanner staticScanner = new StaticScanner();
        String implFile = Util.workingDir() + File.separator + String.format("src%stest%sresources%stest%sfiles%sfooAliases.java", File.separator, File.separator, File.separator, File.separator, File.separator);
        String contents = staticScanner.readFile(implFile, Charsets.UTF_8);
        staticScanner.addStepsFromFileContents(implFile, contents);
        StepPositionsRequestProcessor stepPositionsRequestProcessor = new StepPositionsRequestProcessor(staticScanner.getRegistry());
        Messages.StepPositionsRequest stepPositionRequest = Messages.StepPositionsRequest.newBuilder().setFilePath(implFile).build();
        Messages.Message request = Messages.Message.newBuilder().setMessageType(Messages.Message.MessageType.StepPositionsRequest).setMessageId(1l).setStepPositionsRequest(stepPositionRequest).build();
        Messages.StepPositionsResponse response = stepPositionsRequestProcessor.process(request).getStepPositionsResponse();

        assertEquals(response.getStepPositionsList().size(), 2);
        assertEquals(response.getStepPositionsList().get(0).getStepValue(), "another step");
        assertEquals(response.getStepPositionsList().get(1).getStepValue(), "new step");
        assertEquals(response.getStepPositionsList().get(0).getSpan().getStart(), 6);
    }
}