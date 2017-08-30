package com.zxl.test.javabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FinalTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testFinalField() {

        FinalFieldClass ffc1 = new FinalFieldClass();
        FinalFieldClass ffc2 = new FinalFieldClass();

        for (int i = 0; i < 10; i++) {
            ffc1.incrGen();
            ffc1.incrNum();
            ffc2.incrGen();
            ffc2.incrNum();
        }

        System.out.println("ffc1-gen:" + ffc1.getGen() + "-num:" + ffc1.getNum() + ",ffc2-gen:" + ffc2.getGen()
                + "-num:" + ffc2.getNum());
    }

}
