package com.kevo.MemoryModel;

public class RaceConditionExample {
    public static void main(String[] args) throws InterruptedException {
//        Counter counter = new Counter();
        SyncCounter counter = new SyncCounter();
        int numThreads = 1000;

        // Simulate multiple threads incrementing the counter concurrently
        Thread[] threads = new Thread[numThreads];
        for (int i = 0; i < numThreads; i++) {
            threads[i] = new Thread(counter::increment);
            threads[i].start();
        }

        // Wait for all threads to finish
        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println("Expected count: " + numThreads);
        System.out.println("Actual count: " + counter.count);
    }
}
