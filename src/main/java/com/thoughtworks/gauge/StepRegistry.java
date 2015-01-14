package com.thoughtworks.gauge;

import java.lang.reflect.Method;
import java.util.*;

public class StepRegistry {

    private static Map<String, Method> stepTextToMethodMap = new HashMap<String, Method>();
    private static Map<Method, String> methodToFileMap = new HashMap<Method, String>();
    private static Map<String, StepValue> stepTextToStepValue = new HashMap<String, StepValue>();

    public static void addStepImplementation(StepValue stepValue, Method method, String fileName) {
        stepTextToMethodMap.put(stepValue.getStepText(), method);
        stepTextToStepValue.put(stepValue.getStepText(), stepValue);
        methodToFileMap.put(method,fileName);
    }

    public static boolean contains(String stepText) {
        return stepTextToMethodMap.containsKey(stepText);
    }

    public static Method get(String stepText) {
        return stepTextToMethodMap.get(stepText);
    }
    public static String getFileName(Method method) {
        return methodToFileMap.get(method);
    }

    public static List<String> getAllStepTexts() {
        List<String> stepTexts = new ArrayList<String>();
        for (StepValue stepValue : stepTextToStepValue.values()) {
            stepTexts.add(stepValue.getStepAnnotationText());
        }
        return stepTexts;
    }

    public static List<String> getStepAnnotationFor(Set<String> stepTexts) {
        List<String> stepValues = new ArrayList<String>();
        for (String stepText : stepTexts) {
            for (StepValue stepValue : stepTextToStepValue.values()) {
                if (stepValue.getStepText().equals(stepText)){
                    stepValues.add(stepValue.getStepAnnotationText());
                }
            }
        }
        return stepValues;
    }

    public static Set<String> getAliasStepTexts(String stepText) {
        Method stepTextMethod = stepTextToMethodMap.get(stepText);
        for (Method method : stepTextToMethodMap.values()) {
            if (method.equals(stepTextMethod)) {
                return getKeysByValue(stepTextToMethodMap, method);
            }
        }
        return new HashSet<String>();
    }

    private static <T, E> Set<T> getKeysByValue(Map<T, E> map, E value) {
        Set<T> keys = new HashSet<T>();
        for (Map.Entry<T, E> entry : map.entrySet()) {
            if (value.equals(entry.getValue())) {
                keys.add(entry.getKey());
            }
        }
        return keys;
    }
}
