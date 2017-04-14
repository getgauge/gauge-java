package com.thoughtworks.gauge.execution.parameters.parsers.base;

import com.thoughtworks.gauge.execution.parameters.ParsingException;

import gauge.messages.Spec.Parameter;

public interface ParameterParser {
    Object parse(Class<?> parameterType, Parameter parameter) throws ParsingException;
}
