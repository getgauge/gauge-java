package com.thoughtworks.gauge;

import main.Messages;

public class KillProcessProcessor implements IMessageProcessor {
    @Override
    public Messages.Message process(Messages.Message message) {
        return message;
    }
}
