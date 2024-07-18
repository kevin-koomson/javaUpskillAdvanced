package com.kevo.ReflectionAnnotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@MyAnnotation(name = "processOrder")
public class OrderProcessor {

    @MyAnnotation(name = "processOrder")
    public void processOrder(String orderId) {
        // Order processing logic
        System.out.println("Processing order: " + orderId);
    }

    public static void main(String[] args) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InvocationTargetException {
        Class<?> orderProcessorClass = OrderProcessor.class;

        Method processOrderMethod = orderProcessorClass.getMethod("processOrder", String.class);

        // Check if the method is annotated with MyAnnotation
        if (processOrderMethod.isAnnotationPresent(MyAnnotation.class)) {
//            if(processOrderMethod.){
//                System.out.println("Hello");
//            }
            MyAnnotation annotation = processOrderMethod.getAnnotation(MyAnnotation.class);
            System.out.println("Method name (from annotation): " + annotation.name());
        }

        // Invoke the method using reflection
        OrderProcessor processor = new OrderProcessor();
        processOrderMethod.invoke(processor, "12345");
    }
}

@Retention(RetentionPolicy.RUNTIME)
@interface MyAnnotation {
    String name();
}



