package com.kevo.MemoryModel;

public class SyncCounter {
    public int count = 0;
    private final Object lock = new Object();

    public synchronized void increment(){
        count++;
    }
}
