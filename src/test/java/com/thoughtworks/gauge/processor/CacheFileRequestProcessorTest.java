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
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class CacheFileRequestProcessorTest {


    private String implFile;
    private String contents;
    private String fooAliasesFilePath;
    private String fooAliasesContents;
    private StaticScanner staticScanner;

    @Before
    public void setUp(){
        staticScanner = new StaticScanner();
        String implFileRelativePath = String.format("src%stest%sresources%stest%sfiles%sfoo.java", File.separator, File.separator, File.separator, File.separator, File.separator);
        implFile = Util.workingDir() + File.separator + implFileRelativePath;
        contents = staticScanner.readFile(implFile, Charsets.UTF_8);

        String fooAliasesImplFileRelativePath = String.format("src%stest%sresources%stest%sfiles%sfooAliases.java", File.separator, File.separator, File.separator, File.separator, File.separator);
        fooAliasesFilePath = Util.workingDir() + File.separator + fooAliasesImplFileRelativePath;
        fooAliasesContents = staticScanner.readFile(fooAliasesFilePath, Charsets.UTF_8);
    }

    @Test
    public void shouldProcessRequestWithFileOpenedStatus() {
        CacheFileRequestProcessor cacheFileRequestProcessor = new CacheFileRequestProcessor(staticScanner);
        Messages.CacheFileRequest cacheFileRequest = Messages.CacheFileRequest.newBuilder()
                .setFilePath(implFile)
                .setContent(contents)
                .setStatus(Messages.CacheFileRequest.FileStatus.OPENED).build();

        Messages.Message request = Messages.Message.newBuilder()
                .setMessageType(Messages.Message.MessageType.CacheFileRequest)
                .setMessageId(1l)
                .setCacheFileRequest(cacheFileRequest).build();
        cacheFileRequestProcessor.process(request);

        assertTrue(staticScanner.getRegistry().contains("new step"));

    }

    @Test
    public void ShouldProcessRequestWithDeletedStatus() {
        staticScanner.addStepsFromFileContents(implFile, contents);
        CacheFileRequestProcessor cacheFileRequestProcessor = new CacheFileRequestProcessor(staticScanner);
        Messages.CacheFileRequest cacheFileRequest = Messages.CacheFileRequest.newBuilder()
                .setFilePath(implFile)
                .setStatus(Messages.CacheFileRequest.FileStatus.DELETED).build();

        Messages.Message request = Messages.Message.newBuilder()
                .setMessageType(Messages.Message.MessageType.CacheFileRequest)
                .setMessageId(1l)
                .setCacheFileRequest(cacheFileRequest).build();

        assertTrue(staticScanner.getRegistry().contains("new step"));
        cacheFileRequestProcessor.process(request);
        assertFalse(staticScanner.getRegistry().contains("new step"));
    }

    @Test
    public void shouldProcessRequestWithClosedStatus() {
        staticScanner.addStepsFromFileContents(implFile, contents);
        CacheFileRequestProcessor cacheFileRequestProcessor = new CacheFileRequestProcessor(staticScanner);
        Messages.CacheFileRequest cacheFileRequest = Messages.CacheFileRequest.newBuilder()
                .setFilePath(implFile)
                .setStatus(Messages.CacheFileRequest.FileStatus.CLOSED).build();

        Messages.Message request = Messages.Message.newBuilder()
                .setMessageType(Messages.Message.MessageType.CacheFileRequest)
                .setMessageId(1l)
                .setCacheFileRequest(cacheFileRequest).build();
        String stepValue = "StepValue{stepText='new step', parameterizedStepText='new step', parameters=[]}";
        assertEquals(stepValue, staticScanner.getRegistry().get("new step").getStepValue().toString());
        cacheFileRequestProcessor.process(request);
        assertEquals(stepValue, staticScanner.getRegistry().get("new step").getStepValue().toString());
    }

    @Test
    public void shouldNotProcessRequestWithCreateStatusIfFileIsCached() {
        // load contents from different file.
        staticScanner.addStepsFromFileContents(implFile, fooAliasesContents);
        CacheFileRequestProcessor cacheFileRequestProcessor = new CacheFileRequestProcessor(staticScanner);
        Messages.CacheFileRequest cacheFileRequest = Messages.CacheFileRequest.newBuilder()
                .setFilePath(implFile)
                .setStatus(Messages.CacheFileRequest.FileStatus.CREATED).build();

        Messages.Message request = Messages.Message.newBuilder()
                .setMessageType(Messages.Message.MessageType.CacheFileRequest)
                .setMessageId(1l)
                .setCacheFileRequest(cacheFileRequest).build();

        assertTrue(staticScanner.getRegistry().get("new step").getHasAlias());

        cacheFileRequestProcessor.process(request);
        assertTrue(staticScanner.getRegistry().get("new step").getHasAlias());
    }

    @Test
    public void shouldProcessRequestWithCreateStatusIfFileIsNotCached() {
        CacheFileRequestProcessor cacheFileRequestProcessor = new CacheFileRequestProcessor(staticScanner);
        Messages.CacheFileRequest cacheFileRequest = Messages.CacheFileRequest.newBuilder()
                .setFilePath(implFile)
                .setStatus(Messages.CacheFileRequest.FileStatus.CREATED).build();

        Messages.Message request = Messages.Message.newBuilder()
                .setMessageType(Messages.Message.MessageType.CacheFileRequest)
                .setMessageId(1l)
                .setCacheFileRequest(cacheFileRequest).build();

        assertFalse(staticScanner.getRegistry().contains("new step"));

        cacheFileRequestProcessor.process(request);
        assertTrue(staticScanner.getRegistry().contains("new step"));
    }
}