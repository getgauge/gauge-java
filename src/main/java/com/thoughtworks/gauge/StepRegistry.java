package com.thoughtworks.gauge;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public static String getStepAnnotationFor(String stepText) {
        for (StepValue stepValue : stepTextToStepValue.values()) {
            if (stepValue.getStepText().equals(stepText)){
                return stepValue.getStepAnnotationText();
            }
        }
        return null;
    }
}
