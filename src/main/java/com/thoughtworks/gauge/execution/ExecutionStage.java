/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge.execution;

import gauge.messages.Spec;

public interface ExecutionStage {

    void setNextStage(ExecutionStage stage);

    Spec.ProtoExecutionResult execute(Spec.ProtoExecutionResult result);

    Spec.ProtoExecutionResult executeNext(Spec.ProtoExecutionResult stageResult);
}
