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

package com.thoughtworks.gauge.scan;

import com.thoughtworks.gauge.refactor.Util;
import com.thoughtworks.gauge.registry.StepRegistry;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class StaticScannerTest {

    private static final String STEP_TEXT = "new step";
    private static final String IMPL_FILE = Util.workingDir() + File.separator + String.format("src%stest%sresources%stest%sfiles%sfoo.java", File.separator, File.separator, File.separator, File.separator, File.separator);

    @Test
    public void ShouldAddStepsFromFile() {
        StaticScanner staticScanner = new StaticScanner();
        staticScanner.addStepsFromFile(IMPL_FILE);
        StepRegistry registry = staticScanner.getRegistry();
        assertTrue(registry.contains(STEP_TEXT));
    }

    @Test
    public void ShouldRemoveStepsFromFile() {
        StaticScanner staticScanner = new StaticScanner();
        staticScanner.addStepsFromFile(IMPL_FILE);
        StepRegistry registry = staticScanner.getRegistry();

        assertTrue(registry.contains(STEP_TEXT));
        registry.removeSteps(IMPL_FILE);
        assertFalse(registry.contains(STEP_TEXT));
    }

    @Test
    public void ShouldReloadStepsFromFile() {
        StaticScanner staticScanner = new StaticScanner();
        staticScanner.addStepsFromFile(IMPL_FILE);
        StepRegistry registry = staticScanner.getRegistry();
        assertTrue(registry.contains(STEP_TEXT));
        staticScanner.reloadSteps(IMPL_FILE);
        assertTrue(registry.contains(STEP_TEXT));
    }
}