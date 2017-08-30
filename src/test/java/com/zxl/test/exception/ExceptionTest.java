package com.zxl.test.exception;

public class ExceptionTest
{
    
    public void test1() throws Exception
    {
        Integer i = new Integer(-3);
        try
        {
            this.getValue(i);
        }
        catch(Exception e)
        {
            throw e;
        }
        finally
        {
            System.out.print("it's errored!");
        }
        
    }
    
    public Integer getValue(Integer x) throws Exception
    {
        
        if(x.intValue() <= 0)
            throw new Exception("error");
        
        return x + 1;
    }
    
    public static void main(String... strings)
    {
        ExceptionTest t = new ExceptionTest();
        try
        {
            t.test1();
        }
        catch(Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
