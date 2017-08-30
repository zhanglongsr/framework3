package com.zxl.test.concurrency.example;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class CallableTest
{
    
    /**
     * @param args
     */
    public static void main(String[] args)
    {
        // TODO Auto-generated method stub
        Runnable r = new Runnable()
        {
            
            @Override
            public void run()
            {
                System.out.println("I'm Runnable!");
                
            }
            
        };
        
        Callable<String> c = new Callable<String>()
        {
            
            @Override
            public String call() throws Exception
            {
                try
                {
                    Thread.currentThread().sleep(5000);
                }
                catch(InterruptedException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return "I'm Callable!";
                
            }
            
        };
        
        ThreadPoolService.getInstance().addWork(r);
        Future<String> f = ThreadPoolService.getInstance().addWorkAndCallBack(c);
        if(f != null)
        {
            try
            {
                System.out.println(f.get().toString());
            }
            catch(InterruptedException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            catch(ExecutionException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
        ThreadPoolService.getInstance().shutdown();
        
    }
    
}
