package com.zxl.test.concurrency.example;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledRunServcie
{
    
    private static final ScheduledExecutorService service = Executors.newScheduledThreadPool(10);
    
    public static void timeRun(final Runnable r, long timeout, TimeUnit tu) throws InterruptedException
    {
        class RethrowableTask implements Runnable
        {
            volatile Throwable t = null;
            
            @Override
            public void run()
            {
                
                try
                {
                    r.run();
                }
                catch(Throwable e)
                {
                    t = e;
                }
            }
            
            public void rethrow()
            {
                if(t != null)
                    throw launderThrow();
            }
            
            private RuntimeException launderThrow()
            {
                return new RuntimeException("出错了", t);
            }
            
        }
        
        RethrowableTask task = new RethrowableTask();
        final Thread taskThread = new Thread(task);
        taskThread.start();
        service.schedule(new Runnable()
        {
            
            @Override
            public void run()
            {
                taskThread.interrupt();
            }
        }, timeout, tu);
        
        taskThread.join(timeout);
        task.rethrow();
    }
    
    public static void main(String... strings)
    {
        Runnable r = new Runnable()
        {
            
            @Override
            public void run()
            {
                System.out.println("task start!");
                while(!Thread.currentThread().isInterrupted())
                {
                    try
                    {
                        Thread.sleep(1000);
                    }
                    catch(InterruptedException e)
                    {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        System.out.println("task has been interrupted!");
                        Thread.currentThread().interrupt();
                    }
                }
                
                System.out.println("task end!");
            }
            
        };
        
        try
        {
            ScheduledRunServcie.timeRun(r, 5000, TimeUnit.MILLISECONDS);
        }
        catch(InterruptedException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
