package com.zxl.test.util;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ArraysTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test() {
       String[] names = {"张三","李四","王五","田六"};
       
      String[] newNames =  Arrays.copyOf(names, 20);
      System.out.println("name array length:"+newNames.length);
      
      List<String> list = Arrays.asList(newNames);
      System.out.println("list's size:"+list.size());
    }

}
