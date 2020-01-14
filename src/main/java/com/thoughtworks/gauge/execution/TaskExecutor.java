package com.thoughtworks.gauge.execution;

import java.util.concurrent.CopyOnWriteArrayList;

public class TaskExecutor extends Thread {
    private CopyOnWriteArrayList<Runnable> queue;
    private boolean done;

    TaskExecutor(String name) {
        super(name);
        this.queue = new CopyOnWriteArrayList<>();
        this.done = false;
    }

    @Override
    public void run() {
        while (!done) {
            if (!queue.isEmpty()) {
                Runnable task = queue.get(0);
                queue.remove(0);
                if (task != null) {
                    task.run();
                }
            }
        }
    }

    void stopThread() {
        this.done = true;
    }

    void submitTask(Runnable task) {
        queue.add(task);
    }
}
