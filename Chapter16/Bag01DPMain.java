package Charpter16;

public class Bag01DPMain {

    public static void printTable(int[][] table) {
        System.out.println();
        for(int i=0; i<table.length; i++) {
            for(int j=0; j<table[i].length; j++) {
                System.out.printf("%-5d", table[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }
    public static void printTable(boolean[][] table) {
        System.out.println();
        for(int i=0; i<table.length; i++) {
            for(int j=0; j<table[i].length; j++) {
                System.out.printf("%-6s", table[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }
    public static void main(String[] args) {

        int[] v = {7, 6, 5, 4, 3, 2, 1};
        int[] w = {1, 5, 7, 8, 9, 16, 27};

        int W = 30;
        int[][] c = new int[8][W+1];
        boolean[][] b = new boolean[8][W+1];
        ButtomBagItems(v, w, W, c, b);

        printTable(c);
        printTable(b);

        printBagItems(v, w, b, w.length, W);


    }

    public static void printBagItems(int[] v, int[] w, boolean[][] b, int i, int j) {
        if(i>0 && j>0) {
            if(b[i-1][j]) {
                printBagItems(v, w, b, i-1, j-w[i-1]);
                System.out.println("itemId: " + i + ", weight: " + w[i-1] + ", value: " + v[i-1]);
            } else {
                printBagItems(v, w, b, i-1, j);
            }
        }
    }

    //let c[0...n][0...W] be new array, let b[0...n-1][0...W] be new array
    public static void ButtomBagItems(int[] v, int[] w, int W, int[][] c, boolean[][] b) {
        int n = v.length;
        int q;

        for(int i=1; i<=n; i++) {
            for(int j=0; j<=W; j++) {
                q = c[i-1][j];
                if(j>=w[i-1] && q<(c[i-1][j-w[i-1]]+v[i-1])) {
                    q = c[i-1][j-w[i-1]]+v[i-1];
                    b[i-1][j] = true;
                } else {
                    b[i-1][j] = false;
                }
                c[i][j] = q;
            }
        }
    }
}
