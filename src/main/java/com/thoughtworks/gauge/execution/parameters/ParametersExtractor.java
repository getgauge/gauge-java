// Copyright 2019 ThoughtWorks, Inc.

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

package com.thoughtworks.gauge.execution.parameters;

import com.thoughtworks.gauge.execution.parameters.parsers.base.ParameterParser;
import gauge.messages.Spec;

import java.util.List;

public class ParametersExtractor {

    private ParameterParser parameterParser;

    public ParametersExtractor(ParameterParser parameterParser) {
        this.parameterParser = parameterParser;
    }

    public Object[] extract(List<Spec.Parameter> arguments, Class<?>[] parameterTypes) throws ParsingException {
        Object[] parameters = new Object[arguments == null ? 0 : arguments.size()];

        for (int i = 0; i < parameters.length; i++) {
            parameters[i] = parameterParser.parse(parameterTypes[i], arguments.get(i));
        }

        return parameters;
    }
}
