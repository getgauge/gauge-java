/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge.processor;

import com.thoughtworks.gauge.ClassInstanceManager;

public enum ClearObjectCache {
    SUITE_LEVEL("suite"), SPEC_LEVEL("spec"), SCENARIO_LEVEL("scenario");

    private static final String CLEAR_STATE_FLAG = "gauge_clear_state_level";
    private final String level;

    ClearObjectCache(String level) {
        this.level = level;
    }

    public static void clear(ClearObjectCache currentPosition, ClassInstanceManager manager) {
        String level = System.getenv(CLEAR_STATE_FLAG);
        if (level != null && level.equals(currentPosition.level)) {
            manager.clearCache();
        }
    }
}
