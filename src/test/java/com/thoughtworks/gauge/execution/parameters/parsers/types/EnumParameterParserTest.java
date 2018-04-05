package com.thoughtworks.gauge.execution.parameters.parsers.types;

import static com.thoughtworks.gauge.execution.ParameterTestHelpers.parameter;
import static com.thoughtworks.gauge.test.TestHelpers.asObject;
import static com.thoughtworks.gauge.test.TestValues.A_NON_ENUM_TYPE;
import static com.thoughtworks.gauge.test.TestValues.A_VALUE;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

import com.thoughtworks.gauge.execution.parameters.parsers.base.ParameterParser;
import com.thoughtworks.gauge.test.TestValues;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.thoughtworks.gauge.test.AnEnum;

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
