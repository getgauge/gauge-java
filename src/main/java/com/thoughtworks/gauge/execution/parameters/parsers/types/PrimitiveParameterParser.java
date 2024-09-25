/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge.execution.parameters.parsers.types;

import com.google.common.base.Throwables;
import com.thoughtworks.gauge.execution.parameters.ParsingException;
import com.thoughtworks.gauge.execution.parameters.parsers.base.ParameterParser;
import gauge.messages.Spec;
import gauge.messages.Spec.Parameter;

public class PrimitiveParameterParser implements ParameterParser {
    private final PrimitivesConverter primitivesConverter;

    public PrimitiveParameterParser(PrimitivesConverter primitivesConverter) {
        this.primitivesConverter = primitivesConverter;
    }

    @Override
    public boolean canParse(Class<?> parameterType, Parameter parameter) {
        return primitivesConverter.contains(parameterType);
    }

    @Override
    public Object parse(Class<?> parameterType, Parameter parameter) throws ParsingException {
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
