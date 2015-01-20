package com.thoughtworks.gauge;


import gauge.messages.Messages;

public interface IMessageProcessor {
    Messages.Message process(Messages.Message message);
}
