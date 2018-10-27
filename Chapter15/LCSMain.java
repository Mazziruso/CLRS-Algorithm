package Charpter15;

import javax.swing.*;

public class LCSMain {
    public static void main(String[] args) {

//        char[] X = {'A', 'B', 'C', 'B', 'D', 'A', 'B'};
//        char[] Y = {'B', 'D', 'C', 'A', 'B', 'A'};
        int[] X = {1, 0, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1};
        int[] Y = {0, 1, 0, 1, 1, 0, 1, 1, 0, 0, 1, 0, 1, 0};
        int m = X.length;
        int n = Y.length;
        int[][] c = new int[m+1][n+1];
        char[][] b = new char[m][n];
        LCSLength(X, Y, c, b);

        printMatrix(c);
        System.out.println();
        PrintLCS(b, X, m, n);
        System.out.println();
//        PrintLCS(c, X, Y, m, n);
        LCSLengthMemoized(X, Y, c, b);
        printMatrix(c);
        System.out.println();
        PrintLCS(b, X, m, n);
        System.out.println();


    }

    private static void printMatrix(int[][] A) {
        int m = A.length;
        int n;
        for(int i=0; i<m; i++) {
            n = A[i].length;
            for(int j=0; j<n; j++) {
                if(A[i][j] == Integer.MIN_VALUE) {
                    System.out.print("  -inf");
                } else {
                    System.out.printf("%6d", A[i][j]);
                }
            }
            System.out.println();
        }
    }

    public static void LCSLength(int [] X, int[] Y, int[][] c, char[][] b) {
        int m = X.length;
        int n = Y.length;

        for(int i=1; i<=m; i++) {
            for(int j=1; j<=n; j++) {
                if(X[i-1] == Y[j-1]) {
                    c[i][j] = c[i-1][j-1] + 1;
                    b[i-1][j-1] = 'S';
                } else if(c[i-1][j] >= c[i][j-1]) {
                    c[i][j] = c[i-1][j];
                    b[i-1][j-1] = 'U';
                } else {
                    c[i][j] = c[i][j-1];
                    b[i-1][j-1] = 'L';
                }
            }
        }
    }

    //自顶至下带备忘
    public static void LCSLengthMemoized(int[] X, int[] Y, int[][] c, char[][] b) {
        int m = X.length;
        int n = Y.length;
        for(int i=0; i<=m; i++) {
            for(int j=0; j<=n; j++) {
                c[i][j] = Integer.MIN_VALUE;
            }
        }

        LookUpLCS(X, Y, c, b, m, n);
    }

    private static int LookUpLCS(int[] X, int[] Y, int[][] c, char[][] b, int i, int j) {
        if(c[i][j]>= 0) {
            return c[i][j];
        }

        if(i==0 || j==0) {
            c[i][j] = 0;
        } else {
            if(X[i-1] == Y[j-1]) {
                c[i][j] = LookUpLCS(X, Y, c, b, i-1, j-1) + 1;
                b[i-1][j-1] = 'S';
            } else {
                c[i][j] = LookUpLCS(X, Y, c, b, i-1, j);
                int q = LookUpLCS(X, Y, c, b, i, j-1);
                if(c[i][j] >= q) {
                    b[i-1][j-1] = 'U';
                } else {
                    c[i][j] = q;
                    b[i-1][j-1] = 'L';
                }
            }
        }

        return c[i][j];
    }

    public static void PrintLCS(char[][] b, int[] X, int i, int j) {
        if(i!=0 && j!=0) {
            if(b[i-1][j-1] == 'S') {
                PrintLCS(b, X, i-1, j-1);
                System.out.print(X[i-1] + ", ");
            } else if(b[i-1][j-1] == 'U') {
                PrintLCS(b, X, i-1, j);
            } else {
                PrintLCS(b, X, i, j-1);
            }
        }
    }

    //不使用表b来重构LCS
    public static void PrintLCS(int[][] c, int[] X, int[] Y, int i, int j) {
        if(i!=0 && j!=0) {
            if(X[i-1] == Y[j-1]) {
                PrintLCS(c, X, Y, i-1, j-1);
                System.out.print(X[i-1] + ", ");
            } else if(c[i-1][j] >= c[i][j-1]) {
                PrintLCS(c, X, Y, i-1, j);
            } else {
                PrintLCS(c, X, Y, i, j-1);
            }
        }
    }
}
