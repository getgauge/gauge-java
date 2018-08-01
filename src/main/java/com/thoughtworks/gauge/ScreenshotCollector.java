package com.thoughtworks.gauge;

import com.google.protobuf.ByteString;
import gauge.messages.Spec;

import java.util.ArrayList;
import java.util.List;

import static com.thoughtworks.gauge.Gauge.getScreenshots;

public class ScreenshotCollector {
    public Spec.ProtoExecutionResult addPendingScreenshotTo(Spec.ProtoExecutionResult result) {
        List<byte[]> screenshots = getAllPendingScreenshots();
        return addPendingScreenshot(result, screenshots);
    }

    Spec.ProtoExecutionResult addPendingScreenshot(Spec.ProtoExecutionResult result, List<byte[]> screenshots) {
        Spec.ProtoExecutionResult.Builder builder = Spec.ProtoExecutionResult.newBuilder(result);
        for (byte[] screenshot : screenshots) {
            builder.addScreenShot(ByteString.copyFrom(screenshot));
        }
        return builder.build();
    }

    private List<byte[]> getAllPendingScreenshots() {
        List<byte[]> pendingScreenshots = new ArrayList<>(getScreenshots());
        clear();
        return pendingScreenshots;
    }

    private void clear() {
        getScreenshots().clear();
    }
}
