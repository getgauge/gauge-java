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
        String screenShot = "1";

        Spec.ProtoExecutionResult previous = Spec.ProtoExecutionResult.newBuilder().setFailed(true).
                setExecutionTime(100).
                setRecoverableError(false).
                setErrorMessage("Previous failed").
                setStackTrace("Previous stacktrace").
                setFailureScreenshotFile(screenShot).build();
        Spec.ProtoExecutionResult current = Spec.ProtoExecutionResult.newBuilder().setFailed(false).setExecutionTime(1100).build();
        Spec.ProtoExecutionResult result = new TestExecutionStage().mergeExecResults(previous, current);

        assertTrue(result.getFailed());
        assertEquals(1200, result.getExecutionTime());
        assertEquals("Previous failed", result.getErrorMessage());
        assertEquals("Previous stacktrace", result.getStackTrace());
        assertFalse(result.getRecoverableError());
        assertEquals(screenShot, result.getFailureScreenshotFile());
    }

    public void testMergingResultsCurrentFailing() throws Exception {
        String screenShot = "2";

        Spec.ProtoExecutionResult previous = Spec.ProtoExecutionResult.newBuilder().setFailed(false).setExecutionTime(100).build();
        Spec.ProtoExecutionResult current = Spec.ProtoExecutionResult.newBuilder().setFailed(true).
                setExecutionTime(100).
                setRecoverableError(false).
                setErrorMessage("current failed").
                setStackTrace("current stacktrace").
                setFailureScreenshotFile(screenShot).build();
        Spec.ProtoExecutionResult result = new TestExecutionStage().mergeExecResults(previous, current);

        assertTrue(result.getFailed());
        assertEquals(200, result.getExecutionTime());
        assertEquals("current failed", result.getErrorMessage());
        assertEquals("current stacktrace", result.getStackTrace());
        assertFalse(result.getRecoverableError());
        assertEquals(screenShot, result.getFailureScreenshotFile());
    }

    public void testMergingResultsBothFailing() throws Exception {
        String screenShotPrevious = "2";
        String screenShotCurrent = "hello";

        Spec.ProtoExecutionResult previous = Spec.ProtoExecutionResult.newBuilder().setFailed(true).
                setExecutionTime(1001).
                setRecoverableError(true).
                setErrorMessage("previous failed").
                setStackTrace("previous stacktrace").
                setFailureScreenshotFile(screenShotPrevious).build();
        Spec.ProtoExecutionResult current = Spec.ProtoExecutionResult.newBuilder().setFailed(true).
                setExecutionTime(1002).
                setRecoverableError(false).
                setErrorMessage("current failed").
                setStackTrace("current stacktrace").
                setFailureScreenshotFile(screenShotCurrent).build();
        Spec.ProtoExecutionResult result = new TestExecutionStage().mergeExecResults(previous, current);

        assertTrue(result.getFailed());
        assertEquals(2003, result.getExecutionTime());
        assertEquals("previous failed", result.getErrorMessage());
        assertEquals("previous stacktrace", result.getStackTrace());
        assertFalse(result.getRecoverableError());
        assertEquals(screenShotPrevious, result.getFailureScreenshotFile());
    }

    public void testMergingResultsCurrentFailingAndIsRecoverable() throws Exception {
        String screenShotCurrent = "screenShotCurrent.png";

        Spec.ProtoExecutionResult previous = Spec.ProtoExecutionResult.newBuilder().setFailed(false).setExecutionTime(1001).build();
        Spec.ProtoExecutionResult current = Spec.ProtoExecutionResult.newBuilder().setFailed(true).
                setExecutionTime(1002).
                setRecoverableError(true).
                setErrorMessage("current failed").
                setStackTrace("current stacktrace").
                setFailureScreenshotFile(screenShotCurrent).build();
        Spec.ProtoExecutionResult result = new TestExecutionStage().mergeExecResults(previous, current);

        assertTrue(result.getFailed());
        assertEquals(2003, result.getExecutionTime());
        assertEquals("current failed", result.getErrorMessage());
        assertEquals("current stacktrace", result.getStackTrace());
        assertTrue(result.getRecoverableError());
        assertEquals(screenShotCurrent, result.getFailureScreenshotFile());
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
