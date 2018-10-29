package com.thoughtworks.gauge.execution.parameters;

import com.thoughtworks.gauge.execution.parameters.parsers.base.ParameterParsingChain;
import gauge.messages.Spec;

import java.util.List;

public class ParametersExtractor {

    public Object[] extract(List<Spec.Parameter> arguments, Class<?>[] parameterTypes) throws ParsingException {
        Object[] parameters = new Object[arguments == null ? 0 : arguments.size()];

        for (int i = 0; i < parameters.length; i++) {
            parameters[i] = new ParameterParsingChain().parse(parameterTypes[i], arguments.get(i));
        }

        return parameters;
    }
}
