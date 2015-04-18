// Copyright 2015 ThoughtWorks, Inc.

// This file is part of Gauge-Java.

// Gauge-Java is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.

// Gauge-Java is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.

// You should have received a copy of the GNU General Public License
// along with Gauge-Java.  If not, see <http://www.gnu.org/licenses/>.
package com.thoughtworks.gauge.execution;

import gauge.messages.Spec;

import java.lang.reflect.Method;
import java.util.Set;


public class HookExecutionStage extends AbstractExecutionStage {
    private Set<Method> execHooks;
    private ExecutionStage next;

    public HookExecutionStage(Set<Method> execHooks) {
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
        MethodExecutor methodExecutor = new MethodExecutor();
        return methodExecutor.executeMethods(execHooks);
    }
}
