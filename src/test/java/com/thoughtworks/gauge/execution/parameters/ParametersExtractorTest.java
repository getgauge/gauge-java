/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge.execution.parameters;

import com.thoughtworks.gauge.execution.parameters.parsers.base.ParameterParser;
import gauge.messages.Spec.Parameter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.thoughtworks.gauge.execution.ParameterTestHelpers.aParameter;
import static com.thoughtworks.gauge.test.TestValues.ANY_TYPE;
import static com.thoughtworks.gauge.test.TestValues.SPECIFIC_VALUE;
import static java.util.Arrays.asList;
import static java.util.Arrays.stream;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ParametersExtractorTest {
    private static final Object[] EMPTY = {};
    private static final Class<?>[] NO_PARAMETER_TYPES = {};
    private static final List<Parameter> NO_PARAMETERS = emptyList();
    private static final Object ANOTHER_SPECIFIC_VALUE = new Object();

    @InjectMocks
    private ParametersExtractor parametersExtractor;
    @Mock
    private ParameterParser parameterParser;

    @Test
    public void whenExtractNoParametersThenNoParametersReturned() throws Exception {
        assertThat(parametersExtractor.extract(NO_PARAMETERS, NO_PARAMETER_TYPES)).isEqualTo(EMPTY);
    }

    @Test
    public void whenExtractNullThenNoParametersReturned() throws Exception {
        assertThat(parametersExtractor.extract(null, NO_PARAMETER_TYPES)).isEqualTo(EMPTY);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void whenExtractAParameterThenTheParameterIsParsed() throws Exception {
        parametersExtractor.extract(List.of(aParameter()), new Class<?>[]{ANY_TYPE});

        verify(parameterParser).parse(ANY_TYPE, aParameter());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void whenExtractAParameterThenTheParsedParameterIsReturned() throws Exception {
        when(parameterParser.parse(ANY_TYPE, aParameter())).thenReturn(SPECIFIC_VALUE);

        Object[] extract = parametersExtractor.extract(singletonList(aParameter()), new Class<?>[]{ANY_TYPE});
        assertThat(stream(extract).filter(v -> v == SPECIFIC_VALUE).count()).isEqualTo(Long.valueOf(extract.length));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void whenExtractMultipleParametersThenTheParsedParametersAreReturned() throws Exception {
        when(parameterParser.parse(ANY_TYPE, aParameter())).thenReturn(SPECIFIC_VALUE, ANOTHER_SPECIFIC_VALUE);

        Object[] extract = parametersExtractor.extract(asList(aParameter(), aParameter()), new Class<?>[]{ANY_TYPE, ANY_TYPE});
        assertThat(stream(extract).filter(v -> v == SPECIFIC_VALUE || v == ANOTHER_SPECIFIC_VALUE).count()).isEqualTo(Long.valueOf(extract.length));
    }
}
