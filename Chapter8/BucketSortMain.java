package Charpter8;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.Collections;

public class BucketSortMain {

    public static void main(String[] args) {

        int N = 100;
        double[] A = new double[N];
        int i = 0;
        while(i<N) {
            A[i] = Math.random();
            if(A[i] < 1.0) {
                i++;
            }
        }

        System.out.println(Arrays.toString(A));

        BSort.bucketSort(A);
        System.out.println(Arrays.toString(A));

    }
}

class BSort {

    //基本桶排序
    public static void bucketSort(double[] A) {
        int len = A.length;
        LinkedList<Double>[] B = new LinkedList[len];

        for(int i=0; i<len; i++) {
            B[i] = new LinkedList();
        }

        for(int i=0; i<len; i++) {
            B[(int)(len*A[i])].add(A[i]);
        }

        int k = 0;
        for(int j=0; j<11; j++) {
            Collections.sort(B[j]);
            Iterator<Double> iter = B[j].iterator();
            while(iter.hasNext()) {
                A[k] = iter.next();
                k++;
            }
        }
    }

}

