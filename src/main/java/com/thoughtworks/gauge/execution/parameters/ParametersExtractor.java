package com.thoughtworks.gauge.execution.parameters;

import java.util.List;

import com.thoughtworks.gauge.execution.parameters.parsers.base.ParameterParser;
import com.thoughtworks.gauge.execution.parameters.parsers.base.ParameterParsingChain;

import gauge.messages.Spec;

public class ParametersExtractor {
    private ParameterParser parameterParser = new ParameterParsingChain();

    public Object[] extract(List<Spec.Parameter> arguments, Class<?>[] parameterTypes) throws ParsingException {
        Object[] parameters = new Object[arguments == null ? 0 : arguments.size()];

        for (int i = 0; i < parameters.length; i++) {
            parameters[i] = parameterParser.parse(parameterTypes[i], arguments.get(i));
        }

        return parameters;
    }
}
