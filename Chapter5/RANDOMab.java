package Charpter5;

public class RANDOMab {
	
	public static void main(String[] args) {
		
		System.out.println(randomab(5,10));
		System.out.println(Math.round(Math.random()*5+5));
		
	}
	
	public static int randomab(int a, int b) {
		int n = Integer.toBinaryString(b).length();
		int x = -1;
		int pow2;
		
		while(x>b | x<a) {
			x = 0;
			pow2 = 1;
			for(int i=0; i<n; i++) {
				x += (int)(Math.round(Math.random())) * pow2;
				pow2 *= 2;
			}
		}
		
		return x;
	}

}
