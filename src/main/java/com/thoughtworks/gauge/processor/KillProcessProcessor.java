/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge.processor;

import gauge.messages.Messages;

public class KillProcessProcessor implements IMessageProcessor {

    public KillProcessProcessor() {
    }

    public Messages.Message process(Messages.Message message) {
        return message;
    }
}
