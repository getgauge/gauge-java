package com.thoughtworks.gauge.execution.parameters;

import static com.thoughtworks.gauge.execution.ParameterTestHelpers.aParameter;
import static com.thoughtworks.gauge.test.Matchers.aslist;
import static com.thoughtworks.gauge.test.Matchers.containsOnly;
import static com.thoughtworks.gauge.test.TestHelpers.array;
import static com.thoughtworks.gauge.test.TestValues.ANY_TYPE;
import static com.thoughtworks.gauge.test.TestValues.SPECIFIC_VALUE;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.thoughtworks.gauge.execution.parameters.parsers.base.ParameterParser;

import gauge.messages.Spec.Parameter;

@RunWith(MockitoJUnitRunner.class)
public class ParametersExtractorTest {
    private static final Object[] EMPTY = array();
    private static final Class<?>[] NO_PARAMETER_TYPES = array();
    private static final List<Parameter> NO_PARAMETERS = emptyList();
    private static final Object ANOTHER_SPECIFIC_VALUE = new Object();

    @InjectMocks
    private ParametersExtractor parametersExtractor;
    @Mock
    private ParameterParser parameterParser;

    @Test
    public void whenExtractNoParametersThenNoParametersReturned() throws Exception {
        assertThat(parametersExtractor.extract(NO_PARAMETERS, NO_PARAMETER_TYPES), is(EMPTY));
    }

    @Test
    public void whenExtractNullThenNoParametersReturned() throws Exception {
        assertThat(parametersExtractor.extract(null, NO_PARAMETER_TYPES), is(EMPTY));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void whenExtractAParameterThenTheParameterIsParsed() throws Exception {
        parametersExtractor.extract(asList(aParameter()), array(ANY_TYPE));

        verify(parameterParser).parse(ANY_TYPE, aParameter());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void whenExtractAParameterThenTheParsedParameterIsReturned() throws Exception {
        when(parameterParser.parse(ANY_TYPE, aParameter())).thenReturn(SPECIFIC_VALUE);

        assertThat(parametersExtractor.extract(asList(aParameter()), array(ANY_TYPE)),
                aslist(containsOnly(sameInstance(SPECIFIC_VALUE))));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void whenExtractMultipleParametersThenTheParsedParametersAreReturned() throws Exception {
        when(parameterParser.parse(ANY_TYPE, aParameter())).thenReturn(SPECIFIC_VALUE, ANOTHER_SPECIFIC_VALUE);

        assertThat(parametersExtractor.extract(asList(aParameter(), aParameter()), array(ANY_TYPE, ANY_TYPE)),
                aslist(containsOnly(sameInstance(SPECIFIC_VALUE), sameInstance(ANOTHER_SPECIFIC_VALUE))));
    }
}
