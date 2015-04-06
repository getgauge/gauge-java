package com.thoughtworks.gauge.processor;

import com.thoughtworks.gauge.ClassInstanceManager;

public enum ClearState {
    SUITE_LEVEL("suite"), SPEC_LEVEL("spec"), SCENARIO_LEVEL("scenario");
    private final String level;

    ClearState(String level) {
        this.level = level;
    }

    public static final String clear_state_flag = "gauge_clear_state_level";

    public static void clear(ClearState currentPosition){
        String level = System.getenv(clear_state_flag);
        if(level != null && level.equals(currentPosition.level)){
            ClassInstanceManager.clearCache();
        }
    }
}
