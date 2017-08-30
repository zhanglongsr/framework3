package com.zxl.test.concurrency.example;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import org.junit.Ignore;
import org.junit.Test;

public class ThreadPoolTest
{
    @Test
    @Ignore
    // 任务的提交与任务的执行在同一线程中的测试
    public void testExecutorForCurrentThread()
    {
        MyExecutorForCurrentThread efct = new MyExecutorForCurrentThread();
        Runnable r = new Runnable()
        {
            @Override
            public void run()
            {
                System.out.println("run in current tread!");
            }
        };
        efct.execute(r);
        
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void testRunableAndCallable()
    {
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
                return "I'm Callable!";
            }
            
        };
        
        Future<Void> f_run = (Future<Void>) ThreadPoolService.getInstance().addWork(r);
        Future<String> f_call = ThreadPoolService.getInstance().addWorkAndCallBack(c);
        
        try
        {
            System.out.println("f_run:" + (f_run.get() == null ? null : f_run.get().toString()));
            System.out.println("f_call:" + f_call.get());
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
    
}
