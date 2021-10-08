package com.becu;

public class PublicKey {

    private long e;
    private long n;

    public PublicKey(long e, long n) {
        this.e = e;
        this.n = n;
    }

    public long getE() { return e; }

    public long getN() {
        return n;
    }
}
