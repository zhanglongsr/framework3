package com.zxl.test.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import org.junit.Test;

public class LinkedHashMapTest
{
    @Test
    public void testLinkedHashMap()
    {
        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
        map.put("rsiss", "rsiss");
        map.put("101", "101");
        map.put("abc", "abc");
        map.put("e63435", "e63435");
        
        Iterator<String> iter = map.keySet().iterator();
        while(iter.hasNext())
        {
            System.out.println(iter.next());
        }
        
        System.out.println("-----------------------");
        HashMap<String, String> map1 = new HashMap<String, String>();
        map1.put("rsiss", "rsiss");
        map1.put("101", "101");
        map1.put("abc", "abc");
        map1.put("e63435", "e63435");
        map1.values();
        Iterator<String> iter1 = map1.keySet().iterator();
        while(iter1.hasNext())
        {
            System.out.println(iter1.next());
        }
        
        LinkedList a = new LinkedList();
        a.add(new Integer(1));
        ArrayList b = null;
    }
    
}
