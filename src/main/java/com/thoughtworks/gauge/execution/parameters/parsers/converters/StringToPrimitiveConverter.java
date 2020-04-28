/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge.execution.parameters.parsers.converters;

import gauge.messages.Spec;

public interface StringToPrimitiveConverter {
    Object convert(Spec.Parameter source) throws Exception;
}
