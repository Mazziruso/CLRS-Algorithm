package Charpter8;

import java.util.Arrays;

public class LinearInPlace {

    public static void main(String[] args) {
//        int[] A = new int[10];
//        for(int i=0; i<10; i++) {
//            A[i] = i;
//        }
//
//        randomizedInPlaced(A);
        int[] A = {3,2,4,6,5};

        System.out.println(Arrays.toString(A));

        Linear_in_Place(A, 7);

        System.out.println(Arrays.toString(A));

    }

    public static void randomizedInPlaced(int[] A) {
        int len = A.length;
        int tmp;
        int index;
        for(int i=0; i<len; i++) {
            index = (int)(Math.random() * (len-1-i)) + i;
            tmp = A[i];
            A[i] = A[index];
            A[index] = tmp;
        }
    }

    public static void Linear_in_Place(int[] A, int k){
        int[] C = new int[k];
        int[] P = new int[k];
        for (int i = 0; i < A.length; i++){
            C[A[i]] = C[A[i]] + 1;
        }
        for (int i = 1; i < C.length; i++){
            C[i] = C[i] + C[i - 1];
        }
        System.arraycopy(C, 0, P, 0, k);
        int j = 0;
        while (j < A.length){
            boolean placed;
            if (A[j] == 0){
                placed = j < P[A[j]];
            }else{
                placed = P[A[j] - 1] <= j && P[A[j]] > j;
            }
            if (placed){
                j++;
            }else{
                int c = (--C[A[j]]);
                int temp = A[j];
                A[j] = A[c];
                A[c] = temp;
            }
        }
    }
}
