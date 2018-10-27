package Charpter7;

import java.util.Arrays;

public class QuickSortMain {
	
	public static void main(String[] args) {
		
//		int[] A = {13, 19, 9, 5, 12, 8, 7, 4, 21, 2, 6, 11};
		int[] A = {1,11,3,4,5,6,7,8,9,10,2,12};
		int[] B = {1,11,3,4,5,6,7,8,9,10,2,12};
//		int[] A = {1,1,1,1,1,1,1,1,1,1,1,1};
		
		System.out.println("A: ");
		System.out.println(Arrays.toString(A));
		QuickSort(A);
		System.out.println(Arrays.toString(A));
		
		System.out.println("B: ");
		System.out.println(Arrays.toString(B));
		QuickSort(B);
		System.out.println(Arrays.toString(B));
		
		
	}
	
	//快排函数
	public static void QuickSort(int[] A, int start, int end) {
		
		//base-case
		//while start equals end
		//recursion should be stoped
		
		if(start<end) {
			//PARTITION分解重排
			int q = Partition(A, start, end);
			
			//Recursion递归
			QuickSort(A, start, q-1);
			QuickSort(A, q+1, end);
			
			//由于是原址排序，所以不需要合并
			
		}
	}
	
	//QuickSort Func Overload函数重载
	public static void QuickSort(int[] A) {
		QuickSort(A, 0, A.length-1);
	}
	
	//快排函数
		public static void RandomizedQuickSort(int[] A, int start, int end) {
			
			//base-case
			//while start equals end
			//recursion should be stoped
			
			if(start<end) {
				//PARTITION分解重排
				int q = RandomizedPartition(A, start, end);
				
				//Recursion递归
				QuickSort(A, start, q-1);
				QuickSort(A, q+1, end);
				
				//由于是原址排序，所以不需要合并
				
			}
		}
		
		//RandomizedQuickSort Func Overload函数重载
		public static void RandomizedQuickSort(int[] A) {
			RandomizedQuickSort(A, 0, A.length-1);
		}
	
	public static int Partition(int[] A, int start, int end) {
		int x = A[end];
		int i = start-1;
		
		int temp;
		int count = 0;//与主元相同的元素个数计数器
		for(int j = start; j<end; j++) {
			if(A[j]<=x) {
				i++;
				temp = A[j];
				A[j] = A[i];
				A[i] = temp;
				count = (x==A[j]) ? (count+1) : count;
			}
			
		}
		
		//exchange A[end] with A[i+1]
		count++;
		i++;
		A[end] = A[i];
		A[i] = x;
		
		if(count != (end-start+1))
			return i;
		else
			return (start+end)/2;
	}
	
	//随机化分解快排
	public static int RandomizedPartition(int[] A, int start, int end) {
		int i = (int)(Math.random() * (end-start)) + start;
		int temp = A[end];
		A[end] = A[i];
		A[i] = temp;
		
		return Partition(A, start, end);
	}

}
