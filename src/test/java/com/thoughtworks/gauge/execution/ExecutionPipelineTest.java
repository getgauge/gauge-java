package com.thoughtworks.gauge.execution;

import gauge.messages.Spec;
import junit.framework.TestCase;

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