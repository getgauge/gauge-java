/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge.processor;

import com.thoughtworks.gauge.Util;
import com.thoughtworks.gauge.scan.StaticScanner;
import gauge.messages.Messages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;


public class CacheFileRequestProcessorTest {


    private String implFile;
    private String contents;
    private String fooAliasesFilePath;
    private String fooAliasesContents;
    private StaticScanner staticScanner;

    @BeforeEach
    public void setUp(){
        staticScanner = new StaticScanner();
        String implFileRelativePath = String.format("src%stest%sresources%stest%sfiles%sfoo.java", File.separator, File.separator, File.separator, File.separator, File.separator);
        implFile = Util.workingDir() + File.separator + implFileRelativePath;
        contents = staticScanner.readFile(implFile, StandardCharsets.UTF_8);

        String fooAliasesImplFileRelativePath = String.format("src%stest%sresources%stest%sfiles%sfooAliases.java", File.separator, File.separator, File.separator, File.separator, File.separator);
        fooAliasesFilePath = Util.workingDir() + File.separator + fooAliasesImplFileRelativePath;
        fooAliasesContents = staticScanner.readFile(fooAliasesFilePath, StandardCharsets.UTF_8);
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
