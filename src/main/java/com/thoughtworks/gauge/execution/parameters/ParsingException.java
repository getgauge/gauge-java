package com.thoughtworks.gauge.execution.parameters;

import gauge.messages.Spec.ProtoExecutionResult;

public class ParsingException extends Exception {
    private static final long serialVersionUID = -4205182693938362914L;

    private ProtoExecutionResult executionResult;

    public ParsingException(ProtoExecutionResult executionResult) {
        this.executionResult = executionResult;
    }

    public ProtoExecutionResult getExecutionResult() {
        return executionResult;
    }
}
