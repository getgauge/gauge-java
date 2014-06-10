package com.thoughtworks.gauge;

import main.Messages;

public interface IMessageProcessor {
    Messages.Message process(Messages.Message message);
}
