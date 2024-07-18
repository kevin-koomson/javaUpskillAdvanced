package com.kevo.AOP;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class LoggingAspect {
    @Around("@annotation(logExecutionTime)")
    public Object logMethodCall (ProceedingJoinPoint joinPoint, LogExecutionTime logExecutionTime) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        System.out.println("Method " + methodName + " called at " + System.currentTimeMillis());
        return joinPoint.proceed();
    }
}
