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

public class EnumParameterParser implements ParameterParser {
    public static final String ENUM_VALUE_NOT_FOUND_MESSAGE = "%s is not an enum value of %s.";

    @Override
    public boolean canParse(Class<?> parameterType, Parameter parameter) {
        return parameterType.isEnum();
    }

    @Override
    public Object parse(Class<?> parameterType, Parameter parameter) throws ParsingException {
        @SuppressWarnings("unchecked")
        Class<? extends Enum<?>> enumClass = (Class<? extends Enum<?>>) parameterType;
        String enumValue = parameter.getValue();
        try {
            return getEnumInstance(enumClass, enumValue);
        } catch (IllegalArgumentException e) {
            throw new ParsingException(Spec.ProtoExecutionResult.newBuilder().setFailed(true).setExecutionTime(0)
                    .setStackTrace(Throwables.getStackTraceAsString(e))
                    .setErrorMessage(String.format(ENUM_VALUE_NOT_FOUND_MESSAGE, enumValue, enumClass.getSimpleName()))
                    .build());
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public <T extends Enum<T>> Enum<T> getEnumInstance(Class<? extends Enum> clazz, String name) {
        return Enum.valueOf(clazz, name);
    }
}
