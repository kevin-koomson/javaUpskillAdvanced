package com.kevo.ReflectionAnnotation;

import java.lang.reflect.Method;
import java.util.Arrays;

import static com.kevo.ReflectionAnnotation.MethodInjector.createProxy;

public class ExecutionTimeLogger {
    @LogExecutionTime
    public void someMethod() throws InterruptedException {
        Thread.sleep(2000);
        System.out.println("some Method executed");
    }

    public void anotherMethod() throws InterruptedException {
        Thread.sleep(2000);
        System.out.println("another Method executed");
    }

    public static void main(String[] args) throws InterruptedException, ClassNotFoundException {
        Class<ExecutionTimeLogger> executionTimeLoggerClass = ExecutionTimeLogger.class;
        Method[] methods = executionTimeLoggerClass.getDeclaredMethods();
        for(Method method : methods){
            if(method.isAnnotationPresent(LogExecutionTime.class)){
                method.getDeclaringClass().getClassLoader().loadClass("com.kevo.ReflectionAnnotation.Logger");
            }
        }
    }
}

