package ua.knu.montag;

import java.math.BigInteger;

public class PrivateKey {
    private final BigInteger key;

    public PrivateKey(BigInteger key) {
        this.key = key;
    }

    public BigInteger getKey() {
        return key;
    }
}
