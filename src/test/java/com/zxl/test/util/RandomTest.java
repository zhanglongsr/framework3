package com.zxl.test.util;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import org.junit.Test;

public class RandomTest
{
    @Test
    public void testNext()
    {
        
        Random r = new Random();
        for(int i = 0; i < 100; i++)
        {
            int x = r.nextInt(33);
            System.out.println("x:" + x);
        }
        
        // Runnable run = new Runnable()
        // {
        //
        // @Override
        // public void run()
        // {
        // Random r = new Random();
        // for(int i = 0; i < 20; i++)
        // {
        // int x = r.nextInt(20);
        // System.out.println("x:" + x);
        // }
        //
        // }
        //
        // };
        //
        // ExecutorService service = java.util.concurrent.Executors.newFixedThreadPool(5);
        // service.execute(run);
        
        HashSet<String> set = new HashSet<String>();
        set.add("23523");
        set.add("12345");
        set.add("zhangxl");
        set.add("yangyx");
        
        Iterator<String> iter1 = set.iterator();
        while(iter1.hasNext())
        {
            System.out.println(iter1.next());
        }
        System.out.println("-----------------");
        Iterator<String> iter2 = set.iterator();
        while(iter2.hasNext())
        {
            System.out.println(iter2.next());
        }
    }
    
}
