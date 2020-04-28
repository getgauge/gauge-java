/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge.command;

public class GaugeCommandFactory {
    public GaugeJavaCommand getExecutor(String phase) {
        if ("--init".equals(phase)) {
            return new SetupCommand();
        }
        return new StartCommand();
    }
}
