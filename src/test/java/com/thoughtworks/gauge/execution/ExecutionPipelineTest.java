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
import org.mockito.InOrder;
import org.mockito.Mockito;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;

public class ExecutionPipelineTest extends TestCase {

    public void testOrderOfPipelineStages() throws Exception {
        TestExecutionStage first = createStage();
        TestExecutionStage second = createStage();
        TestExecutionStage third = createStage();
        TestExecutionStage fourth = createStage();

        ExecutionPipeline pipeline = new ExecutionPipeline(first);
        pipeline.addStages(second, third, fourth);

        assertEquals(first.next(), second);
        assertEquals(second.next(), third);
        assertEquals(third.next(), fourth);
        assertEquals(fourth.next(), null);
    }

    public void testOrderOfPipelineStagesExecution() throws Exception {
        TestExecutionStage first = mock(TestExecutionStage.class);
        TestExecutionStage second = mock(TestExecutionStage.class);
        TestExecutionStage third = mock(TestExecutionStage.class);
        TestExecutionStage fourth = mock(TestExecutionStage.class);

        Mockito.doCallRealMethod().when(first).setNextStage(any(ExecutionStage.class));
        Mockito.doCallRealMethod().when(first).execute(any(Spec.ProtoExecutionResult.class));
        Mockito.doCallRealMethod().when(first).executeNext(any(Spec.ProtoExecutionResult.class));
        Mockito.doCallRealMethod().when(first).next();

        Mockito.doCallRealMethod().when(second).setNextStage(any(ExecutionStage.class));
        Mockito.doCallRealMethod().when(second).execute(any(Spec.ProtoExecutionResult.class));
        Mockito.doCallRealMethod().when(second).executeNext(any(Spec.ProtoExecutionResult.class));
        Mockito.doCallRealMethod().when(second).next();

        Mockito.doCallRealMethod().when(third).setNextStage(any(ExecutionStage.class));
        Mockito.doCallRealMethod().when(third).execute(any(Spec.ProtoExecutionResult.class));
        Mockito.doCallRealMethod().when(third).executeNext(any(Spec.ProtoExecutionResult.class));
        Mockito.doCallRealMethod().when(third).next();

        Mockito.doCallRealMethod().when(fourth).setNextStage(any(ExecutionStage.class));
        Mockito.doCallRealMethod().when(fourth).execute(any(Spec.ProtoExecutionResult.class));
        Mockito.doCallRealMethod().when(fourth).executeNext(any(Spec.ProtoExecutionResult.class));
        Mockito.doCallRealMethod().when(fourth).next();

        InOrder inOrder = Mockito.inOrder(first, second, third, fourth);

        ExecutionPipeline pipeline = new ExecutionPipeline(first);
        pipeline.addStages(second, third, fourth);
        pipeline.start();


        inOrder.verify(first).execute(any(Spec.ProtoExecutionResult.class));
        inOrder.verify(second).execute(any(Spec.ProtoExecutionResult.class));
        inOrder.verify(third).execute(any(Spec.ProtoExecutionResult.class));
        inOrder.verify(fourth).execute(any(Spec.ProtoExecutionResult.class));
    }

    private TestExecutionStage createStage() {
        return new TestExecutionStage();
    }

    private class TestExecutionStage extends AbstractExecutionStage {
        public ExecutionStage next;

        public void setNextStage(ExecutionStage stage) {
            this.next = stage;
        }

        public Spec.ProtoExecutionResult execute(Spec.ProtoExecutionResult result) {
            return executeNext(result);
        }

        protected ExecutionStage next() {
            return this.next;
        }
    }

}
