package Charpter8;

import java.util.Arrays;

public class CountSortMain {
    public static void main(String[] args) {

        int N = 100;
        int[] A = new int[N];
        int[] B = new int[N];
        for(int i=0; i<A.length; i++) {
            A[i] = i+4;
        }
        int k = findMax(A);


        //随机排列A
        randomizedInPlace(A);

        System.out.println(Arrays.toString(A));

        //计数排序A
        CSort.countingSort(A, B, k);
        System.out.println(Arrays.toString(B));

        //计数[a, b]区间内的个数
//        int count = CSort.countingAB(A, 3,5, k);
//        System.out.println(count);

    }

    public static void randomizedInPlace(int[] A) {
        int len = A.length;
        int temp;
        int index;

        for(int i=0; i<len; i++) {
            index = (int)(Math.random()*(len-i-1)) + i;
            temp = A[index];
            A[index] = A[i];
            A[i] = temp;
        }
    }

    public static int findMax(int[] A) {
        int max = Integer.MIN_VALUE;
        for(int i=0; i<A.length; i++) {
            if(max < A[i]) {
                max = A[i];
            }
        }
        return max;
    }
}

class CSort {

    public static void countingSort(int[] A, int[] B, int k) {
        //初始化C，用来计数
        int[] C = new int[k+1];
        int len = A.length;

        // C[i]包括等于i的元素的个数
        for(int i=0; i<len; i++) {
            C[A[i]]++;
        }

        //C[i]包括小于等于i的元素个数，即当前大小为i的元素所处的位置
        for(int i=1; i<=k; i++) {
            C[i] += C[i-1];
        }

        //将元素按C数组中的位置放置在新数组B上，为了满足指针关系，需要减1
        for(int i=len-1; i>=0; i--) {
            B[C[A[i]]-1] = A[i];
            C[A[i]]--;
        }

    }

    public static int countingAB(int[] A, int a, int b, int k) {
        int[] C = new int[k+1];
        int count = 0;

        for(int i=0; i<A.length; i++) {
            C[A[i]]++;
        }

        for(int i=a; i<=b; i++) {
            count += C[i];
        }

        return count;
    }

}
