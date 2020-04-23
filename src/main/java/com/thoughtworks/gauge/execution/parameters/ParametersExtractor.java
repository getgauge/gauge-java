/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
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
