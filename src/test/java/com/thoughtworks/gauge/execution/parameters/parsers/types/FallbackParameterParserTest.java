package com.thoughtworks.gauge.execution.parameters.parsers.types;

import static com.thoughtworks.gauge.execution.ParameterTestHelpers.parameter;
import static com.thoughtworks.gauge.test.TestValues.ANY_TYPE;
import static com.thoughtworks.gauge.test.TestValues.A_VALUE;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class FallbackParameterParserTest {
    @Test
    public void givenAParameterWithAValueWhenParsedThenTheValueIsReturned() throws Exception {
        assertThat(new FallbackParameterParser().parse(ANY_TYPE, parameter(A_VALUE)), equalTo((Object) A_VALUE));
    }
}
