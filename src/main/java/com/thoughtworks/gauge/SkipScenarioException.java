package com.thoughtworks.gauge;

public class SkipScenarioException extends RuntimeException {
    public SkipScenarioException(String message) {
        super(message);
    }

    @SuppressWarnings("unused")
    public SkipScenarioException(String message, Throwable cause) {
        super(message, cause);
    }
}
