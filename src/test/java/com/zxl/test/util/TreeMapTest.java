package com.zxl.test.util;

import java.util.Iterator;
import java.util.SortedMap;
import java.util.TreeMap;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TreeMapTest
{
    TreeMap<String, String> map = new TreeMap<String, String>();
    
    @Before
    public void setUp() throws Exception
    {
        map.put("12413", "zhangxl");
        map.put("68790", "new level");
        map.put("50505", "hello you");
        map.put("46465", "haier");
    }
    
    @After
    public void tearDown() throws Exception
    {
    }
    
    @Test
    public void testTailMap()
    {
        SortedMap<String, String> sm = map.tailMap("30000");
        Iterator<String> iter = sm.keySet().iterator();
        while(iter.hasNext())
        {
            String key = iter.next();
            System.out.println("key:" + key + ",value:" + sm.get(key));
        }
        
        System.out.println(sm.firstKey());
        
    }
    
}
