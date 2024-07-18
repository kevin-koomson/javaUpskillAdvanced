package com.kevo.ReflectionAnnotation;

public class MyServiceImpl implements MyService{

    @Override
    @LogExecutionTime
    public void someMethod() throws InterruptedException {
        Thread.sleep(2000);
        System.out.println("some Method executed");
    }

    @Override
    public void anotherMethod() throws InterruptedException {
        Thread.sleep(2000);
        System.out.println("another Method executed");
    }
}
