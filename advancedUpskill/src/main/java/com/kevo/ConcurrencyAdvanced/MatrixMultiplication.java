package com.kevo.ConcurrencyAdvanced;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.RecursiveTask;

public class MatrixMultiplication extends RecursiveTask<List<List<Integer>>> {
    private final List<List<Integer>> matrixA;
    private final List<List<Integer>> matrixB;

    public MatrixMultiplication(List<List<Integer>> matrixA, List<List<Integer>> matrixB) {
        this.matrixA = matrixA;
        this.matrixB = matrixB;
    }

    @Override
    protected List<List<Integer>> compute() {
//      obtain dimensions (order) of given matrices to multiply
        List<Integer> matrixAOrder = getMatrixOrder(matrixA);
        List<Integer> matrixBOrder = getMatrixOrder(matrixB);

//        if sizes are incompatible, throw an error for bad input: incompatible matrix multiplication
        if(!Objects.equals(matrixAOrder.get(1), matrixBOrder.get(0))){
            throw new IllegalArgumentException("Matrices orders are not compatible");
        }

        return multiplyMatrices(matrixA, matrixB);

    }

    private int multiply(List<Integer> a, List<Integer> b) {
        int product = 0;
        for( int i = 0; i < a.size(); i++ ) {
            product += a.get(i) * b.get(i);
        }
        return product;
    }
    public static List<List<Integer>> getColumnValues(List<List<Integer>> matrix) {
        // Check if the matrix is empty
        if (matrix.isEmpty()) {
            return new ArrayList<>();
        }

        int numRows = matrix.size();
        int numCols = matrix.get(0).size();
        List<List<Integer>> columns = new ArrayList<>(numCols);

        // Initialize columns lists
        for (int i = 0; i < numCols; i++) {
            columns.add(new ArrayList<>(numRows));
        }

        // Fill columns with values from the matrix
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                columns.get(j).add(matrix.get(i).get(j));
            }
        }

        return columns;
    }

    private List<List<Integer>> multiplyMatrices(List<List<Integer>> matrixA, List<List<Integer>> matrixB) {
        // Get orders of matrices
        List<Integer> matrixAOrder = getMatrixOrder(matrixA);
        List<Integer> matrixBOrder = getMatrixOrder(matrixB);
        List<Integer> resultOrder = List.of(matrixAOrder.get(0), matrixBOrder.get(1));

        // initialize resulting product matrix
        List<List<Integer>> result = new ArrayList<>();

        // multiply matrix
        List<List<Integer>> transposedB = getColumnValues(matrixB);
        for(List<Integer> row : matrixA){
            List<Integer> resultRow = new ArrayList<>();
            for(List<Integer> col : transposedB){
                resultRow.add(multiply(row, col));
            }
            result.add(resultRow);
        }
        printMatrix(result);
        return result;
    }

    private List<Integer> getMatrixOrder(List<List<Integer>> matrix) {
        int columnSize = matrix.size();
        int rowSize = matrix.get(0).size();

        for( List<Integer> a : matrix ) {
            if( a.size() != rowSize ) {
                throw new IllegalArgumentException("Matrix does not have the same number of elements");
            }
        }
        return List.of(columnSize, rowSize);
    }
    private void printMatrix(List<List<Integer>> matrix) {
        System.out.println("[");
        for( List<Integer> a : matrix ) {
            System.out.println(a);
        }
        System.out.println("]");
    }

    public static void main(String[] args) {
        List<List<Integer>> matrixA = List.of(
                List.of(3, 4),
                List.of(7, 2),
                List.of(5, 9)
        );
        List<List<Integer>> matrixB = List.of(
                List.of(3, 1, 5),
                List.of(6, 9, 7)
        );

        MatrixMultiplication matrix = new MatrixMultiplication(matrixA, matrixB);
        matrix.compute();

        List<List<Integer>> matrixC = List.of(
                List.of(3, 1, 4)
        );
        List<List<Integer>> matrixD = List.of(
                List.of(4, 3),
                List.of(2, 5),
                List.of(6, 8)
        );

        MatrixMultiplication matrix2 = new MatrixMultiplication(matrixC, matrixD);
        matrix2.compute();

    }
    /*
    * obtain dimensions (order) of given matrices to multiply
    * if sizes are incompatible, throw an error for bad input: incompatible matrix multiplication
    * determine dimensions of resulting product matrix, create and initialize with zero
    * take first row of a, multiply with first column of b, place in resulting matrix,
    * repeat previous action
    * */
}
