package Charpter15;

import javafx.beans.property.IntegerProperty;

import java.lang.reflect.Array;
import java.util.*;

public class DPMain {

    public static void main(String[] args) {

        int[] p = {1, 5, 8, 9, 10, 17, 17, 20, 24, 30};

        for(int n=0; n<=10; n++) {
            System.out.println(n + " : " + MemoizedCutRod(p, n) + ", " + BottomUpCutRod(p, n));
        }

        int n = 10;
        int[] r = new int[n+1];
        int[] s = new int[n+1];
        System.out.println();
        System.out.println();
        ExtendedBottomUpCutRod(p, n, s, r);
        System.out.println("r : " + Arrays.toString(r));
        System.out.println("s : " + Arrays.toString(s));

        System.out.println();
        System.out.println();
        ExtendedMemoizedCutRod(p, n, s, r);
        System.out.println("r : " + Arrays.toString(r));
        System.out.println("s : " + Arrays.toString(s));

        System.out.println();
        ArrayList<Integer> l = solutionCut(s);
        Iterator iter = l.iterator();
        while(iter.hasNext()) {
            System.out.println(iter.next());
        }


    }

    private static ArrayList<Integer> solutionCut(int[] s) {
        ArrayList<Integer> l = new ArrayList<>();
        int n = s.length-1;

        while(n>0) {
            l.add(s[n]);
            n -= s[n];
        }

        return l;
    }

    public static int MemoizedCutRod(int[] p, int n) {
        int[] r = new int[n+1];
        for(int i=0; i<n+1; i++) {
            r[i] = Integer.MIN_VALUE;
        }

        return MemoizedCutRodAux(p, n, r);
    }

    private static int MemoizedCutRodAux(int[] p, int n, int[] r) {
        if(r[n] >= 0) {
            return r[n];
        }
        int q;
        int temp;

        if(n==0) {
            q = 0;
        } else {
            q = Integer.MIN_VALUE;
            for(int i=0; i<n; i++) {
                temp = MemoizedCutRodAux(p, n-i-1, r) + p[i];
                q = (q>temp) ? q : temp;
            }
        }
        r[n] = q;

        return q;
    }

    public static int ExtendedMemoizedCutRod(int[] p, int n, int[] s, int[] r) {
        for(int i=0; i<n+1; i++) {
            r[i] = Integer.MIN_VALUE;
        }

        return ExtendedMemoizedCutRodAux(p, n, s, r);
    }

    private static int ExtendedMemoizedCutRodAux(int[] p, int n, int[] s, int[] r) {
        if(r[n] >= 0) {
            return r[n];
        }
        int q;
        int temp;

        if(n==0) {
            q = 0;
        } else {
            q = Integer.MIN_VALUE;
            for(int i=0; i<n; i++) {
                temp = MemoizedCutRodAux(p, n-i-1, r) + p[i];
                if(q < temp) {
                    q = temp;
                    s[n] = i+1;
                }
            }
        }
        r[n] = q;

        return q;
    }

    public static int BottomUpCutRod(int[] p, int n) {
        int[] r = new int[n+1];
        r[0] = 0;
        int q;
        int temp;

        for(int j=1; j<=n; j++) {
            q = Integer.MIN_VALUE;
            for(int i=0; i<j; i++) {
                temp = p[i] + r[j-i-1];
                q = (q>temp) ? q : temp;
            }
            r[j] = q;
        }

        return r[n];
    }

    public static void ExtendedBottomUpCutRod(int[] p, int n, int[] s , int[] r) {
        r[0] = 0;
        int q;
        int temp;

        for(int j=1; j<=n; j++) {
            q = Integer.MIN_VALUE;
            for(int i=0; i<j; i++) {
                temp = p[i] + r[j-i-1];
                if(q < temp) {
                    q = temp;
                    s[j] = i+1;
                }
            }
            r[j] = q;
        }
    }
}

