/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge.execution.parameters.parsers.types;

import com.thoughtworks.gauge.test.AnEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static com.thoughtworks.gauge.execution.ParameterTestHelpers.parameter;
import static com.thoughtworks.gauge.test.TestHelpers.asObject;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class EnumParameterParserTest {
    @InjectMocks
    private EnumParameterParser enumParameterParser;

    @Test
    public void givenAnEnumParameterTypeAndAValidParameterForThatEnumThenTheEnumInstanceIsReturned() throws Exception {
        assertThat(enumParameterParser.parse(AnEnum.class, parameter(AnEnum.VALUE.name())),
                equalTo(asObject(AnEnum.VALUE)));
    }
}
