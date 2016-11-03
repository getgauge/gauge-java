// Copyright 2015 ThoughtWorks, Inc.

// This file is part of Gauge-Java.

// This program is free software.
//
// It is dual-licensed under:
// 1) the GNU General Public License as published by the Free Software Foundation,
// either version 3 of the License, or (at your option) any later version;
// or
// 2) the Eclipse Public License v1.0.
//
// You can redistribute it and/or modify it under the terms of either license.
// We would then provide copied of each license in a separate .txt file with the name of the license as the title of the file.

package com.thoughtworks.gauge.execution;

import com.thoughtworks.gauge.ExecutionContext;
import com.thoughtworks.gauge.hook.Hook;
import gauge.messages.Spec;

import java.util.List;


public class HookExecutionStage extends AbstractExecutionStage {
    private List<Hook> execHooks;
    private ExecutionStage next;

    public HookExecutionStage(List<Hook> execHooks) {
        this.execHooks = execHooks;
    }

    public void setNextStage(ExecutionStage stage) {
        this.next = stage;
    }

    public Spec.ProtoExecutionResult execute(Spec.ProtoExecutionResult result) {
        Spec.ProtoExecutionResult execResult = execute();
        Spec.ProtoExecutionResult stageResult = mergeExecResults(result, execResult);
        return executeNext(stageResult);
    }

    protected ExecutionStage next() {
        return next;
    }

    private Spec.ProtoExecutionResult execute() {
        return new HooksExecutor(execHooks, new ExecutionContext()).execute();
    }
}
