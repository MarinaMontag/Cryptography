package ua.knu.montag;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Encryptor {
    private final Key key;

    public Encryptor(Key key) {
        this.key = key;
    }

    public List<CipherMessage> encryptString(String s) {
        List<CipherMessage> cipherTextCipherMessages = new LinkedList<>();

        byte[] byteInput = s.getBytes();
        for (byte b : byteInput) {
            cipherTextCipherMessages.add(this.encrypt(new byte[]{b}));
        }

        return cipherTextCipherMessages;
    }

    public CipherMessage encrypt(byte[] message) {
        BigInteger y = key.getPublicKey().getKeyY();
        BigInteger g = key.getPublicKey().getKeyG();
        BigInteger p = key.getPublicKey().getKeyP();

        int randomK = generateRandomKByP(p);
        BigInteger messageBI = new BigInteger(message);
        BigInteger a = g.modPow(BigInteger.valueOf(randomK), p);
        BigInteger b = ((y.pow(randomK)).multiply(messageBI)).mod(p);

        return new CipherMessage(a.toByteArray(), b.toByteArray());
    }

    private int generateRandomKByP(BigInteger p) {
        Random random = new Random();

        //guaranteed k > 0 (at least 2), and in range of int size
        if (p.bitLength() > 32) {
            return random.nextInt(Integer.MAX_VALUE - 2) + 2;
        } else {
            int pInt = p.intValue();
            if (pInt < 3) {
                throw new RuntimeException("LESS THAN 3");
            }
            return Math.abs(random.nextInt(pInt - 3)) + 2;
        }
    }

    public String decryptString(List<CipherMessage> cipherTextMessages) {
        StringBuilder stringBuffer = new StringBuilder();
        for (CipherMessage m : cipherTextMessages) {
            stringBuffer.append(new String(this.decrypt(m)));
        }

        return stringBuffer.toString();
    }

    public byte[] decrypt(CipherMessage message) {
        BigInteger p = key.getPublicKey().getKeyP();
        BigInteger x = key.getPrivateKey().getKey();
        BigInteger a = new BigInteger(message.getA());
        BigInteger b = new BigInteger(message.getB());

        BigInteger degree = p.subtract(x).subtract(BigInteger.ONE);
        BigInteger decrypted = (b.multiply(bigIntPowBrute(a, degree))).mod(p);

        return decrypted.toByteArray();
    }

    private static BigInteger bigIntPowBrute(BigInteger base, BigInteger exp) {
        BigInteger i = BigInteger.ONE;
        BigInteger product = BigInteger.ONE;
        for (; i.compareTo(exp) <= 0; i = i.add(BigInteger.ONE)) {
            product = product.multiply(base);
        }

        return product;
    }


}
