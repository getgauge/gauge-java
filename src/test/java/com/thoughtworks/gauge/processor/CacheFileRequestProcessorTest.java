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
import com.thoughtworks.gauge.refactor.Util;
import com.thoughtworks.gauge.scan.StaticScanner;
import gauge.messages.Messages;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CacheFileRequestProcessorTest {


    @Test
    public void shouldProcessRequestWithFileOpenedStatus() {
        StaticScanner staticScanner = new StaticScanner();
        String implFile = Util.workingDir() + File.separator + String.format("src%stest%sresources%stest%sfiles%sfoo.java", File.separator, File.separator, File.separator, File.separator, File.separator);
        String contents = staticScanner.readFile(implFile, Charsets.UTF_8);
        CacheFileRequestProcessor cacheFileRequestProcessor = new CacheFileRequestProcessor(staticScanner);
        Messages.CacheFileRequest cacheFileRequest = Messages.CacheFileRequest.newBuilder().setFilePath(implFile).setContent(contents).setStatus(Messages.CacheFileRequest.FileStatus.OPENED).build();
        Messages.Message request = Messages.Message.newBuilder().setMessageType(Messages.Message.MessageType.CacheFileRequest).setMessageId(1l).setCacheFileRequest(cacheFileRequest).build();
        cacheFileRequestProcessor.process(request);

        assertTrue(staticScanner.getRegistry().contains("new step"));

    }

    @Test
    public void ShouldProcessRequestWithDeletedStatus() {
        StaticScanner staticScanner = new StaticScanner();
        String implFile = Util.workingDir() + File.separator + String.format("src%stest%sresources%stest%sfiles%sfoo.java", File.separator, File.separator, File.separator, File.separator, File.separator);
        String contents = staticScanner.readFile(implFile, Charsets.UTF_8);
        staticScanner.addStepsFromFileContents(implFile, contents);
        CacheFileRequestProcessor cacheFileRequestProcessor = new CacheFileRequestProcessor(staticScanner);
        Messages.CacheFileRequest cacheFileRequest = Messages.CacheFileRequest.newBuilder().setFilePath(implFile).setStatus(Messages.CacheFileRequest.FileStatus.DELETED).build();
        Messages.Message request = Messages.Message.newBuilder().setMessageType(Messages.Message.MessageType.CacheFileRequest).setMessageId(1l).setCacheFileRequest(cacheFileRequest).build();

        assertTrue(staticScanner.getRegistry().contains("new step"));
        cacheFileRequestProcessor.process(request);
        assertFalse(staticScanner.getRegistry().contains("new step"));
    }
}