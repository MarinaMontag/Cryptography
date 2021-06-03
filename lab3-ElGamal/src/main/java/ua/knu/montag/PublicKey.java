package ua.knu.montag;

import java.math.BigInteger;

public class PublicKey {
    private final BigInteger keyY;
    private final BigInteger keyG;
    private final BigInteger keyP;

    public PublicKey(BigInteger keyY, BigInteger keyG, BigInteger keyP) {
        this.keyY = keyY;
        this.keyG = keyG;
        this.keyP = keyP;
    }

    public BigInteger getKeyY() {
        return keyY;
    }

    public BigInteger getKeyG() {
        return keyG;
    }

    public BigInteger getKeyP() {
        return keyP;
    }
}
