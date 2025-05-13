/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge.execution;

import gauge.messages.Spec;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AbstractExecutionStageTest {

    @Test
    public void testMergingSimpleResultsBothPassing() throws Exception {
        Spec.ProtoExecutionResult previous = Spec.ProtoExecutionResult.newBuilder().setFailed(false).setExecutionTime(1000).build();
        Spec.ProtoExecutionResult current = Spec.ProtoExecutionResult.newBuilder().setFailed(false).setExecutionTime(1100).build();
        Spec.ProtoExecutionResult result = new TestExecutionStage().mergeExecResults(previous, current);
        assertFalse(result.getFailed());
        assertEquals(2100, result.getExecutionTime());
    }

    @Nested
    public class Failing {

        @Test
        public void testMergingResultsPreviousFailing() throws Exception {
            String screenShot = "1";

            Spec.ProtoExecutionResult previous = Spec.ProtoExecutionResult.newBuilder().setFailed(true)
                    .setExecutionTime(100)
                    .setRecoverableError(false)
                    .setErrorMessage("Previous failed")
                    .setStackTrace("Previous stacktrace")
                    .setFailureScreenshotFile(screenShot).build();
            Spec.ProtoExecutionResult current = Spec.ProtoExecutionResult.newBuilder().setFailed(false).setExecutionTime(1100).build();
            Spec.ProtoExecutionResult result = new TestExecutionStage().mergeExecResults(previous, current);

            assertTrue(result.getFailed());
            assertEquals(1200, result.getExecutionTime());
            assertEquals("Previous failed", result.getErrorMessage());
            assertEquals("Previous stacktrace", result.getStackTrace());
            assertFalse(result.getRecoverableError());
            assertEquals(screenShot, result.getFailureScreenshotFile());
        }

        @Test
        public void testMergingResultsCurrentFailing() throws Exception {
            String screenShot = "2";

            Spec.ProtoExecutionResult previous = Spec.ProtoExecutionResult.newBuilder().setFailed(false).setExecutionTime(100).build();
            Spec.ProtoExecutionResult current = Spec.ProtoExecutionResult.newBuilder().setFailed(true)
                    .setExecutionTime(100)
                    .setRecoverableError(false)
                    .setErrorMessage("current failed")
                    .setStackTrace("current stacktrace")
                    .setFailureScreenshotFile(screenShot).build();
            Spec.ProtoExecutionResult result = new TestExecutionStage().mergeExecResults(previous, current);

            assertTrue(result.getFailed());
            assertEquals(200, result.getExecutionTime());
            assertEquals("current failed", result.getErrorMessage());
            assertEquals("current stacktrace", result.getStackTrace());
            assertFalse(result.getRecoverableError());
            assertEquals(screenShot, result.getFailureScreenshotFile());
        }

        @Test
        public void testMergingResultsBothFailing() throws Exception {
            String screenShotPrevious = "2";
            String screenShotCurrent = "hello";

            Spec.ProtoExecutionResult previous = Spec.ProtoExecutionResult.newBuilder().setFailed(true)
                    .setExecutionTime(1001)
                    .setRecoverableError(true)
                    .setErrorMessage("previous failed")
                    .setStackTrace("previous stacktrace")
                    .setFailureScreenshotFile(screenShotPrevious).build();
            Spec.ProtoExecutionResult current = Spec.ProtoExecutionResult.newBuilder().setFailed(true)
                    .setExecutionTime(1002)
                    .setRecoverableError(false)
                    .setErrorMessage("current failed")
                    .setStackTrace("current stacktrace")
                    .setFailureScreenshotFile(screenShotCurrent).build();
            Spec.ProtoExecutionResult result = new TestExecutionStage().mergeExecResults(previous, current);

            assertTrue(result.getFailed());
            assertEquals(2003, result.getExecutionTime());
            assertEquals("previous failed", result.getErrorMessage());
            assertEquals("previous stacktrace", result.getStackTrace());
            assertFalse(result.getRecoverableError());
            assertEquals(screenShotPrevious, result.getFailureScreenshotFile());
        }

        @Test
        public void testMergingResultsCurrentFailingAndIsRecoverable() throws Exception {
            String screenShotCurrent = "screenShotCurrent.png";

            Spec.ProtoExecutionResult previous = Spec.ProtoExecutionResult.newBuilder().setFailed(false).setExecutionTime(1001).build();
            Spec.ProtoExecutionResult current = Spec.ProtoExecutionResult.newBuilder().setFailed(true)
                    .setExecutionTime(1002)
                    .setRecoverableError(true)
                    .setErrorMessage("current failed")
                    .setStackTrace("current stacktrace")
                    .setFailureScreenshotFile(screenShotCurrent).build();
            Spec.ProtoExecutionResult result = new TestExecutionStage().mergeExecResults(previous, current);

            assertTrue(result.getFailed());
            assertEquals(2003, result.getExecutionTime());
            assertEquals("current failed", result.getErrorMessage());
            assertEquals("current stacktrace", result.getStackTrace());
            assertTrue(result.getRecoverableError());
            assertEquals(screenShotCurrent, result.getFailureScreenshotFile());
        }
    }

    @Nested
    public class Skipped {

        @Test
        public void testMergingResultsPreviousSkipped() throws Exception {
            Spec.ProtoExecutionResult previous = Spec.ProtoExecutionResult.newBuilder().setSkipScenario(true)
                    .setExecutionTime(100)
                    .build();
            Spec.ProtoExecutionResult current = Spec.ProtoExecutionResult.newBuilder().setSkipScenario(false).setExecutionTime(1100).build();
            Spec.ProtoExecutionResult result = new TestExecutionStage().mergeExecResults(previous, current);

            assertTrue(result.getSkipScenario());
            assertEquals(1200, result.getExecutionTime());
        }

        @Test
        public void testMergingResultsCurrentSkipped() throws Exception {
            Spec.ProtoExecutionResult previous = Spec.ProtoExecutionResult.newBuilder().setSkipScenario(false).setExecutionTime(100).build();
            Spec.ProtoExecutionResult current = Spec.ProtoExecutionResult.newBuilder().setSkipScenario(true)
                    .setExecutionTime(100)
                    .build();
            Spec.ProtoExecutionResult result = new TestExecutionStage().mergeExecResults(previous, current);

            assertTrue(result.getSkipScenario());
            assertEquals(200, result.getExecutionTime());
        }

        @Test
        public void testMergingResultsBothSkipped() throws Exception {
            Spec.ProtoExecutionResult previous = Spec.ProtoExecutionResult.newBuilder().setSkipScenario(true)
                    .setExecutionTime(1001)
                    .build();
            Spec.ProtoExecutionResult current = Spec.ProtoExecutionResult.newBuilder().setSkipScenario(true)
                    .setExecutionTime(1002)
                    .build();
            Spec.ProtoExecutionResult result = new TestExecutionStage().mergeExecResults(previous, current);

            assertTrue(result.getSkipScenario());
            assertEquals(2003, result.getExecutionTime());
        }

    }

    private static class TestExecutionStage extends AbstractExecutionStage {
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
