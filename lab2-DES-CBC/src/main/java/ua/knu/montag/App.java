package ua.knu.montag;

import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("write password");
        String password = scanner.nextLine();

        System.out.println("write text");
        String s = scanner.nextLine();

        byte[] array = new byte[8]; // length is bounded by 8
        new Random().nextBytes(array);
        String vectorInit = new String(array, StandardCharsets.UTF_8);

        DESAlgorithm des = new DESAlgorithm();
        System.out.println(s + "\t\tstart");
        String p = des.encrypt(s, password, vectorInit);
        System.out.println(p + "\t\tafter encrypt");
        s = des.decrypt(p, password, vectorInit);
        System.out.println(s + "\tafter decrypt");
    }
}
