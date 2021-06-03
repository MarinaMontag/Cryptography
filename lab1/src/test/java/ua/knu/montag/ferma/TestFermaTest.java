package ua.knu.montag.ferma;

import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestFermaTest {
    @Test
    public void testCheckPrime(){
        String[] primeList = new String[]{
                "7", "11", "13",
                "17", "19", "23",
                "29", "31", "37",
                "41", "43", "47",
                "53", "59", "61",
                "67", "71", "73",
                "79", "83", "89",
                "97", "101", "103",
                "107", "109", "113",
                "999946346549", "999946346551",
                "999946346563", "999946346611",
                "999946346723", "999946346731",
                "999946346809", "999946346813"
        };
        String[] noPrimeList = new String[]{
                "8", "12", "15",
                "18", "20", "21",
                "28", "33", "45"
        };
        for (String bigInteger : primeList) {
            assertTrue(TestFerma.checkPrime(new BigInteger(bigInteger), 20));
        }

        for (String bigInteger : noPrimeList) {
            assertFalse(TestFerma.checkPrime(new BigInteger(bigInteger), 20));
        }
    }
}
