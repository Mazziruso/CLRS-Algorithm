package Charpter9;

import java.util.Arrays;

public class ArraySelect {
	
	public static void main(String[] args) {
		int[] A = {4,2,6,5,7,19,12,3,8,1};
		
//		int q = Select.RandomPartition(A, 0, -1);
//		System.out.println(Arrays.toString(A));
//		System.out.println(q);
		
		System.out.println(Select.RandomSelect(A, 0, A.length-1, 8));
		System.out.println(Arrays.toString(A));
	}

}

class Select {
	public static int Partition(int[] A, int p, int r) {
		int x = A[r];
		int i = p-1;
		int tmp;
		
		for(int j=p; j<r; j++) {
			if(A[j] <= x) {
				i++;
				tmp = A[j];
				A[j] = A[i];
				A[i] = tmp;
			}
		}
		
		i++;
		A[r] = A[i];
		A[i] = x;
		
		return i;
	}
	
	public static int RandomPartition(int[] A, int p, int r) {
		if(p > r) {
			return -1;
		}
		
		int k = (int)(Math.random()*(r-p)) + p;
		int tmp = A[r];
		A[r] = A[k];
		A[k] = tmp;
		
		return Partition(A, p, r);
	}
	
	public static int RandomSelect(int[] A, int p, int r, int i) {
		//base-case
		if(p == r) {
			return A[p];
		}
		
		int q = RandomPartition(A, p, r);
		if(q<p || q>r) {
			return -1;
		}
		
		//base-case
		int k = q-p+1;
		if(k == i) {
			return A[q];
		} else if(i < k) {
			//recursion
			return RandomSelect(A, p, q-1, i);
		} else {
			return RandomSelect(A, q+1, r, i-k);
		}
		
	}
}
