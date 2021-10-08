package com.becu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Encryption encryption = new Encryption();
        encryption.generateKeys();
        System.out.println("Introdu mesajul:");

        Scanner scanner = new Scanner(System.in);
        String inputString = scanner. nextLine();
        long[] mesajul = encryption.encrypt(inputString);
        System.out.println("Mesajul criptat:");
        for (long i: mesajul) {
            System.out.print(i + " ");
        }
        System.out.println();

        Decryption decryption = new Decryption();
        decryption.receivePrivateKey(encryption.getPrivateKey());

        System.out.println("Mesajul decriptat:");
        System.out.println(decryption.decrypt(mesajul));

    }
}
