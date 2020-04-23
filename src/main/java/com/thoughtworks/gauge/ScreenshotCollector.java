/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge;

import gauge.messages.Spec;

import java.util.ArrayList;
import java.util.List;

import static com.thoughtworks.gauge.Gauge.getScreenshots;

public class ScreenshotCollector {
    public Spec.ProtoExecutionResult addPendingScreenshotTo(Spec.ProtoExecutionResult result) {
        ArrayList<String> screenshots = getAllPendingScreenshots();
        return addPendingScreenshot(result, screenshots);
    }

    Spec.ProtoExecutionResult addPendingScreenshot(Spec.ProtoExecutionResult result, List<String> screenshotsFileName) {
        Spec.ProtoExecutionResult.Builder builder = Spec.ProtoExecutionResult.newBuilder(result);
        builder.addAllScreenshotFiles(screenshotsFileName);
        return builder.build();
    }

    private ArrayList<String> getAllPendingScreenshots() {
        ArrayList<String> pendingScreenshots = new ArrayList<>(getScreenshots());
        clear();
        return pendingScreenshots;
    }

    private void clear() {
        getScreenshots().clear();
    }
}
