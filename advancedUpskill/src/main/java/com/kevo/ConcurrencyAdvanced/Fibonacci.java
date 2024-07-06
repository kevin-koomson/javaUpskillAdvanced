package com.kevo.ConcurrencyAdvanced;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class Fibonacci extends RecursiveTask<Integer> {
    private final int input;
    public Fibonacci(int input) {
        this.input = input;
    }

    @Override
    protected Integer compute() {
        if (input<=1){
            return input;
        }
        Fibonacci task1 = new Fibonacci(input - 1);
        task1.fork();
        Fibonacci task2 = new Fibonacci(input - 2);
        task2.fork();

        return task1.join() + task2.join();
    }

    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        int number = 4;
        Fibonacci task = new Fibonacci(number);

        int result = pool.invoke(task);

        System.out.println("Fibonacci of " + number + " is: " + result);
    }
}
