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

package com.thoughtworks.gauge.execution.parameters.parsers.base;

import com.google.common.base.Throwables;
import com.thoughtworks.gauge.execution.parameters.ParsingException;
import gauge.messages.Spec;

/**
 * Extension hook for custom parameter parser implementations.
 *
 * An example:
 *
 * Given the following custom model class
 * <code>
 *     public class Person {
 *         private String name;
 *
 *         public Person(String name) {
 *             this.name = name;
 *         }
 *     }
 * </code>
 *
 * One can create a <tt>CustomParameterParser</tt> like
 * <code>
 *     public class PersonGaugeParser extends CustomParameterParser<Person> {
 *         @Override
 *         public boolean canParse(Class<?> aClass, Spec.Parameter parameter) {
 *             return aClass.equals(Person.class);
 *         }
 *
 *
 *         @Override
 *         protected Person customParse(Class<?> aClass, Spec.Parameter parameter) {
 *             return new Person(parameter.getValue());
 *         }
 *     }
 * </code>
 *
 * and then use in a step implementation
 *
 * <code>
 *     @Step("Create user <user>")
 *     public void createUser(Person person) {
 *         Assert.assertEquals(person.getUserName(), "John Doe");
 *     }
 * </code>
 */
public abstract class CustomParameterParser<T> implements ParameterParser {
    @Override
    public final T parse(Class<?> parameterType, Spec.Parameter parameter) throws ParsingException {
        try {
            return customParse(parameterType, parameter);
        } catch (Throwable e) {
            throw new ParsingException(Spec.ProtoExecutionResult.newBuilder().setFailed(true).setExecutionTime(0)
                    .setStackTrace(Throwables.getStackTraceAsString(e))
                    .setErrorMessage(String.format("Failed to convert argument from type String to type %s. %s",
                            parameterType.toString(), e.getMessage()))
                    .build());
        }
    }

    protected abstract T customParse(Class<?> parameterType, Spec.Parameter parameter);
}
