package Charpter7;

import java.util.Arrays;

public class QuickInsertSort {
	
	public static void main(String[] args) {
		
		int[] A = new int[1000000];
		
		for(int k=0; k<A.length; k++) {
			A[k] = (int)(Math.random() * 100);
		}
		
		System.out.println(Arrays.toString(A));
		
//		AdvanceSort(A);
		InsertSort(A);
		
		System.out.println(Arrays.toString(A));
		
	}
	
	public static int Partition(int[] A, int p, int r) {
		
		int temp;
		int x = A[r];
		int i = p-1;
		
		for(int j=p; j<r; j++) {
			if(A[j] <= x) {
				i++;
				temp = A[j];
				A[j] = A[i];
				A[i] = temp;
			}
		}
		
		i++;
		A[r] = A[i];
		A[i] = x;
		
		return i;
	}
	
	public static void QuickSort(int[] A, int p, int r, int k) {
		//base-case
		//当p>=r或r-p<k时
		
		if(p<r & (r-p)>=k) {
			int q = Partition(A, p, r);
			
			QuickSort(A, p, q-1, k);
			QuickSort(A, q+1, r, k);
		}
	}
	
	public static void InsertSort(int[] A) {
		int len = A.length;
		int key;
		int i;
		
		for(int j=1; j<len; j++) {
			key = A[j];
			
			i = j-1;
			while(i>=0 && key<A[i]) {
				A[i+1] = A[i];
				i--;
			}
			
			A[i+1] = key;
		}
	}
	
	public static void AdvanceSort(int[] A) {
		
		int len = A.length;
		int k = (int)Math.sqrt(len);
		QuickSort(A, 0, len-1, k);
		InsertSort(A);
	}

}
