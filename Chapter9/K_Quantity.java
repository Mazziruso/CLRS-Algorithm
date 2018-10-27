package Charpter9;

import java.util.Arrays;

public class K_Quantity {
	
	public static void main(String[] args) {
		int N = 100;
		int[] A = new int[N];
		for(int i=0; i<N; i++) {
			A[i] = i;
		}
		randomizeInPlace(A);
		
		int k =8;
		System.out.println(Arrays.toString(A));
		int[] result = Seperate.kSplit(A, k);
		System.out.println(Arrays.toString(A));
		System.out.println(Arrays.toString(result));
	}
	
	public static void randomizeInPlace(int[] A) {
		int n = A.length;
		int index;
		int tmp;
		
		for(int i=0; i<n; i++) {
			index = (int)(Math.random()*(n-1-i)) + i;
			tmp = A[i];
			A[i] = A[index];
			A[index] = tmp;
		}
	}

}

class Seperate {
	
	public static int partition(int[] A, int start, int end, int x) {
		int index = 0; //记录主元的下标值
		int tmp;
		int i = start - 1;
		
		for(int j=start; j<=end; j++) {
			if(A[j]<=x) {
				i++;
				tmp = A[j];
				A[j] = A[i];
				A[i] = tmp;
				if(A[i] == x) {
					index = i;
				}
			}
		}
		
		A[index] = A[i];
		A[i] = x;
		
		return i;
	}
	
	public static int randomPartition(int[] A, int start, int end) {
		int index = (int)(Math.random()*(end-start)) + start;
		int x = A[index];
		return partition(A, start, end, x);
	}
	
	public static void insertSort(int[] A, int start, int end) {
		int key;
		int i;
		
		for(int j=start+1; j<=end; j++) {
			i = j-1;
			key = A[j];
			while(i>=start && A[i]>key) {
				A[i+1] = A[i];
				i--;
			}
			A[i+1] = key;
		}
		
	}
	
	
	//最坏情况为线性时间的选择算法
	public static int Select(int[] A, int p, int r, int index) {
		
		//base-case
		if(p>=r) {
			return A[p];
		}
		
		int n = r-p+1;
		int groupNum = (int)Math.ceil(n/5.0);
		int median;
		int start;
		int end;
		
		for(int i=0; i<(n/5); i++) {
			start = 5*i+p;
			end = 5*i+4+p;
			insertSort(A, start, end);
			median = A[start+2];
			//将中位数交换到数组前面
			A[start+2] = A[i+p];
			A[i+p] = median;
		}
		
		if(n%5 != 0) {
			start = 5*(n/5)+p;
			end = r;
			insertSort(A, start, end);
			median = A[(start+end)/2];
			//swap
			A[(start+end)/2] = A[n/5+p];
			A[n/5+p] = median;
		}
		
		//递归找出中位数的中位数
		int x =Select(A, p, p+groupNum-1, (groupNum/2+1));
		
		int q = partition(A, p, r, x);
		int k = q-p+1;
		
		if(index == k) {
			return A[q];
		} else if(index < k) {
			return Select(A, p, q-1, index);
		} else {
			return Select(A, q+1, r, index-k);
		}
		
	}
	
	public static int randomSelect(int[] A, int start, int end, int index) {
		
		//base-case
		if(start>=end) {
			return A[start];
		}
		
		int q = randomPartition(A, start, end);
		
		int k = q-start+1;
		
		//递归分解
		if(k==index) {
			return A[q];
		} else if(index<k) {
			return randomSelect(A, start, q-1, index);
		} else {
			return randomSelect(A, q+1, end, index-k);
		}
	}
	
	//overloading，重载函数
	public static int[] kSplit(int[] A, int k) {
		int[] result = new int[k-1];
		Seperate.kSplit(A, 0, A.length-1, 0, k-1, result);
		return result;
	}
	
	//各参数意义，k分位数，结果以数组形式返回，除去最后一个，共有k-1个分位数
	//（数组， 数组起点下标，数组终点下标，分位数起点下标，分位数终点下标，分位数结果数组）
	public static void kSplit(int[] A, int start, int end, int kp, int kr, int[] result) {
		if(kr>kp) {
			int n = end-start+1;
			int k = kr-kp+1;
			int kIndex = (kr+kp-1)/2;
			
			int split = ((int)Math.ceil((n*1.0)/k)) * (k/2);
//			result[kIndex] = randomSelect(A, start, end, split); //利用随机选择算法
			result[kIndex] = Select(A, start, end, split); //利用最坏情况为线性时间的选择算法，不可用，因为会把数组打乱
			
			//递归分解
			kSplit(A, start, start+split-1, kp, kIndex, result);
			kSplit(A, start+split, end, (kIndex+1), kr, result);
			
		}
	}
}