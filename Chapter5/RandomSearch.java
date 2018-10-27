package Charpter5;

public class RandomSearch {
	
	public static void main(String[] args) {
		
//		System.out.println(Math.round((Math.random()*5)));
		
		int[] A = {3,1,4,6,2,10,7,5,8};
		int[] B = {3,1,4,6,2,10,7,5,8};
		System.out.println(RandomS(A, 19));
		System.out.println(A.equals(B));
		
	}
	
	public static int RandomS(int[] A, int x) {
		int n = A.length;
		int[] index = new int[n];
		boolean flag = false;
		
		int i = (int) Math.round(Math.random()*(n-1));
		index[i] = 1;
		
		int j;
		while(A[i]!=x & !flag) {
			j = 0;
			while(j < n) {
				if(index[j++]==0) {
					flag = false;
					break;
				} else {
					flag = true;
				}
			}
			
			i = (int) Math.round(Math.random()*(n-1));
			index[i] = 1;
		}
		
		if(flag) {
			return -1;
		} else {
			return i;
		}
	}

}
