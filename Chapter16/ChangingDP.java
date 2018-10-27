package Charpter16;

import java.util.Arrays;

public class ChangingDP {
    public static void main(String[] args) {

        int n = 50;
//        int[] d = {1, 4, 6, 7};
        int[] d = {1, 5, 10, 25};

        int[] b = new int[n];
        int[] c = new int[n+1];

        ButtomChanging(n, d, b, c);
        System.out.println(Arrays.toString(b));
        System.out.println(Arrays.toString(c));
        printChanging(b, n);

    }

    public static void ButtomChanging(int n, int[] d, int[] b, int[] c) {
        int k = d.length;
        int q;

        for(int i=1; i<=n; i++) {
            q = Integer.MAX_VALUE;
            for(int j=0; j<k; j++) {
                if(d[j]<=i && q>c[i-d[j]]) {
                    q = c[i-d[j]];
                    b[i-1] = d[j];
                }
            }
            c[i] = q+1;
        }
    }

    public static void printChanging(int[] b, int n) {
        System.out.print("<");
        while(n>0) {
            System.out.print(b[n-1]);
            n -= b[n-1];
            if(n>0) {
                System.out.print(", ");
            }
        }
        System.out.println(">");
    }

}
