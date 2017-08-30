package com.zxl.algorithm.hash;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HashTest
{
    
    @Before
    public void setUp() throws Exception
    {
    }
    
    @After
    public void tearDown() throws Exception
    {
    }
    
    @Test
    public void test()
    {
        Hashing hashing = Hashing.MD5;
        
        String[] keys = {"index_123456", "index_123457", "index_123458", "index_123459", "index_123460", "index_123461"};
        for(String key : keys)
        {
            long hashVal = hashing.hash(key);
            System.out.println("key:" + key + "'s hash value " + hashVal);
        }
    }
    
}
