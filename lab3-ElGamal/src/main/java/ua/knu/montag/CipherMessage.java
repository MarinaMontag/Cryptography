package ua.knu.montag;

public class CipherMessage {
    private final byte[] a;
    private final byte[] b;

    public CipherMessage(byte[] a, byte[] b) {
        this.a = a;
        this.b = b;
    }

    public byte[] getA() {
        return a;
    }

    public byte[] getB() {
        return b;
    }
}
