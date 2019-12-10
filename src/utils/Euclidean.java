package utils;

public class Euclidean {

    public static int gcd(int a, int b){
        int r0;
        int r1;
        int r2;

        if(a == b)
            return a;
        else if(a > b) {
            r1 = a;
            r2 = b;
        }
        else{
            r1 = b;
            r2 = a;
        }

        while(r2 != 0){
            r0 = r1;
            r1 = r2;
            int q = (int) Math.floor(((double)r0)/r1);
            r2 = r0-(r1*q);
        }

        return r1;
    }

    public static int inverse(int a, int n){
        int t = 0;
        int newT = 1;
        int r = n;
        int newR = a;

        while(newR != 0){
            int q = (int) Math.floor(((double)r)/newR);
            int tmp = newT;
            newT = t - q*newT;
            t = tmp;

            tmp = newR;
            newR = r - q*newR;
            r = tmp;
        }

        if(r > 1)
            return -1;
        if(t < 0)
            t = t+n;

        return t;
    }
}
