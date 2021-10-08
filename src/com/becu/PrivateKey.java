package com.becu;

public class PrivateKey {

    private long d;
    private long n;

    public PrivateKey(long d, long n) {
        this.d = d;
        this.n = n;
    }

    public long getD() {
        return d;
    }

    public long getN() {
        return n;
    }
}