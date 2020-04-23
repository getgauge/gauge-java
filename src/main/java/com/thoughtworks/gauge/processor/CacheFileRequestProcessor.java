/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge.processor;

import com.google.common.base.Charsets;
import com.thoughtworks.gauge.scan.StaticScanner;
import gauge.messages.Messages;

import java.io.File;

public class CacheFileRequestProcessor implements IMessageProcessor {
    private StaticScanner staticScanner;

    public CacheFileRequestProcessor(StaticScanner staticScanner) {
        this.staticScanner = staticScanner;
    }

    @Override
    public Messages.Message process(Messages.Message request) {
        String fileName = request.getCacheFileRequest().getFilePath();
        String contents = request.getCacheFileRequest().getContent();
        Messages.CacheFileRequest.FileStatus status = request.getCacheFileRequest().getStatus();
        switch (status) {
            case OPENED:
            case CHANGED:
                staticScanner.reloadSteps(fileName, contents);
                break;
            case DELETED:
                staticScanner.removeSteps(fileName);
                break;
            case CREATED:
                if (!staticScanner.isFileCached(fileName)) {
                    loadFromDisk(fileName);
                }
                break;
            case CLOSED:
                loadFromDisk(fileName);
                break;
            default:
                throw new IllegalArgumentException();
        }
        return Messages.Message.newBuilder().build();
    }

    private void loadFromDisk(String fileName) {
        if ((new File(fileName).exists())) {
            staticScanner.reloadSteps(fileName, staticScanner.readFile(fileName, Charsets.UTF_8));
        }
    }
}

