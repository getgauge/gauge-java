/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge;

import gauge.messages.Spec;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ScreenshotCollectorTest {
    @Test
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
