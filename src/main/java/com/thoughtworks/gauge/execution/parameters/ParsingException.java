/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge.execution.parameters;

import gauge.messages.Spec.ProtoExecutionResult;

public class ParsingException extends Exception {
    private static final long serialVersionUID = -4205182693938362914L;

    private final ProtoExecutionResult executionResult;

    public ParsingException(ProtoExecutionResult executionResult) {
        this.executionResult = executionResult;
    }

    public ProtoExecutionResult getExecutionResult() {
        return executionResult;
    }
}
