package com.thoughtworks.gauge.execution.parameters.parsers.types;

import com.google.common.base.Throwables;
import com.thoughtworks.gauge.execution.parameters.ParsingException;
import com.thoughtworks.gauge.execution.parameters.parsers.base.BaseParameterParser;
import com.thoughtworks.gauge.execution.parameters.parsers.base.ParameterParser;

import gauge.messages.Spec;
import gauge.messages.Spec.Parameter;

public class PrimitiveParameterParser extends BaseParameterParser implements ParameterParser {
    private PrimitivesConverter primitivesConverter;

    public PrimitiveParameterParser(ParameterParser next, PrimitivesConverter primitivesConverter) {
        super(next);
        this.primitivesConverter = primitivesConverter;
    }

    @Override
    protected boolean condition(Class<?> parameterType, Parameter parameter) {
        return primitivesConverter.contains(parameterType);
    }

    @Override
    protected Object parseParameter(Class<?> parameterType, Parameter parameter) throws ParsingException {
        try {
            return primitivesConverter.convert(parameterType, parameter);
        } catch (Exception e) {
            throw new ParsingException(Spec.ProtoExecutionResult.newBuilder().setFailed(true).setExecutionTime(0)
                    .setStackTrace(Throwables.getStackTraceAsString(e))
                    .setErrorMessage(String.format("Failed to convert argument from type String to type %s. %s",
                            parameterType.toString(), e.getMessage()))
                    .build());
        }
    }

}
