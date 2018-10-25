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
        if (!(new File(fileName).exists())) {
            return;
        }
        staticScanner.reloadSteps(fileName);
    }
}

