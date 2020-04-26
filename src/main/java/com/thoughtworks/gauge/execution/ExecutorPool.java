/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge.execution;


import java.util.HashMap;
import java.util.Map;

public class ExecutorPool {
    private Map<String, TaskExecutor> executors = new HashMap<>();

    public ExecutorPool(int size) {
        for (int count = 1; count <= size; count++) {
            String threadName = getName(count);
            TaskExecutor executor = new TaskExecutor(threadName);
            executors.put(threadName, executor);
            executor.start();
        }
    }

    private String getName(int count) {
        return "Executor-" + count;
    }

    public void execute(int stream, Runnable task) throws Exception {
        executors.get(getName(stream)).submitTask(task);
    }

    public void stopAfterCompletion() throws InterruptedException {
        for (Map.Entry<String, TaskExecutor> entry : executors.entrySet()) {
            TaskExecutor value = entry.getValue();
            value.stopThread();
            value.join();
        }
    }
}
