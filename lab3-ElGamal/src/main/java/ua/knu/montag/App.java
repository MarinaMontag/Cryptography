package ua.knu.montag;

import java.util.List;

public class App {
    public static void main(String[] args) {
        Key key = Key.generateKey(16);
        Encryptor encryptor = new Encryptor(key);

        String plaintext = "Hello, World!";
        List<CipherMessage> encrypted = encryptor.encryptString(plaintext);
        String decrypted = encryptor.decryptString(encrypted);
        System.out.println(decrypted);
    }
}
