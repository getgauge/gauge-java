package com.thoughtworks.gauge.execution.parameters.parsers.types;

import static com.thoughtworks.gauge.execution.ParameterTestHelpers.aSpecialTableParameter;
import static com.thoughtworks.gauge.execution.ParameterTestHelpers.aTableParameter;
import static com.thoughtworks.gauge.execution.ParameterTestHelpers.nonTableParameter;
import static com.thoughtworks.gauge.test.TestValues.ANY_TYPE;
import static com.thoughtworks.gauge.test.TestValues.SPECIFIC_VALUE;
import static org.hamcrest.CoreMatchers.theInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.thoughtworks.gauge.execution.parameters.parsers.base.ParameterParser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.thoughtworks.gauge.execution.parameters.parsers.converters.TableConverter;

@RunWith(MockitoJUnitRunner.class)
public class TableParameterParserTest {
    @Mock
    private ParameterParser parameterParserMock;
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

        assertThat(tableParameterParser.parse(ANY_TYPE, aTableParameter()), theInstance(SPECIFIC_VALUE));
    }
}
