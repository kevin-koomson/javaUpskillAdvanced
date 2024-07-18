package com.kevo.ReflectionAnnotation;

import java.lang.reflect.Method;
import java.time.Instant;
import java.util.Date;

public class Logger {
    public static void logExecutionTime(Method method) {
        if(method.isAnnotationPresent(LogExecutionTime.class)){
            System.out.println(method.getName() + " run at " + Date.from(Instant.now()));
        }
    }

}
