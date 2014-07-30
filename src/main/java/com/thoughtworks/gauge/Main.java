package com.thoughtworks.gauge;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static final int THREADS = 1000;

    public static void main(String[] args) throws IOException {
        ExecutorService executorService = Executors.newFixedThreadPool(THREADS);
        for (int i = 0; i < THREADS; i++) {
            executorService.execute(getRunnable());
        }
        executorService.shutdown();
        while (!executorService.isTerminated()) {
        }
        System.out.println("Done");
    }

    private static Runnable getRunnable() {
        return new Runnable() {
            @Override
            public void run() {
                GaugeConnection gaugeConnection = new GaugeConnection(9999);
                for (int j = 0; j < 10; j++) {
                    gaugeConnection.getStepValue("this is \"sparta\"");

                }
            }
        };
    }

}
