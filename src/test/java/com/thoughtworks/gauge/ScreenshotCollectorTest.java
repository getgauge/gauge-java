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

package com.thoughtworks.gauge;

import gauge.messages.Spec;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class ScreenshotCollectorTest extends TestCase {
    public void testAddingScreenshotsToProtoResult() {
        Spec.ProtoExecutionResult executionResult = emptyExecResult();
        String a = "1";
        String b = "2";
        List<String> screenshots = new ArrayList<>();
        screenshots.add(a);
        screenshots.add(b);
        Spec.ProtoExecutionResult protoExecutionResult = new ScreenshotCollector().addPendingScreenshot(executionResult, screenshots);
        List<String> actualScreenshotList = protoExecutionResult.getScreenshotFilesList();
        for (String screenshot : screenshots) {
            assertTrue(actualScreenshotList.contains(screenshot));
        }
    }

    private Spec.ProtoExecutionResult emptyExecResult() {
        return Spec.ProtoExecutionResult.newBuilder().setFailed(false).setExecutionTime(0).build();
    }
}
