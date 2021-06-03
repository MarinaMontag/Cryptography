package ua.knu.montag.karazuba;

import java.math.BigInteger;

import static java.math.BigInteger.TWO;

public class KarazubaMultiplication {
    public static BigInteger multiply(BigInteger x, BigInteger y) {
        // cutoff to brute force
        int N = Math.max(x.bitLength(), y.bitLength());

        // Karazuba is not efficient
        if (N <= 10)
            return x.multiply(y);

        // number of bits divided by 2, rounded up
        N = (N / 2) + (N % 2);

        // x = X1 * 2^N + X2, y = Y1 * 2^N + Y2
        BigInteger x1 = x.shiftRight(N);
        BigInteger x2 = x.subtract(x1.shiftLeft(N));
        BigInteger y1 = y.shiftRight(N);
        BigInteger y2 = y.subtract(y1.shiftLeft(N));

        // compute sub-expressions
        BigInteger x1y1 = multiply(x1, y1);
        BigInteger x2y2 = multiply(x2, y2);
        BigInteger x1x2y1y2 = multiply(x2.add(x1), y2.add(y1)); // (x1+x2) * (y1+y2)

        // x1y1 * 2^2n + (x1y2 + x2y1) * 2^n + x2y2
        // (x1+x2)(y1+y2) - x1y1 - x2y2 = x1y2 + x2y1
        // x1y1 * 2^2n + ((x1+x2)(y1+y2) - x1y1 - x2y2) * 2^n + x2y2
        return x2y2.add(x1x2y1y2.subtract(x2y2).subtract(x1y1).shiftLeft(N)).add(x1y1.shiftLeft(2 * N));
    }

    public static void main(String []args){
        System.out.println("karazubaMult 2*2="+multiply(TWO,TWO).toString());
        System.out.println("karazubaMult 1e9*1e9="+multiply(BigInteger.valueOf(1000_000_000),BigInteger.valueOf(1000_000_000)).toString());
        System.out.println("karazubaMult 2e9*2e9="+multiply(BigInteger.valueOf(2000_000_000),BigInteger.valueOf(2000_000_000)).toString());
    }
}
