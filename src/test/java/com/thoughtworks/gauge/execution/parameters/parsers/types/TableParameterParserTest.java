/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge.execution.parameters.parsers.types;

import com.thoughtworks.gauge.execution.parameters.parsers.converters.TableConverter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.thoughtworks.gauge.execution.ParameterTestHelpers.aSpecialTableParameter;
import static com.thoughtworks.gauge.execution.ParameterTestHelpers.aTableParameter;
import static com.thoughtworks.gauge.test.TestValues.ANY_TYPE;
import static com.thoughtworks.gauge.test.TestValues.SPECIFIC_VALUE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TableParameterParserTest {
    @Mock
    private TableConverter tableConverterMock;
    @InjectMocks
    private TableParameterParser tableParameterParser;

    @Test
    public void whenATableParameterParsedThenTheTableConverterIsPassedThatParameter() throws Exception {
        tableParameterParser.parse(ANY_TYPE, aTableParameter());

        verify(tableConverterMock).convert(aTableParameter());
    }

    @Test
    public void whenASpecialTableParameterParsedThenTheTableConverterIsPassedThatParameter() throws Exception {
        tableParameterParser.parse(ANY_TYPE, aSpecialTableParameter());

        verify(tableConverterMock).convert(aSpecialTableParameter());
    }

    @Test
    public void whenATableParameterParsedThenTheTheConvertedTableIsReturned() throws Exception {
        when(tableConverterMock.convert(aTableParameter())).thenReturn(SPECIFIC_VALUE);

        assertThat(tableParameterParser.parse(ANY_TYPE, aTableParameter())).isSameAs(SPECIFIC_VALUE);
    }
}
