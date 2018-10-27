package Charpter8;

import java.util.Arrays;

public class RadixSortMain {

    public static void main(String[] args) {

        int N = 100000;
        int d = 7;

        int[] A= new int[N];
        for(int i=0; i<N; i++) {
            A[i] = (int)(Math.random() * ((Math.pow(10,d)-1)-(Math.pow(10,d-1)))) + (int)Math.pow(10,d-1);
        }
        System.out.println(Arrays.toString(A));

        try{
            Thread.sleep(2000);
        } catch(Exception e) {
            e.printStackTrace();
        }

        System.out.println("Start sorting...");

        //基数排序
        RSort.radixSort(A, d);

        System.out.println(Arrays.toString(A));
    }
}

class RSort {

    //为基数排序而修改的计数排序
    public static void modifyCountingSort(int[] A, int[][] B, int k) {
        int[] C = new int[k+1];
        int len = B.length;

        for(int i=0; i<len; i++) {
            C[B[i][0]]++;
        }

        for(int i=1; i<=k; i++) {
            C[i] += C[i-1];
        }

        for(int i=len-1; i>=0; i--) {
            A[C[B[i][0]]-1] = B[i][1];
            C[B[i][0]]--;
        }

    }

    //针对十进制的基数排序，其中k=9
    public static void radixSort(int[] A, int d) {
        int len = A.length;
        int[][] B = new int[len][2];
        int pow10 = 1;

        for(int i=1; i<=d; i++) {

            //提取出第i位，并封装成一定格式
            pow10 *= 10;
            for(int j=0; j<len; j++) {
                B[j][0] = (A[j]%pow10)/(pow10/10);
                B[j][1] = A[j];
            }

            //按第i位数字进行排序
            modifyCountingSort(A, B, 9);
        }

    }

}