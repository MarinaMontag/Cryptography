package ua.knu.montag.montgomery;

import ua.knu.montag.euclid.ExtendedEuclidAlgorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

public class MontgomeryArithmetic {
    private static BigInteger MonPro(BigInteger T, BigInteger N, BigInteger R, int pow) {
        BigInteger[] array = ExtendedEuclidAlgorithm.extendedAlgorithm(N, R);
        BigInteger d = array[0];

        if (!d.equals(BigInteger.ONE)) {
            throw new IllegalArgumentException("GCD(N,R) != 1");
        }

        BigInteger invN = array[1];
        BigInteger minusInvN = invN.negate();
        BigInteger m = (T.multiply(minusInvN)).mod(R);
        BigInteger t = (T.add(m.multiply(N))).shiftRight(pow);

        while (N.compareTo(t) < 0) {
            t = t.subtract(N);
        }
        return t;
    }

    public static BigInteger multiply(BigInteger a, BigInteger b, BigInteger R, BigInteger N, int pow) {
        BigInteger a1 = a.shiftLeft(pow).mod(N);
        BigInteger b1 = b.shiftLeft(pow).mod(N);
        BigInteger c1 = MonPro(a1.multiply(b1), N, R, pow);
        return MonPro(c1, N, R, pow);
    }

    public static BigInteger pow(BigInteger a, BigInteger e, BigInteger R, BigInteger N, int pow) {
        BigInteger a1 = a.shiftLeft(pow).mod(N);
        BigInteger x1 = BigInteger.ONE;
        while (e.compareTo(BigInteger.ZERO) > 0) {
            x1 = MonPro(x1.multiply(a1), N, R, pow);
            e = e.subtract(BigInteger.ONE);
        }
        return x1;
    }

    // Example input: 68 57 109 128
    public static void main(String[] args) throws IOException {
        // Prompt user on standard output, parse standard input
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.US_ASCII));
        System.out.print("Number x: ");
        BigInteger x = new BigInteger(in.readLine());
        System.out.print("Operation (\"times\" or \"pow\"): ");
        String oper = in.readLine();
        System.out.print("b = ");
        BigInteger y = new BigInteger(in.readLine());
        System.out.print("Modulus: ");      // непарне число
        BigInteger N = new BigInteger(in.readLine());
        System.out.print("R: ");            // степінь 2
        BigInteger R = new BigInteger(in.readLine());
        System.out.println();

        BigInteger R1 = R;
        int pow = -1;
        while (R1.compareTo(BigInteger.ZERO) > 0) {
            R1 = R1.divide(BigInteger.TWO);
            pow++;
        }

        BigInteger res;
        BigInteger res_real;

        if (oper.equals("times")) {
            res = multiply(x, y, R, N, pow);
            res_real = x.multiply(y).mod(N);
        } else if (oper.equals("pow")) {
            res = pow(x, y, R, N, pow);
            res_real = x.modPow(y, N);
        } else {
            throw new IllegalArgumentException("Invalid operation: " + oper);
        }
        System.out.printf("%d%s%d mod %d", x, oper.equals("times") ? " * " : "^", y, N);
        System.out.println(" = " + res);
        System.out.println("Real result = " + res_real);
    }
}
