package com.thoughtworks.gauge;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StepRegistry {

    private static Map<String, Method> stepTextToMethodMap = new HashMap<String, Method>();

    public static void addStepImplementation(String stepText, Method method) {
        stepTextToMethodMap.put(stepText, method);
    }

    public static boolean contains(String stepText) {
        return stepTextToMethodMap.containsKey(stepText);
    }

    public static Method get(String stepText) {
        return stepTextToMethodMap.get(stepText);
    }

    public static List<String> getAllStepTexts() {
        return new ArrayList<String>(stepTextToMethodMap.keySet());
    }
}
