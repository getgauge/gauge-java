/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge.execution.parameters.parsers.types;

import com.thoughtworks.gauge.test.AnEnum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.thoughtworks.gauge.execution.ParameterTestHelpers.parameter;
import static com.thoughtworks.gauge.test.TestHelpers.asObject;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@ExtendWith(MockitoExtension.class)
public class EnumParameterParserTest {
    @InjectMocks
    private EnumParameterParser enumParameterParser;

    @Test
    public void givenAnEnumParameterTypeAndAValidParameterForThatEnumThenTheEnumInstanceIsReturned() throws Exception {
        assertThat(enumParameterParser.parse(AnEnum.class, parameter(AnEnum.VALUE.name())),
                equalTo(asObject(AnEnum.VALUE)));
    }
}
