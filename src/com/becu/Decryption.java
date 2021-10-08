package com.becu;

public class Decryption {

    private PrivateKey privateKey;

    public void receivePrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    public String decrypt(long[] messajul) {
        StringBuilder text = new StringBuilder();
        for (long l : messajul) {
            long symbol = modPow(l, privateKey.getD(), privateKey.getN());

            text.append((char) (symbol));
        }
        return text.toString();
    }

    // calculeaza (a ^ b) % c
    private long modPow(long a, long b, long c) {
        long res = 1;
        for (int i = 0; i < b; i++) {
            res *= a;
            res %= c;
        }
        return res % c;
    }
}
