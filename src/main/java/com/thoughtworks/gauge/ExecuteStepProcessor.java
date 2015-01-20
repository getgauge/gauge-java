package com.thoughtworks.gauge;

import gauge.messages.Messages;
import gauge.messages.Spec;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExecuteStepProcessor extends MethodExecutionMessageProcessor implements IMessageProcessor {

    private Map<Class<?>, StringToPrimitiveConverter> primitiveConverters = new HashMap<Class<?>, StringToPrimitiveConverter>();

    public ExecuteStepProcessor() {
        primitiveConverters.put(int.class, new StringToIntegerConverter());
        primitiveConverters.put(Integer.class, new StringToIntegerConverter());
        primitiveConverters.put(boolean.class, new StringToBooleanConverter());
        primitiveConverters.put(Boolean.class, new StringToBooleanConverter());
        primitiveConverters.put(long.class, new StringToLongConverter());
        primitiveConverters.put(Long.class, new StringToLongConverter());
        primitiveConverters.put(float.class, new StringToFloatConverter());
        primitiveConverters.put(Float.class, new StringToFloatConverter());
        primitiveConverters.put(double.class, new StringToDoubleConverter());
        primitiveConverters.put(Double.class, new StringToDoubleConverter());
        primitiveConverters.put(Table.class, new TableConverter());
    }

    @Override
    public Messages.Message process(Messages.Message message) {
        Method method = StepRegistry.get(message.getExecuteStepRequest().getParsedStepText());
        List<Spec.Parameter> args = message.getExecuteStepRequest().getParametersList();
        if (args != null && args.size() > 0) {
            Object[] parameters = new Object[args.size()];
            Class<?>[] parameterTypes = method.getParameterTypes();
            for (int i = 0; i < parameterTypes.length; i++) {
                Class<?> parameterType = parameterTypes[i];
                if (primitiveConverters.containsKey(parameterType)) {
                    parameters[i] = primitiveConverters.get(parameterType).convert(args.get(i));
                } else {
                    parameters[i] = args.get(i).getValue();
                }
            }
            return execute(method, message, parameters);
        } else {
            return execute(method, message);
        }
    }
}
