package Charpter15;

public class MatrixChainDPMain {

    public static void main(String[] args) {
        int[] p = {30, 35, 15, 5, 10, 20, 25};
//        int[] p = {5, 10, 3, 12, 5, 50, 6};
        int n = p.length-1;
        int[][] m = new int[n][n];
        int[][] s = new int[n-1][n-1];

//        matrixChainOrder(p, m, s);
        MemoizedMatrixChain(p, m, s);
        printMatrix(m);
        System.out.println();
        printMatrix(s);
        PrintOptimalParens(s, 1, 6);

    }

    private static void printMatrix(int[][] A) {
        int m = A.length;
        int n;
        for(int i=0; i<m; i++) {
            n = A[i].length;
            for(int j=0; j<n; j++) {
                System.out.printf("%6d", A[i][j]);
            }
            System.out.println();
        }
    }

    private static void MemoizedMatrixChain(int[] p, int[][] m, int[][] s) {
        int n = p.length - 1;

        for(int i=0; i<n; i++) {
            for(int j=i; j<n; j++) {
                m[i][j] = Integer.MAX_VALUE;
            }
        }

        LookUpChain(m, s, p, 1, n);
    }

    private static int LookUpChain(int[][] m, int[][] s, int[] p, int i, int j) {
        if(m[i-1][j-1] < Integer.MAX_VALUE) {
            return m[i-1][j-1];
        }

        int q;

        if(i == j) {
            m[i-1][j-1] = 0;
        } else {
            for(int k=i; k<j; k++) {
               q = LookUpChain(m, s, p, i, k) + LookUpChain(m, s, p, k+1, j) + p[i-1]*p[k]*p[j];
               if(q < m[i-1][j-1]) {
                   m[i-1][j-1] = q;
                   s[i-1][j-2] = k;
               }
            }
        }

        return m[i-1][j-1];
    }

    public static void matrixChainOrder(int[] p, int[][] m, int[][] s) {
        int n = p.length-1;
        int j;
        int q;

        for(int l=2; l<=n; l++) {
            for(int i=1; i<=(n-l+1); i++) {
              j = i+l-1;
              m[i-1][j-1] = Integer.MAX_VALUE;
              for(int k=i; k<=j-1; k++) {
                  q = m[i-1][k-1] + m[k][j-1] + p[i-1] * p[k] *p[j];
                  if(q < m[i-1][j-1]) {
                      m[i-1][j-1] = q;
                      s[i-1][j-2] = k;
                  }
              }
            }
        }
    }

    private static void PrintOptimalParens(int[][] s, int i, int j) {
        if(i == j) {
            System.out.print("A" + i);
        } else {
            System.out.print("(");
            PrintOptimalParens(s, i, s[i-1][j-2]);
            PrintOptimalParens(s, s[i-1][j-2]+1, j);
            System.out.print(")");
        }
    }
}
