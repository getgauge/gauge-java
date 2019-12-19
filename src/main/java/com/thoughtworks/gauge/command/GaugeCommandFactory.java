package com.thoughtworks.gauge.command;

public class GaugeCommandFactory {
    public GaugeJavaCommand getExecutor(String phase) {
        if ("--init".equals(phase)) {
            return new SetupCommand();
        }
        return new StartCommand();
    }
}
