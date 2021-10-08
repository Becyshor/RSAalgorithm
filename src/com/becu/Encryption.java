package com.becu;

import java.util.Random;

public class Encryption {

    private PublicKey publicKey;
    private PrivateKey privateKey;
    private final int MaxNrBitKey = 22;
    //numarul maxim de biti care va avea cheia
    private final int NrBitPrime = Math.floorDiv(MaxNrBitKey, 2);
    //numarul de biti pentru numarul prim

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    //metoda pentru generarea cheilor
    public void generateKeys() {
        //generarea lui p
        int p = getPrime();

        int q;
        //generarea lui q
        do {
            q = getPrime();
        } while (p == q);

        //calculam n
        long n = p * q;

        //calculăm fi
        long fi = (p - 1) * (q - 1);

        //generăm e prin metoda findE
        long e = findE(fi);

         //generăm d prin metoda findD
        long d = findD(fi, e);

        publicKey = new PublicKey(e, n);
        privateKey = new PrivateKey(d, n);
    }

    //metoda pentru generarea aleatorie a numerelor prime
    private int getPrime() {
        int possiblePrime;
        Random random = new Random();
        do {
            possiblePrime = (int) (random.nextInt((int) (Math.pow(2, NrBitPrime) - Math.pow(2, NrBitPrime - 1) + 1)) + Math.pow(2, NrBitPrime - 1) + 1);
            //verificam dacă numărul generat este prim
        } while (!isPrime(possiblePrime));
        return possiblePrime;
    }

    //metoda pentru verificarea numerelor prime
    private boolean isPrime(int possiblePrime) {
        int[] firstPrimes = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29,
                31, 37, 41, 43, 47, 53, 59, 61, 67, 71,
                73, 79, 83, 89, 97, 101};
        if (possiblePrime < 2)
            return false;

        if (possiblePrime % 2 == 0)
            return false;
        for (int prime : firstPrimes) {
            if (possiblePrime % prime == 0)
                return false;
        }
        return isMillerRabinTestPassed(possiblePrime);
    }

    //testul care decide dacă numărul este sau nu este prim
    private boolean isMillerRabinTestPassed(int possiblePrime) {
        int testRepetitions = 20;
        Random random = new Random();
        int possiblePrimePredecessor = possiblePrime - 1;
        int t = possiblePrimePredecessor;
        int s = 0;

        while (t % 2 == 0) {
            s++;
            t /= 2;
        }
        cicleA:
        for (int i = 0; i < testRepetitions; i++) {
            int a = random.nextInt(possiblePrimePredecessor - 2) + 2;
            int x = (int) modPow(a, t, possiblePrime);
            if (x == 1 || x == possiblePrimePredecessor)
                continue;
            for (int j = 0; j < (s - 1); j++) {
                x = (int) modPow(x, 2, possiblePrime);
                if (x == 1)
                    return false;
                if (x == possiblePrimePredecessor)
                    continue cicleA;
            }
            return false;
        }
        return true;
    }

    //aflăm cel mai mare divizor comun
    private long gcd(long a, long b) {
        if (b == 0) {
            return a;
        } else {
            return gcd(b, a % b);
        }
    }

    //metoda pentru a găsi e
    private long findE(long fi) {
        Random random = new Random();
        long e = random.nextInt((int) fi);
        while (e < fi) {
            if (gcd(e, fi) == 1)
                break;
            else
                e = random.nextInt((int) fi);
        }
        return e;
    }

    //Extended Euclidian Algorithm pentru a afla d
    private long[] extEuclid(long a, long b) {
        if (b == 0) return new long[]{a, 1, 0};
        long[] arr = extEuclid(b, a % b);
        long d = arr[0];
        long s = arr[2];
        long t = arr[1] - (a / b) * arr[2];
        return new long[]{d, s, t};
    }

    //metoda pentru a găsi d
    private long findD(long fi, long e) {

        return extEuclid(e, fi)[1];
    }

    //transformăm textul în numere
    private int[] textToNumbers(String text) {
        int[] codes = new int[text.length()];
        for (int i = 0; i < text.length(); i++) {
            codes[i] = text.charAt(i);
        }
        return codes;
    }

    //metoda de criptare
    public long[] encrypt(String text) {
        long[] mesajul = new long[text.length()];
        int[] nomesajul = textToNumbers(text);
        for (int i = 0; i < text.length(); i++) {
            mesajul[i] = modPow(nomesajul[i], publicKey.getE(), publicKey.getN());
        }
        return mesajul;
    }

    //calculăm (a ^ b) % c
    private long modPow(long a, long b, long c) {
        long res = 1;
        for (int i = 0; i < b; i++) {
            res *= a;
            res %= c;
        }
        return res % c;
    }
}
