package MillerRabinScheme;

import java.util.Random;

public class Main {

    private static long mod_mul(long a, long b, long n) {
        long res = 0;
        while(b != 0) {
            if((b & 1) == 1) {
                res = (res + a) % n;
            }
            a = (a + a) % n;
            b >>= 1;
        }
        return res;
    }

    private static long mod_pow(long a, long b, long n) {
        long res = 1;
        while(b != 0) {
            if((b & 1) == 1) {
                res = (res * a) % n;
            }
            a = (a * a) % n;
            b >>= 1;
        }
        return res;
    }

    //if n is a composite then return true;
    private static boolean witness(long a, long n) {
        //special case while n=0,1,2
        if(n == 0) {
            return true;
        }
        if(n == 1 || n == 2) {
            return false;
        }

        //calculate t and u, equivalent n-1=u*2^t
        long t = 0;
        long u = n - 1;
        while((u & 1) == 0) {
            t++;
            u >>= 1;
        }
        long x_pre = mod_pow(a,u,n);
        long x = x_pre;
        for(int i=0; i<t; i++) {
            x = mod_pow(x_pre,2, n);
            if(x==1 && x_pre!=1 && x_pre!=(n-1)) {
                return true;
            }
            x_pre = x;
        }
        return (x != 1);
    }

    //if n is a prime then return true
    private static boolean MillerRabin(long n, long s) {
        Random rand = new Random();
        long a;
        for(int i=0; i<s; i++) {
            a = Math.abs(rand.nextLong() % (n-1)) + 1;
            if(witness(a, n)) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Random r = new Random();

        int s = 20;
        int n;
        for(int i=0; i<50; i++) {
            n = Math.abs(r.nextInt());
            if(MillerRabin(n, s)) {
                System.out.println(n + " is a prime");
            } else {
                System.out.println(n + " is a composite");
            }
        }
    }
}
