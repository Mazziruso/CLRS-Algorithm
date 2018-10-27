package Charpter9;

import java.util.Arrays;

/*
 * 最坏情况为线性时间的选择算法
 * 有两种方法，经复杂度分析，都是线性
 * 一种是根据书上伪代码编写Selection.select_CSLR
 * 一种自己对上面代码进行改编Selection.select_Original
 * 
 */

public class LinearSelect {
	
	public static void main(String[] args) {
		int[] A = {15,9,8,10,3,4,2,11,13,6,1,5,12,14,7};
		System.out.println(Arrays.toString(A));
		int x = Selection.select_CSLR(A, 1, A.length-1, 8);
		System.out.println(Arrays.toString(A));
		System.out.println(x);
	}
	

}

class Selection {
	
	public static int partition(int[] A, int start, int end, int x) {
		
		int index = 0; //记录与主元x相同元素的下标
		int i = start-1;
		int tmp;
		
		for(int j=start; j<=end; j++) {
			if(A[j] <= x) {
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
	
	public static void insertSort(int[] A, int start, int end) {
		int key;
		int i;
		for(int j=start+1; j<=end; j++) {
			key = A[j];
			i = j-1;
			while(i>=start && A[i] > key) {
				A[i+1] = A[i];
				i--;
			}
			A[++i] = key;
		}
	}
	
	public static int selectMedian(int[] A, int p, int r) {
		
		//base-case
		if(p == r) {
			return A[p];
		}
		
		int n = r-p+1;
		int groupNum = (int)Math.ceil(n/5.0);
		int[] start = new int[groupNum];
		int[] end = new int[groupNum];
		int[] median = new int[groupNum];
		
		for(int i=0; i<(n/5); i++) {
			start[i] = 5*i+p;
			end[i] = 5*i+4+p;
			Selection.insertSort(A, start[i], end[i]);
			median[i] = A[start[i]+2];
		}
		if(n%5 != 0) {
			start[groupNum-1] = 5*(n/5)+p;
			end[groupNum-1] = r;
			Selection.insertSort(A, start[groupNum-1], end[groupNum-1]);
			median[groupNum-1] = A[(start[groupNum-1]+end[groupNum-1])/2];
		}
		
		return Selection.selectMedian(median, 0, groupNum-1); //主元
		
	}
	
	public static int select_Original(int[] A, int start, int end, int index) {
		int x = Selection.selectMedian(A, start, end);
		
		int q = Selection.partition(A, start, end, x);
		
		int k = q-start+1; //划分点到当前起点的距离
		
		if(index == k) {
			return A[q];
		} else if(index < k) {
			return Selection.select_Original(A, start, q-1, index);
		} else {
			return Selection.select_Original(A, q+1, end, index-k);
		}
		
	}
	
	
	public static int select_CSLR(int[] A, int p, int r, int index) {
		
		//base-case
		if(p == r) {
			return A[p];
		}
		
		int n = r-p+1;
		int groupNum = (int)Math.ceil(n/5.0);
		int start;
		int end;
		int median;
		
		for(int i=0; i<(n/5); i++) {
			start = 5*i+p;
			end = 5*i+4+p;
			Selection.insertSort(A, start, end);
			median = A[start+2];
			A[start+2] = A[i+p];
			A[i+p] = median;
		}
		if(n%5 != 0) {
			start = 5*(n/5)+p;
			end = r;
			Selection.insertSort(A, start, end);
			median = A[(start+end)/2];
			A[(start+end)/2] = A[n/5+p];
			A[n/5+p] = median;
		}
		
		int x = select_CSLR(A, p, p+groupNum-1, (groupNum/2+1)); //主元
		
		int q = Selection.partition(A, p, r, x);
		
		int k = q-p+1;
		
		if(k==index) {
			return A[q];
		} else if(index < k) {
			return select_CSLR(A, p, q-1, index);
		} else {
			return select_CSLR(A, q+1, r, index-k);
		}
		
	}

	
}
