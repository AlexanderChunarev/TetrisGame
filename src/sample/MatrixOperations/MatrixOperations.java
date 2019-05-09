package sample.MatrixOperations;

import javafx.scene.shape.Rectangle;

public class MatrixOperations {

    private static int[][] transposeMatrix(int[][] matrix) {
        int[][] temp = new int[matrix[0].length][matrix.length];
        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix[0].length; j++)
                temp[j][i] = matrix[i][j];
        return temp;
    }

    private static int[][] swap(int[][] matrix) {
        int[][] temp = new int[matrix.length][matrix[0].length];
        int index;
        for (int i = 0; i < matrix.length; i++) {
            index = 0;
            for (int j = matrix[0].length - 1; j >= 0; j--) {
                temp[i][index] = matrix[i][j];
                index++;
            }
        }
        return temp;
    }

    public static int[][] rotate(int[][] matrix) {
        return swap(transposeMatrix(matrix));
    }
}