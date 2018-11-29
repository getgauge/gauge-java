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
        Messages.CacheFileRequest.FileStatus status = request.getCacheFileRequest().getStatus();
        switch (status) {
            case OPENED:
            case CHANGED:
                staticScanner.reloadSteps(fileName);
                break;
            case DELETED:
                staticScanner.removeSteps(fileName);
                break;
            case CREATED:
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
            staticScanner.reloadSteps(fileName);
        }
    }
}

