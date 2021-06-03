package ua.knu.montag.euclid;

import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.assertEquals;

public class ExtendedEuclidAlgorithmTest {
    @Test
    public void extendedAlgorithm() {
        BigInteger[] gcd1 = ExtendedEuclidAlgorithm
                .extendedAlgorithm(new BigInteger(String.valueOf(84)), new BigInteger(String.valueOf(275)));
        assertEquals(gcd1[0], BigInteger.ONE);
        assertEquals(gcd1[1],  new BigInteger("-36"));
        assertEquals(gcd1[2],  new BigInteger("11"));

        BigInteger[] gcd2 = ExtendedEuclidAlgorithm
                .extendedAlgorithm(new BigInteger(String.valueOf(100)), new BigInteger(String.valueOf(100)));
        assertEquals(gcd2[0], new BigInteger("100"));
        assertEquals(gcd2[1], BigInteger.ZERO);
        assertEquals(gcd2[2], BigInteger.ONE);

        BigInteger[] gcd3 = ExtendedEuclidAlgorithm
                .extendedAlgorithm(new BigInteger(String.valueOf(51)), new BigInteger(String.valueOf(17)));
        assertEquals(gcd3[0], new BigInteger("17"));
        assertEquals(gcd3[1], BigInteger.ZERO);
        assertEquals(gcd3[2], BigInteger.ONE);

        BigInteger[] gcd4 = ExtendedEuclidAlgorithm
                .extendedAlgorithm(new BigInteger(String.valueOf(35)), new BigInteger(String.valueOf(20)));
        assertEquals(gcd4[0], new BigInteger("5"));
        assertEquals(gcd4[1], new BigInteger("-1"));
        assertEquals(gcd4[2], new BigInteger("2"));
    }


}
