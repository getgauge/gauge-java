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

        Byte b = Byte.valueOf("1");
        byte[] bytes = {b};
        ByteString screenShot = ByteString.copyFrom(bytes);

        Spec.ProtoExecutionResult previous = Spec.ProtoExecutionResult.newBuilder().setFailed(true).
                setExecutionTime(100).
                setRecoverableError(false).
                setErrorMessage("Previous failed").
                setStackTrace("Previous stacktrace").
                setFailureScreenshot(screenShot).build();
        Spec.ProtoExecutionResult current = Spec.ProtoExecutionResult.newBuilder().setFailed(false).setExecutionTime(1100).build();
        Spec.ProtoExecutionResult result = new TestExecutionStage().mergeExecResults(previous, current);

        assertTrue(result.getFailed());
        assertEquals(1200, result.getExecutionTime());
        assertEquals("Previous failed", result.getErrorMessage());
        assertEquals("Previous stacktrace", result.getStackTrace());
        assertFalse(result.getRecoverableError());
        assertEquals(screenShot, result.getFailureScreenshot());
    }

    public void testMergingResultsCurrentFailing() throws Exception {
        Byte b = Byte.valueOf("2");
        byte[] bytes = {b};
        ByteString screenShot = ByteString.copyFrom(bytes);

        Spec.ProtoExecutionResult previous = Spec.ProtoExecutionResult.newBuilder().setFailed(false).setExecutionTime(100).build();
        Spec.ProtoExecutionResult current = Spec.ProtoExecutionResult.newBuilder().setFailed(true).
                setExecutionTime(100).
                setRecoverableError(false).
                setErrorMessage("current failed").
                setStackTrace("current stacktrace").
                setFailureScreenshot(screenShot).build();
        Spec.ProtoExecutionResult result = new TestExecutionStage().mergeExecResults(previous, current);

        assertTrue(result.getFailed());
        assertEquals(200, result.getExecutionTime());
        assertEquals("current failed", result.getErrorMessage());
        assertEquals("current stacktrace", result.getStackTrace());
        assertFalse(result.getRecoverableError());
        assertEquals(screenShot, result.getFailureScreenshot());
    }

    public void testMergingResultsBothFailing() throws Exception {
        Byte b = Byte.valueOf("2");
        byte[] bytes = {b};
        ByteString screenShotPrevious = ByteString.copyFrom(bytes);
        ByteString screenShotCurrent = ByteString.copyFromUtf8("hello");

        Spec.ProtoExecutionResult previous = Spec.ProtoExecutionResult.newBuilder().setFailed(true).
                setExecutionTime(1001).
                setRecoverableError(true).
                setErrorMessage("previous failed").
                setStackTrace("previous stacktrace").
                setFailureScreenshot(screenShotPrevious).build();
        Spec.ProtoExecutionResult current = Spec.ProtoExecutionResult.newBuilder().setFailed(true).
                setExecutionTime(1002).
                setRecoverableError(false).
                setErrorMessage("current failed").
                setStackTrace("current stacktrace").
                setFailureScreenshot(screenShotCurrent).build();
        Spec.ProtoExecutionResult result = new TestExecutionStage().mergeExecResults(previous, current);

        assertTrue(result.getFailed());
        assertEquals(2003, result.getExecutionTime());
        assertEquals("previous failed", result.getErrorMessage());
        assertEquals("previous stacktrace", result.getStackTrace());
        assertFalse(result.getRecoverableError());
        assertEquals(screenShotPrevious, result.getFailureScreenshot());
    }

    public void testMergingResultsCurrentFailingAndIsRecoverable() throws Exception {
        ByteString screenShotCurrent = ByteString.copyFromUtf8("hello");

        Spec.ProtoExecutionResult previous = Spec.ProtoExecutionResult.newBuilder().setFailed(false).setExecutionTime(1001).build();
        Spec.ProtoExecutionResult current = Spec.ProtoExecutionResult.newBuilder().setFailed(true).
                setExecutionTime(1002).
                setRecoverableError(true).
                setErrorMessage("current failed").
                setStackTrace("current stacktrace").
                setFailureScreenshot(screenShotCurrent).build();
        Spec.ProtoExecutionResult result = new TestExecutionStage().mergeExecResults(previous, current);

        assertTrue(result.getFailed());
        assertEquals(2003, result.getExecutionTime());
        assertEquals("current failed", result.getErrorMessage());
        assertEquals("current stacktrace", result.getStackTrace());
        assertTrue(result.getRecoverableError());
        assertEquals(screenShotCurrent, result.getFailureScreenshot());
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
