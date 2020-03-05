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

package com.thoughtworks.gauge.execution.parameters.parsers.types;

import com.google.common.base.Throwables;
import com.thoughtworks.gauge.execution.parameters.ParsingException;
import com.thoughtworks.gauge.execution.parameters.parsers.base.ParameterParser;
import gauge.messages.Spec;
import gauge.messages.Spec.Parameter;

public class PrimitiveParameterParser implements ParameterParser {
    private PrimitivesConverter primitivesConverter;

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
