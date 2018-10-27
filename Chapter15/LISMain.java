package Charpter15;
import java.util.Arrays;

public class LISMain {
    public static void main(String[] args) {
        int[] A = {4,5,4,5,7,8,1,2,3,4,5,9,13,6};

        int n = A.length;
        int[] c = new int[n+1];
        int[] c1 = new int[n+2];
        int[] b = new int[n];
        int[] b1 = new int[n+1];

        LISLengthButtom(A, c, b);
        System.out.println(Arrays.toString(c));
        System.out.println(Arrays.toString(b));

        MemoizedLISLength(A, c1, b1);
        System.out.println(Arrays.toString(c1));
        System.out.println(Arrays.toString(b1));

        System.out.println();
        PrintLIS(A, c1, b1, n);
        System.out.println();

        int[] c2 = new int[n];
        int[] b2 = new int[n+1];
        int LISLen = ButtomLISLengthAda(A, c2, b2);
        System.out.println(Arrays.toString(c2));
        System.out.println(Arrays.toString(b2));
        PrintLISAda(A, c2, b2, LISLen);


    }


    //c.length=n+1(0->n), b.length=n
    public static void LISLengthButtom(int[] A, int[] c, int[] b) {
        int n = A.length;
        int q;

        for(int i=1; i<=n; i++) {
            q = 0;
            for(int k=1; k<i; k++) {
                if(A[k-1] <= A[i-1]) {
                    if(q <= c[k]) {
                        q = c[k];
                        b[i-1] = k;
                    }
                }
            }
            c[i] = q+1;
        }
    }

    public static void MemoizedLISLength(int[] A, int[] c, int[] b) {
        int n = A.length;
        int[] A1 = new int[n+1];
        for(int i=0; i<=n+1; i++) {
            c[i] = -1;
        }
        for(int i=0; i<n; i++) {
            A1[i] = A[i];
        }
        A1[n] = Integer.MAX_VALUE;

        LookUpLIS(A1, c, b, n+1);
    }

    public static int LookUpLIS(int[] A, int[] c, int[] b, int i) {
        if(c[i] >= 0) {
            return c[i];
        }

        int q = 0;
        int temp;

        if(i > 0) {
            for(int k=1; k<i; k++) {
                if(A[i-1] >= A[k-1]) {
                    temp = LookUpLIS(A, c, b, k);
                    if(q <= temp) {
                        q = c[k];
                        b[i-1] = k;
                    }
                }
            }
        }
        c[i] = q+1;
        return c[i];
    }

    public static void PrintLIS(int[] A, int[] c, int[] b, int i) {
        int index = i;
        if(i == A.length) {
            int q = 0;
            for(int j=0; j<=i; j++) {
                if(q <= c[j]) {
                    q = c[j];
                    index = j;
                }
            }
        }
        if(index != 0) {
            PrintLIS(A, c, b, b[index-1]);
            System.out.print(A[index-1] + ", ");
        }
    }

    public static int ButtomLISLengthAda(int[] A, int[] c, int[] b) {
        int n = A.length;

        for(int i=0; i<n; i++) {
            c[i] = Integer.MAX_VALUE;
            b[i] = -1;
        }
        b[n] = -1;

        int indexLen = 0;
        int indexSub;

        for(int i=0; i<n; i++) {
            indexSub = BSearchInterval(c, A[i], 0, indexLen);
            c[indexSub] = A[i];
            if(indexSub >= 1)
                b[indexSub] = c[indexSub-1];
            if(indexSub>indexLen) {
                indexLen = indexSub;
            }
        }

        return indexLen+1;
    }

    private static int BSearchInterval(int[] A, int key, int start, int end) {
        if(start>end) {
            return -1;
        } else if(key < A[0]) {
            return 0;
        } else if(key >= A[end]) {
            return end+1;
        }

        int index = (start+end)/2;

        if(key>=A[index] && key<A[index+1]) {
            return index+1;
        } else if(key < A[index]) {
            return BSearchInterval(A, key, start, index);
        } else {
            return BSearchInterval(A, key, index+1, end);
        }

    }

    public static void PrintLISAda(int[] A, int[] c, int[] b, int indexLen) {
        int i = 1;
        System.out.print("<");
        while(i<indexLen) {
            System.out.print(b[i] + ", ");
            i++;
        }
        System.out.print(c[indexLen-1] + ">");
        System.out.println();
    }
}
