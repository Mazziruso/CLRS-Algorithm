package PrimeDetection;

public class RabinMillerScheme {

    private static long mod_mul(long a, long b, long n) {
        long res = 0;
        while((b & 1) == 1){

        }
    }

    private static boolean witness(long a, long n) {
        //calculate t and u, equivalent n=u*2^t
        long t = 0;
        long u = n;
        while((u & 1) == 0) {
            t++;
            u >>= 1;
        }
    }

    private static boolean MillerRabin(long n, long s) {

    }

    public static void main(String[] args) {

    }
}
