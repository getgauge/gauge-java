// Copyright 2015 ThoughtWorks, Inc.

// This file is part of Gauge-Java.

// This program is free software.
//
// It is dual-licensed under:
// 1) the GNU General Public License as published by the Free Software Foundation,
// either version 3 of the License, or (at your option) any later version;
// or
// 2) the Eclipse Public License v1.0.
//
// You can redistribute it and/or modify it under the terms of either license.
// We would then provide copied of each license in a separate .txt file with the name of the license as the title of the file.

package com.thoughtworks.gauge.processor;

import com.thoughtworks.gauge.ClassInstanceManager;

public enum ClearObjectCache {
    SUITE_LEVEL("suite"), SPEC_LEVEL("spec"), SCENARIO_LEVEL("scenario");
    public static final String clear_state_flag = "gauge_clear_state_level";
    private final String level;

    ClearObjectCache(String level) {
        this.level = level;
    }

    public static void clear(ClearObjectCache currentPosition) {
        String level = System.getenv(clear_state_flag);
        if (level != null && level.equals(currentPosition.level)) {
            ClassInstanceManager.clearCache();
        }
    }
}
