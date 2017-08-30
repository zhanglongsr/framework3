package com.zxl.test.base;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.zxl.util.ByteUtil;

public class DataTypeTest
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
    public void testByte()
    {
        // byte[-128,127],如果以十进制指定byte的值，强制转化int,打印的值为原始值;
        byte x = -110;
        int y = (int) x;
        System.out.println(y);
        
        byte x1 = 0;
        for(int i = 0; i < 256; i++)
        {
            System.out.println(x1++);
            System.out.println((int) x1);
        }
        
        byte[] bytes = {-123, -28, 106, -18};
        int intVal = ByteUtil.getInt(bytes);
        long longVal = ByteUtil.getLong(bytes);
        System.out.println(intVal);
        System.out.println();
        
    }
}
