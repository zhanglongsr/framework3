package com.zxl.test.util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

public class LinkedListTest
{
   
    public void testLIFO()
    {
        
        LinkedList<String> list = new LinkedList<String>();
        list.add("我是第一个加进来的");
        for(int i = 0; i < 100; i++)
        {
            String input = "第" + i + "次进入的";
            list.add(input);
            String output = list.removeLast();
            System.out.println("i=" + i + " " + "进入：" + input + " 取出:" + output);
        }
    }
    
    @Test
    public void testToArray(){
        List<Integer> list = new ArrayList<Integer>();
        list.add(100);
        list.add(80);
        list.add(502);
        list.add(109);
        
       Integer[] x = list.toArray(new Integer[]{});
       System.out.println(x.length);
    }
}
