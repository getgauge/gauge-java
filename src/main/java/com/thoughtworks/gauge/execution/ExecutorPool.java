/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge.execution;


import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorPool {
    private Map<String, ExecutorService> executors = new ConcurrentHashMap<>();

    public ExecutorPool(int size) {
        for (int count = 1; count <= size; count++) {
            executors.put(getName(count), Executors.newSingleThreadExecutor());
        }
    }

    private String getName(int count) {
        return "Executor-" + count;
    }

    public void execute(int stream, Runnable task) {
        executors.get(getName(stream)).execute(task);
    }

    public void stopAfterCompletion() {
        for (Map.Entry<String, ExecutorService> entry : executors.entrySet()) {
            entry.getValue().shutdown();
        }
    }
}
