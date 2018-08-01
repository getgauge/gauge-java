package com.thoughtworks.gauge;

import com.google.protobuf.ByteString;
import gauge.messages.Spec;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class ScreenshotCollectorTest extends TestCase {
    public void testAddingScreenshotsToProtoResult() {
        Spec.ProtoExecutionResult executionResult = emptyExecResult();
        byte a = Byte.valueOf("1");
        byte b = Byte.valueOf("2");
        byte[] bytes = {a,b};
        List<byte[]> screenshots = new ArrayList<>();
        screenshots.add(bytes);
        Spec.ProtoExecutionResult protoExecutionResult = new ScreenshotCollector().addPendingScreenshot(executionResult, screenshots);
        List<ByteString> actualScreenshotList = protoExecutionResult.getScreenShotList();
        for (byte[] screenshot : screenshots) {
            assertTrue(actualScreenshotList.contains(ByteString.copyFrom(screenshot)));
        }
    }

    private Spec.ProtoExecutionResult emptyExecResult() {
        return Spec.ProtoExecutionResult.newBuilder().setFailed(false).setExecutionTime(0).build();
    }
}
