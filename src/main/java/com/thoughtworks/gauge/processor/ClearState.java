package com.thoughtworks.gauge.processor;

import com.thoughtworks.gauge.ClassInstanceManager;

public enum ClearState {
    SUITE_LEVEL("suite_level"), SPEC_LEVEL("spec_level"), SCENARIO_LEVEL("scenario_level");
    private final String level;

    ClearState(String level) {
        this.level = level;
    }

    public static final String clear_state_flag = "clear_state";

    public static void clear(ClearState currentPosition){
        String level = System.getenv(clear_state_flag);
        if(level != null && level.equals(currentPosition.level)){
            ClassInstanceManager.clearCache();
        }
    }
}
