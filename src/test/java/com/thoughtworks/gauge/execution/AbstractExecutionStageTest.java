package com.thoughtworks.gauge.execution;

import com.google.protobuf.ByteString;
import gauge.messages.Spec;
import junit.framework.TestCase;

public class AbstractExecutionStageTest extends TestCase {

    public void testMergingSimpleResultsBothPassing() throws Exception {
        Spec.ProtoExecutionResult previous = Spec.ProtoExecutionResult.newBuilder().setFailed(false).setExecutionTime(1000).build();
        Spec.ProtoExecutionResult current = Spec.ProtoExecutionResult.newBuilder().setFailed(false).setExecutionTime(1100).build();
        Spec.ProtoExecutionResult result = new TestExecutionStage().mergeExecResults(previous, current);
        assertFalse(result.getFailed());
        assertEquals(2100, result.getExecutionTime());
    }

    public void testMergingResultsPreviousFailing() throws Exception {

        Byte aByte = new Byte("1");
        byte b = aByte.byteValue();
        byte[] bytes = {b};
        ByteString screenShot = ByteString.copyFrom(bytes);

        Spec.ProtoExecutionResult previous = Spec.ProtoExecutionResult.newBuilder().setFailed(true).
                                                                                    setExecutionTime(100).
                                                                                    setRecoverableError(false).
                                                                                    setErrorMessage("Previous failed").
                                                                                    setStackTrace("Previous stacktrace").
                                                                                    setScreenShot(screenShot).build();
        Spec.ProtoExecutionResult current = Spec.ProtoExecutionResult.newBuilder().setFailed(false).setExecutionTime(1100).build();
        Spec.ProtoExecutionResult result = new TestExecutionStage().mergeExecResults(previous, current);

        assertTrue(result.getFailed());
        assertEquals(1200, result.getExecutionTime());
        assertEquals("Previous failed", result.getErrorMessage());
        assertEquals("Previous stacktrace", result.getStackTrace());
        assertFalse(result.getRecoverableError());
        assertEquals(screenShot, result.getScreenShot());
    }

    public void testMergingResultsCurrentFailing() throws Exception {
        Byte aByte = new Byte("2");
        byte b = aByte.byteValue();
        byte[] bytes = {b};
        ByteString screenShot = ByteString.copyFrom(bytes);

        Spec.ProtoExecutionResult previous = Spec.ProtoExecutionResult.newBuilder().setFailed(false).setExecutionTime(100).build();
        Spec.ProtoExecutionResult current = Spec.ProtoExecutionResult.newBuilder().setFailed(true).
                setExecutionTime(100).
                setRecoverableError(false).
                setErrorMessage("current failed").
                setStackTrace("current stacktrace").
                setScreenShot(screenShot).build();
        Spec.ProtoExecutionResult result = new TestExecutionStage().mergeExecResults(previous, current);

        assertTrue(result.getFailed());
        assertEquals(200, result.getExecutionTime());
        assertEquals("current failed", result.getErrorMessage());
        assertEquals("current stacktrace", result.getStackTrace());
        assertFalse(result.getRecoverableError());
        assertEquals(screenShot, result.getScreenShot());
    }

    public void testMergingResultsBothFailing() throws Exception {
        Byte aByte = new Byte("2");
        byte b = aByte.byteValue();
        byte[] bytes = {b};
        ByteString screenShotPrevious = ByteString.copyFrom(bytes);
        ByteString screenShotCurrent = ByteString.copyFromUtf8("hello");

        Spec.ProtoExecutionResult previous = Spec.ProtoExecutionResult.newBuilder().setFailed(true).
                setExecutionTime(1001).
                setRecoverableError(true).
                setErrorMessage("previous failed").
                setStackTrace("previous stacktrace").
                setScreenShot(screenShotPrevious).build();
        Spec.ProtoExecutionResult current = Spec.ProtoExecutionResult.newBuilder().setFailed(true).
                setExecutionTime(1002).
                setRecoverableError(false).
                setErrorMessage("current failed").
                setStackTrace("current stacktrace").
                setScreenShot(screenShotCurrent).build();
        Spec.ProtoExecutionResult result = new TestExecutionStage().mergeExecResults(previous, current);

        assertTrue(result.getFailed());
        assertEquals(2003, result.getExecutionTime());
        assertEquals("previous failed", result.getErrorMessage());
        assertEquals("previous stacktrace", result.getStackTrace());
        assertFalse(result.getRecoverableError());
        assertEquals(screenShotPrevious, result.getScreenShot());
    }



    private class TestExecutionStage extends AbstractExecutionStage {
        protected ExecutionStage next() {
            return null;
        }

        public void setNextStage(ExecutionStage stage) {

        }

        public Spec.ProtoExecutionResult execute(Spec.ProtoExecutionResult result) {
            return null;
        }
    }
}