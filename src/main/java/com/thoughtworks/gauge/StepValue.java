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

package com.thoughtworks.gauge;

import java.util.List;

public class StepValue {
    private final String stepText;
    private final String parameterizedStepText;
    private final List<String> parameters;

    public StepValue(String stepText, String parameterizedStepText, List<String> parameters) {
        this.stepText = stepText;
        this.parameterizedStepText = parameterizedStepText;
        this.parameters = parameters;
    }

    public String getStepText() {
        return stepText;
    }

    public List<String> getParameters() {
        return parameters;
    }

    public int getParamCount() {
        return parameters.size();
    }

    public String getStepAnnotationText() {
        return parameterizedStepText;
    }

}
