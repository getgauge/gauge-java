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

    @SuppressWarnings({"unchecked", "rawtypes"})
    public <T extends Enum<T>> Enum<T> getEnumInstance(Class<? extends Enum> clazz, String name) {
        return Enum.valueOf(clazz, name);
    }
}
