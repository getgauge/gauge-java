package com.thoughtworks.gauge.execution.parameters.parsers.types;

import com.thoughtworks.gauge.test.AnEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import static com.thoughtworks.gauge.execution.ParameterTestHelpers.parameter;
import static com.thoughtworks.gauge.test.TestHelpers.asObject;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

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
