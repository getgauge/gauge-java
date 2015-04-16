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

public abstract class AbstractExecutionStage implements ExecutionStage {

    public Spec.ProtoExecutionResult executeNext(Spec.ProtoExecutionResult previousStageResult) {
        if (next() != null) {
            return next().execute(previousStageResult);
        } else {
            return previousStageResult;
        }
    }

    protected abstract ExecutionStage next();

}
