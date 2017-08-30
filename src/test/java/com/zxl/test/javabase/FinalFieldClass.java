package com.zxl.test.javabase;

import java.util.concurrent.atomic.AtomicInteger;

public class FinalFieldClass {

    private final AtomicInteger num = new AtomicInteger();
    private static final AtomicInteger poolNumberGenerator;

    static {
        poolNumberGenerator = new AtomicInteger();
    }

    public void incrNum() {
        num.addAndGet(1);
    }

    public Integer getNum() {
        return num.get();
    }

    public void incrGen() {
        poolNumberGenerator.addAndGet(1);
    }

    public Integer getGen() {
        return poolNumberGenerator.get();
    }
}
