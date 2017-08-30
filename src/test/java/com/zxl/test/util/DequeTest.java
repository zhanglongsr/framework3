package com.zxl.test.util;

import java.util.ArrayDeque;
import java.util.Deque;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DequeTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test() {
        Deque<String> deque = new ArrayDeque<String>();
        deque.offer("zhangxl");
        deque.offer("zhangym");
        deque.offer("zhanggq");
        deque.offer("zhangxr");
        deque.offer("zhangsw");
        System.out.println(deque.pollFirst());
        System.out.println(deque.pollFirst());
        System.out.println(deque.pollFirst());
        System.out.println(deque.pollFirst());
        System.out.println(deque.pollFirst());
        deque.offer("zhangxl");
        deque.offer("zhangym");
        deque.offer("zhanggq");
        deque.offer("zhangxr");
        deque.offer("zhangsw");

        System.out.println(deque.pollFirst());
        System.out.println(deque.pollFirst());
        System.out.println(deque.pollFirst());
        System.out.println(deque.pollFirst());
        System.out.println(deque.pollFirst());
        deque.offer("zhangxl");
        deque.offer("zhangym");
        deque.offer("zhanggq");
        deque.offer("zhangxr");
        deque.offer("zhangsw");

        System.out.println(deque.pollFirst());
        System.out.println(deque.pollFirst());
        System.out.println(deque.pollFirst());
        System.out.println(deque.pollFirst());
        System.out.println(deque.pollFirst());

        deque.offer("zhangxl");
        deque.offer("zhangym");
        deque.offer("zhanggq");
        deque.offer("zhangxr");
        deque.offer("zhangsw");

        System.out.println(deque.pollFirst());
        System.out.println(deque.pollFirst());
        System.out.println(deque.pollFirst());
        System.out.println(deque.pollFirst());
        System.out.println(deque.pollFirst());
    }

}
