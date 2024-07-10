package com.kevo.ConcurrencyAdvanced;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class MatrixMultiplicationTask extends RecursiveTask<int[][]> {
    private final int[][] A;
    private final int[][] B;
    private final int[][] C;

    private final int rowA;
    private final int colA;
    private final int rowB;
    private final int colB;
    private final int size;

    public MatrixMultiplicationTask(int[][] A, int[][] B, int[][] C, int rowA, int colA, int rowB, int colB, int size) {
        this.A = A;
        this.B = B;
        this.C = C;
        this.rowA = rowA;
        this.colA = colA;
        this.rowB = rowB;
        this.colB = colB;
        this.size = size;
    }

    @Override
    protected int[][] compute() {
        if (size <= 64) {  // Base case: perform multiplication directly if the size is small
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    C[rowA + i][colB + j] = 0;
                    for (int k = 0; k < size; k++) {
                        C[rowA + i][colB + j] += A[rowA + i][colA + k] * B[rowB + k][colB + j];
                    }
                }
            }
            return C;
        }

        int newSize = size / 2;

        // Create sub-tasks
        MatrixMultiplicationTask[] tasks = new MatrixMultiplicationTask[8];
        tasks[0] = new MatrixMultiplicationTask(A, B, C, rowA, colA, rowB, colB, newSize);
        tasks[1] = new MatrixMultiplicationTask(A, B, C, rowA, colA, rowB, colB + newSize, newSize);
        tasks[2] = new MatrixMultiplicationTask(A, B, C, rowA, colA + newSize, rowB, colB, newSize);
        tasks[3] = new MatrixMultiplicationTask(A, B, C, rowA, colA + newSize, rowB, colB + newSize, newSize);
        tasks[4] = new MatrixMultiplicationTask(A, B, C, rowA + newSize, colA, rowB, colB, newSize);
        tasks[5] = new MatrixMultiplicationTask(A, B, C, rowA + newSize, colA, rowB, colB + newSize, newSize);
        tasks[6] = new MatrixMultiplicationTask(A, B, C, rowA + newSize, colA + newSize, rowB, colB, newSize);
        tasks[7] = new MatrixMultiplicationTask(A, B, C, rowA + newSize, colA + newSize, rowB, colB + newSize, newSize);

        // Fork tasks
        for (int i = 1; i < tasks.length; i++) {
            tasks[i].fork();
        }

        // Compute the first task and wait for the others
        tasks[0].compute();
        for (int i = 1; i < tasks.length; i++) {
            tasks[i].join();
        }

        return C;
    }

    public static void main(String[] args) {
        int n = 1024;  // Matrix size (must be a power of 2 for simplicity)
        int[][] A = new int[n][n];
        int[][] B = new int[n][n];
        int[][] C = new int[n][n];

        // Initialize matrices A and B with random values
        initializeMatrix(A);
        initializeMatrix(B);

        // Create the ForkJoinPool
        ForkJoinPool pool = new ForkJoinPool();

        // Create the main task
        MatrixMultiplicationTask task = new MatrixMultiplicationTask(A, B, C, 0, 0, 0, 0, n);

        // Execute the task
        pool.invoke(task);

        // Print the result matrix
        printMatrix(C);
    }

    private static void initializeMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                matrix[i][j] = (int) (Math.random() * 10);
            }
        }
    }

    private static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int element : row) {
                System.out.print(element + " ");
            }
            System.out.println();
        }
    }
}
