package com.zxl.test.math;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ShiftOperationTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    // 左

    public void testLeft() {
        int x = 1 << 13;
        System.out.println(x);
        System.out.println(1 << 12);

        System.out.println(8 << 1);

    }

    @Test
    public void test1() {
        int x = 10, y = 15;

        System.out.println("x shift:" + (x << 1) + ",y shift:" + (y << 1));

        // System.out.println(x & y);
        System.out.println(x << 1 | y << 1);

        System.out.println((x << 1) + (y << 1));
    }

}
