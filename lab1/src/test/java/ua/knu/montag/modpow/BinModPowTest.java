package ua.knu.montag.modpow;

import org.junit.Test;

import java.math.BigInteger;

import static java.math.BigInteger.*;
import static org.junit.Assert.assertEquals;

public class BinModPowTest {

    @Test
    public void binPow() {
        BigInteger exp1 = BinModPow.binPow(new BigInteger("100"), new BigInteger("5"), new BigInteger("7"));
        BigInteger exp2 = BinModPow.binPow(ZERO, new BigInteger("123"), new BigInteger("7"));
        BigInteger exp3 = BinModPow.binPow(ONE, new BigInteger("7576234374"), new BigInteger("7"));
        BigInteger exp4 = BinModPow.binPow(new BigInteger("72657634"), ZERO, new BigInteger("7"));
        BigInteger exp5 = BinModPow.binPow(new BigInteger("72657635"), ONE, new BigInteger("7"));

        assertEquals(exp1, new BigInteger("4"));
        assertEquals(exp2, ZERO);
        assertEquals(exp3, ONE);
        assertEquals(exp4, ONE);
        assertEquals(exp5, ONE);
    }
}
