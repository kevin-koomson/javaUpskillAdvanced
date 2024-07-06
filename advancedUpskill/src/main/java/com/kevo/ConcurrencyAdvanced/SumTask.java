package com.kevo.ConcurrencyAdvanced;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class SumTask extends RecursiveTask<Long> {
    // variable to hold input array
    private final List<Long> input;

    // constructor
    public SumTask(List<Long> array){
        input = array;
    }

    @Override
    protected Long compute() {
        // If the array length is less than a threshold (e.g., 1000), calculate the sum directly and return it (base case)
        if( input.size() < 1000 ){
            // calculate the sum directly and return it
            System.out.println("Sum returned directly ");
            return input.stream().reduce(0L, Long::sum);
        }

        // Otherwise, split the array into two halves.
        List<Long> subArray1 = input.subList(0,input.size()/2);
        List<Long> subArray2 = input.subList(input.size()/2, input.size()-1);

        // Create two SumTask subtasks, one for each half of the array.
        SumTask task1 = new SumTask(subArray1);
        SumTask task2 = new SumTask(subArray2);

        // Use fork to submit these subtasks.
        task1.fork();
        task2.fork();

        // Use join on each subtask to wait for results (partial sums).
        // Return the sum of the partial sums from both subtasks.
        return task1.join() + task2.join();
    }

    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();

//        Create a large array with sample data.
        List<Long> firstList = generateRandomList(50);
        List<Long> secondList = generateRandomList(2000);

        // Create a SumTask for the entire array.
        SumTask firstTask = new SumTask(firstList);
        SumTask secondTask = new SumTask(secondList);

//        Submit the task to the ForkJoinPool and get the final sum.
        Long firstResult = pool.invoke(firstTask);
        System.out.println("Result of first array sum is " + firstResult);

        Long secondResult = pool.invoke(secondTask);
        System.out.println("Result of second array sum is " + secondResult);
    }

    public static List<Long> generateRandomList(int size){
        List<Long> randomList = new ArrayList<>(size);
        Random random = new Random();

        for(int i = 0; i < size; i++) {
            randomList.add(random.nextLong());
        }
        return randomList;
    }

}
