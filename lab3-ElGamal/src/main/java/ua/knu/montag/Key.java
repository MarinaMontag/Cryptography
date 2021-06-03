package ua.knu.montag;

import java.math.BigInteger;
import java.util.Random;

public class Key {
    private static final int CERTAINTY = 90;
    private final PublicKey publicKey;
    private final PrivateKey privateKey;

    public Key(PublicKey publicKey, PrivateKey privateKey) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }


    public static Key generateKey(int keyBitLength) {
        Random random = new Random();
        BigInteger p = new BigInteger(keyBitLength, CERTAINTY, random);
        BigInteger g = primitiveRootModulo(p);
        BigInteger x = new BigInteger(keyBitLength - 1, CERTAINTY, new Random());
        BigInteger y = g.modPow(x, p);

        return new Key(new PublicKey(y, g, p), new PrivateKey(x));
    }

    private static BigInteger primitiveRootModulo(BigInteger p) {
        BigInteger eulerFunctionValue = p.subtract(BigInteger.ONE);
        BigInteger i = BigInteger.ZERO;
        while (i.compareTo(eulerFunctionValue) <= 0) {
            if (i.modPow(eulerFunctionValue, p).compareTo(BigInteger.ONE) == 0) {
                return i;
            }
            i = i.add(BigInteger.ONE);
        }

        throw new RuntimeException("NOT FOUND");
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }
}
