package ua.knu.montag.modpow;

import java.math.BigInteger;

import static java.math.BigInteger.*;

public class BinModPow {

        public static BigInteger binPow(BigInteger a, BigInteger b, BigInteger mod) { //a^b
            if (b.equals(ZERO))
                return ONE;
            if (b.equals(ONE))
                return a.mod(mod);

            if (b.mod(TWO).equals(ONE)) {
                return a.multiply(binPow(a, b.subtract(ONE), mod)).mod(mod);
            }

            return binPow(a, b.divide(TWO), mod).pow(2).mod(mod);

        }

        public static void main(String[] args) {
            BigInteger a=ONE;
            BigInteger b=TWO;
            BigInteger c=TWO;
            for (int i = 0; i < 100; i++) {
                if (i%10==0) System.out.println(i);
                a=a.add(TWO);
                for (int j = 0; j < 100; j++) {
                    b=b.add(ONE);
                    for (int k = 0; k < 100; k++) {
                        c=c.add(ONE);
                        if (!binPow(a, b, c).equals(a.modPow(b, c))){
                            System.out.println("error a="+a+"\tb="+b+"\tc="+c);
                        }
                    }
                }
            }
            System.out.println("binPow 99^99= " + binPow(BigInteger.valueOf(99), BigInteger.valueOf(99), BigInteger.valueOf(100)).toString());
            System.out.println("binPow 9^2=" + binPow(TEN.subtract(ONE), TWO, TEN).toString());
            System.out.println("binPow 2^10=" + binPow(TWO, TEN, TWO).toString());
        }

}
