package Charpter7;
import java.util.Arrays;

public class HoareP {
	
	public static void main(String[] args) {
		int[] A = {12, 9, 10, 2, 6 , 7, 19, 5, 4, 7, 11, 7};
//		int q = HoarePartition(A, 0, A.length-1);
		int[] q = TrisectPartition(A, 0, A.length-1);
		System.out.println(Arrays.toString(q));
		System.out.println(Arrays.toString(A));
		
		
	}
	
	public static int HoarePartition(int[] A, int p, int r) {
		int x = A[p];
		int i = p-1;
		int j = r+1;
		int temp;
		
		while(true) {
			do {
				j--;
			} while(A[j]>x);
			
			do {
				i++;
			} while(A[i]<x);
			
			if(i<j) {
				temp = A[i];
				A[i] = A[j];
				A[j] = temp;
			} else {
				return j;
			}
		}
	}
	
	public static int[] TrisectPartition(int[] A, int p, int r) {
		int x = A[r];
		int i = p-1;
		int t = p-1;
		
		int tmp;
		
		for(int j=p; j<r; j++) {
			if(A[j] <= x) {
				t++;
				tmp = A[j];
				A[j] = A[t];
				A[t] = tmp;
				if(A[t] < x) {
					i++;
					tmp = A[t];
					A[t] = A[i];
					A[i] = tmp;
				}
			}
		}
		
		t++;
		A[r] = A[t];
		A[t] = x;
		
		int[] res = {i, t};
		return res;
	}

}
