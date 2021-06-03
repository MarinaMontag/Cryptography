package ua.knu.montag.millerrabin;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Random;

public class TestMillerRabin {
    private static final BigInteger THREE = BigInteger.valueOf(3);

    public static boolean isProbablePrime(BigInteger n, int maxIterations) {
        if (n.compareTo(THREE) < 0)            // n > 2
            return true;

        int s = 0;
        BigInteger d = n.subtract(BigInteger.ONE);        // n - 1 = 2^s * d, where d % 2 = 1

        // while d is even, make it odd dividing by two
        while (d.mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
            s++;
            d = d.divide(BigInteger.TWO);
        }

        for (int i = 0; i < maxIterations; i++) {
            BigInteger a = uniformRandom(n.subtract(BigInteger.ONE));
            BigInteger x = a.modPow(d, n);    // x = a^d mod n

            // if x == 1 or x == (n - 1) then it may be prime
            if (x.equals(BigInteger.ONE) || x.equals(n.subtract(BigInteger.ONE)))
                continue;

            int r = 0;
            for (; r < s; r++) {
                x = x.modPow(BigInteger.TWO, n);    // x = x^2 mod n

                // n is definitely not prime
                if (x.equals(BigInteger.ONE))
                    return false;

                if (x.equals(n.subtract(BigInteger.ONE)))
                    break;
            }

            // None of the steps made x equal n - 1 then n is definitely not prime
            if (r == s)
                return false;
        }
        return true;
    }

    // Returns number between bottom and top
    private static BigInteger uniformRandom(BigInteger top) {
        Random rnd = new Random();

        BigInteger res;
        do {
            res = new BigInteger(top.bitLength(), rnd);
        } while (res.compareTo(BigInteger.TWO) < 0 || res.compareTo(top) > 0);
        return res;
    }


    public static void main(String[] args) {
        String[] primes = {"2", "3", "23", "97", "107", "193"};
        String[] nonPrimes = {"21", "102", "198"};

        int k = 40;
        for (String p : primes)
            System.out.println(isProbablePrime(new BigDecimal(p).toBigInteger(), k));
        System.out.println();
        for (String n : nonPrimes)
            System.out.println(isProbablePrime(new BigDecimal(n).toBigInteger(), k));
    }
}
