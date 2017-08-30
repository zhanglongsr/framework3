package com.zxl.format;

import java.text.MessageFormat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MessageFormatTest
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
        this.info("the zhangxl: name is -{0},family -{1}", "zhangxl", "huoshishan");
    }
    
    public void info(String pattern, Object... arguments)
    {
        String message = MessageFormat.format(pattern, arguments);
        System.out.print(message);
    }
    
}
