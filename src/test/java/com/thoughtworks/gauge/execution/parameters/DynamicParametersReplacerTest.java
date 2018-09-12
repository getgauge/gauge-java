package com.thoughtworks.gauge.execution.parameters;

import com.thoughtworks.gauge.execution.ExecutionInfoMapper;
import gauge.messages.Messages;
import gauge.messages.Spec;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static com.thoughtworks.gauge.execution.parameters.DynamicParametersReplacer.replacePlaceholders;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Messages.StepInfo.class, Messages.ExecuteStepRequest.class})
public class DynamicParametersReplacerTest {

    private Messages.StepInfo stepInfo = mock(Messages.StepInfo.class);
    private Messages.ExecuteStepRequest executeStepRequest = mock(Messages.ExecuteStepRequest.class);
    private ExecutionInfoMapper executionInfoMapper = new ExecutionInfoMapper();

    @Test
    public void shouldReplacePlaceholdersWhenMethodParametersAreIncorrect() {
        assertThat(replacePlaceholders("Step text", emptyList())).isEqualTo("Step text");
        assertThat(replacePlaceholders("Step text", null)).isEqualTo("Step text");
        assertThat(replacePlaceholders(null, null)).isNull();
        assertThat(replacePlaceholders("", null)).isEqualTo("");
        assertThat(replacePlaceholders("", emptyList())).isEqualTo("");
        assertThat(replacePlaceholders("", singletonList(dynamicParameter("parameter value")))).isEqualTo("");
        assertThat(replacePlaceholders(null, singletonList(staticParameter("parameter value")))).isNull();
    }

    @Test
    public void shouldNotReplacePlaceholdersForNotDynamicParameters() {
        assertThat(replacePlaceholders("User makes login from <clientType>", singletonList(staticParameter("Facebook Web"))))
                .isEqualTo("User makes login from <clientType>");
    }

    @Test
    public void shouldReplacePlaceholdersForAllDynamicParameters() {
        assertThat(replacePlaceholders("User <username> makes login from <clientType>", asList(dynamicParameter("Elon"), dynamicParameter("Facebook Web"))))
                .isEqualTo("User \"Elon\" makes login from \"Facebook Web\"");
    }

    @Test
    public void shouldReplacePlaceholdersForDynamicParametersWith$() {
        assertThat(replacePlaceholders("User <username> makes login from <clientType>", asList(dynamicParameter("Elon"), dynamicParameter("!@#%^&*()__-++~`$\"':;?/<>.,\\\\\\"))))
                .isEqualTo("User \"Elon\" makes login from \"!@#%^&*()__-++~`$\"':;?/<>.,\\\\\\\"");
    }

    @Test
    public void shouldReplacePlaceholdersOnlyForDynamicParameters() {
        assertThat(replacePlaceholders("User <username> makes login from \"Web\"", asList(dynamicParameter("Elon"), staticParameter("Facebook Web"))))
                .isEqualTo("User \"Elon\" makes login from \"Web\"");
    }

    @Test
    public void shouldGetEmptyStepTextWhenStepIsNotInitialized() {
        when(stepInfo.isInitialized()).thenReturn(false);

        assertThat(executionInfoMapper.stepFrom(stepInfo).getDynamicText()).isEmpty();
    }

    @Test
    public void shouldGetActualStepTextWhenStepParametersListIsEmpty() {
        when(stepInfo.getStep()).thenReturn(executeStepRequest);
        when(stepInfo.isInitialized()).thenReturn(true);
        when(executeStepRequest.getActualStepText()).thenReturn("User makes login");
        when(executeStepRequest.getParametersList()).thenReturn(emptyList());

        assertThat(executionInfoMapper.stepFrom(stepInfo).getDynamicText()).isEqualTo("User makes login");
    }

    @Test
    public void shouldGetActualStepTextWhenStepParametersAreStatic() {
        when(stepInfo.getStep()).thenReturn(executeStepRequest);
        when(stepInfo.isInitialized()).thenReturn(true);
        when(executeStepRequest.getActualStepText()).thenReturn("User \"Elon\" makes login from \"Facebook Web\"");
        when(executeStepRequest.getParametersList()).thenReturn(asList(staticParameter("Elon"), staticParameter("Facebook Web")));

        assertThat(executionInfoMapper.stepFrom(stepInfo).getDynamicText()).isEqualTo("User \"Elon\" makes login from \"Facebook Web\"");
    }

    @Test
    public void shouldGetUpdatedStepTextWhenStepParametersAreDynamic() {
        when(stepInfo.getStep()).thenReturn(executeStepRequest);
        when(stepInfo.isInitialized()).thenReturn(true);
        when(executeStepRequest.getActualStepText()).thenReturn("User <username> makes login from <clientType>");
        when(executeStepRequest.getParametersList()).thenReturn(asList(dynamicParameter("Elon"), dynamicParameter("Facebook Web")));

        assertThat(executionInfoMapper.stepFrom(stepInfo).getDynamicText()).isEqualTo("User \"Elon\" makes login from \"Facebook Web\"");
    }

    private Spec.Parameter dynamicParameter(String value) {
        return parameter(value, Spec.Parameter.ParameterType.Dynamic);
    }

    private Spec.Parameter staticParameter(String value) {
        return parameter(value, Spec.Parameter.ParameterType.Static);
    }

    private Spec.Parameter parameter(String value, Spec.Parameter.ParameterType parameterType) {
        return Spec.Parameter.newBuilder().setParameterType(parameterType).setValue(value).build();
    }
}
