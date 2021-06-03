package ua.knu.montag.montgomery;

import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.assertEquals;

public class MontgomeryArithmeticTest {

    private final BigInteger R = new BigInteger("128");
    private final int pow = 8;

    @Test
    public void multiply() {
        assertEquals(MontgomeryArithmetic.multiply(new BigInteger("68"), new BigInteger("57"),
                R, new BigInteger("109"), pow),
                new BigInteger("6"));
        assertEquals(MontgomeryArithmetic.multiply(new BigInteger("853"), new BigInteger("7821"),
                R, new BigInteger("2957"), pow),
                new BigInteger("719"));
    }

    @Test
    public void pow() {
        assertEquals(MontgomeryArithmetic.pow(new BigInteger("68"), new BigInteger("57"),
                R, new BigInteger("109"), pow),
                new BigInteger("39"));
        assertEquals(MontgomeryArithmetic.pow(new BigInteger("357"), new BigInteger("875"),
                R, new BigInteger("733"), pow),
                new BigInteger("402"));
    }
}
