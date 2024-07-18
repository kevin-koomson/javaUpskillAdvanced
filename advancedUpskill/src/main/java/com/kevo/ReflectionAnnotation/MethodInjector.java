package com.kevo.ReflectionAnnotation;

import java.lang.reflect.Proxy;

public class MethodInjector {
    public static Object createProxy (Object target) {
        return Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                (proxy, method, args) -> {
                    if(method.isAnnotationPresent(LogExecutionTime.class)){
                        Logger.logExecutionTime(method);
                    }
                    return null;
                }
        );
    }
}
