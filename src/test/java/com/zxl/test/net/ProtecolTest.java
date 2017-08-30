package com.zxl.test.net;

import org.junit.Test;

public class ProtecolTest
{
    @Test
    public void testByte()
    {
        String x = "commond-0000";
        byte[] b = x.getBytes();
        
        int i = 15000;
        long j = 1500000000;
        byte b1 = new Integer(i).byteValue();
        byte b2 = new Long(j).byteValue();
        
        System.out.println("llll:" + b.length);
        
    }
    
    public static long getLong(byte[] bb)
    {
        return ((((long) bb[0] & 0xff) << 56) | (((long) bb[1] & 0xff) << 48) | (((long) bb[2] & 0xff) << 40) | (((long) bb[3] & 0xff) << 32) | (((long) bb[4] & 0xff) << 24) | (((long) bb[5] & 0xff) << 16) | (((long) bb[6] & 0xff) << 8) | (((long) bb[7] & 0xff) << 0));
    }
}
