/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge.execution.parameters.parsers.base;

import com.thoughtworks.gauge.execution.parameters.ParsingException;
import gauge.messages.Spec.Parameter;

public interface ParameterParser {
    boolean canParse(Class<?> parameterType, Parameter parameter);

    Object parse(Class<?> parameterType, Parameter parameter) throws ParsingException;
}
