package com.thoughtworks.gauge.execution.parameters.parsers.base;

import com.thoughtworks.gauge.execution.parameters.ParsingException;

import gauge.messages.Spec.Parameter;

public abstract class BaseParameterParser implements ParameterParser {
    private ParameterParser next;

    public BaseParameterParser(ParameterParser next) {
        this.next = next;
    }

    protected abstract boolean condition(Class<?> parameterType, Parameter parameter);

    protected abstract Object parseParameter(Class<?> parameterType, Parameter parameter) throws ParsingException;

    @Override
    public Object parse(Class<?> parameterType, Parameter parameter) throws ParsingException {
        if (condition(parameterType, parameter)) {
            return parseParameter(parameterType, parameter);
        }

        return next.parse(parameterType, parameter);
    }
}
