/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge.execution;

import com.thoughtworks.gauge.ClassInstanceManager;
import com.thoughtworks.gauge.ExecutionContext;
import com.thoughtworks.gauge.hook.Hook;
import gauge.messages.Spec;

import java.util.List;


public class HookExecutionStage extends AbstractExecutionStage {
    private final List<Hook> execHooks;
    private final ClassInstanceManager manager;
    private ExecutionStage next;

    public HookExecutionStage(List<Hook> execHooks, ClassInstanceManager manager) {
        this.execHooks = execHooks;
        this.manager = manager;
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
        return new HooksExecutor(execHooks, new ExecutionContext(), manager).execute();
    }
}
