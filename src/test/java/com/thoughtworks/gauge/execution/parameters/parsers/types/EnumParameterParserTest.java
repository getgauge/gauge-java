package com.thoughtworks.gauge.execution.parameters.parsers.types;

import static com.thoughtworks.gauge.execution.ParameterTestHelpers.asObject;
import static com.thoughtworks.gauge.execution.ParameterTestHelpers.parameter;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.thoughtworks.gauge.execution.parameters.parsers.base.ParameterParser;

@RunWith(MockitoJUnitRunner.class)
public class EnumParameterParserTest {
    @Mock
    private ParameterParser parameterParserMock;
    @InjectMocks
    private EnumParameterParser enumParameterParser;

    @Test
    public void givenAnEnumParameterTypeAndAValidParameterForThatEnumThenTheEnumInstanceIsReturned() throws Exception {
        assertThat(enumParameterParser.parse(TestEnum.class, parameter("FIRST")), is(asObject(TestEnum.FIRST)));
    }

    private enum TestEnum {
        FIRST
    }
}
