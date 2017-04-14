package com.thoughtworks.gauge.execution.parameters.parsers.types;

import com.thoughtworks.gauge.execution.parameters.ParsingException;
import com.thoughtworks.gauge.execution.parameters.parsers.base.ParameterParser;

import gauge.messages.Spec.Parameter;

public class FallbackParameterParser implements ParameterParser {
    @Override
    public Object parse(Class<?> parameterType, Parameter parameter) throws ParsingException {
        return parameter.getValue();
    }
}
