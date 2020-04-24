/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge.processor;

import com.thoughtworks.gauge.connection.StubImplementationCodeProcessor;
import gauge.messages.Messages;
import gauge.messages.Spec;
import org.junit.Test;

import java.nio.file.Paths;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class StubImplementationCodeProcessorTest {
    @Test
    public void testProcessStubImplementationCode() {
        String file = getClass().getClassLoader().getResource("test/files/Empty.java").getFile();
        Messages.StubImplementationCodeRequest codeRequest = Messages.StubImplementationCodeRequest
                .newBuilder()
                .setImplementationFilePath(file)
                .addAllCodes(new ArrayList<String>() {
                    {
                        add("@Step\npublic void foo(){\n}");
                    }
                }).build();
        StubImplementationCodeProcessor processor = new StubImplementationCodeProcessor();
        Messages.Message message = processor.process(Messages.Message.newBuilder().setStubImplementationCodeRequest(codeRequest).build());
        Messages.FileDiff fileDiff = message.getFileDiff();
        Spec.Span span = fileDiff.getTextDiffsList().get(0).getSpan();
        assertEquals(span.getStart(), 1);
        assertEquals(span.getStartChar(), 0);
        assertEquals(span.getEnd(), 1);
        assertEquals(span.getEndChar(), 0);
    }
}
