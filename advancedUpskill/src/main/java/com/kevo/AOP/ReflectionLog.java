package com.kevo.AOP;

public class ReflectionLog {
    @LogExecutionTime
    public static void someTask() throws InterruptedException {
        Thread.sleep(2000);
        System.out.println("Method executed");
    }

    public static void anotherTask() throws InterruptedException {
        Thread.sleep(2000);
        System.out.println("Another task executed");
    }

    public static void main(String[] args) throws InterruptedException {
        someTask();
        anotherTask();
    }
}
