package com.thoughtworks.gauge.execution.parameters.parsers.types;

import static com.thoughtworks.gauge.execution.ParameterTestHelpers.parameter;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class FallbackParameterParserTest {
    private static final String A_VALUE = "a value";
    private static final Class<?> IRRELLEVANT = null;

    @Test
    public void givenAParameterWithAValueWhenParsedThenTheValueIsReturned() throws Exception {
        assertThat(new FallbackParameterParser().parse(IRRELLEVANT, parameter(A_VALUE)), equalTo((Object) A_VALUE));
    }
}
