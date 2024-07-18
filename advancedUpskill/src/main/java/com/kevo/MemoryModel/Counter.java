package com.kevo.MemoryModel;

public class Counter {
    int count = 0;

    public void increment() {
        count++; // This can lead to a race condition
    }
}

