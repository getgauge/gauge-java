package com.thoughtworks.gauge.execution.parameters.parsers.types;

import static com.thoughtworks.gauge.execution.ParameterTestHelpers.aParameter;
import static com.thoughtworks.gauge.test.TestHelpers.asObject;
import static com.thoughtworks.gauge.test.TestValues.A_PRIMITIVE;
import static com.thoughtworks.gauge.test.TestValues.PRIMITIVE_TYPE;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.thoughtworks.gauge.execution.parameters.parsers.base.ParameterParser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PrimitiveParameterParserTest {
    @Mock
    private PrimitivesConverter primitivesConverterMock;
    @InjectMocks
    private PrimitiveParameterParser primitiveParameterParser;

    @Test
    public void givenPrimitiveTypeWhenParseAPrimitiveParameterThenThePrimitiveTypeIsConverted() throws Exception {
        when(primitivesConverterMock.contains(PRIMITIVE_TYPE)).thenReturn(true);

        primitiveParameterParser.parse(PRIMITIVE_TYPE, aParameter());

        verify(primitivesConverterMock).convert(PRIMITIVE_TYPE, aParameter());
    }

    @Test
    public void givenPrimitiveTypeWhenParseAPrimitiveParameterThenTheConvertedPrimitiveTypeIsReturned()
            throws Exception {
        when(primitivesConverterMock.contains(PRIMITIVE_TYPE)).thenReturn(true);
        when(primitivesConverterMock.convert(PRIMITIVE_TYPE, aParameter())).thenReturn(A_PRIMITIVE);

        assertThat(primitiveParameterParser.parse(PRIMITIVE_TYPE, aParameter()), sameInstance(asObject(A_PRIMITIVE)));
    }
}
