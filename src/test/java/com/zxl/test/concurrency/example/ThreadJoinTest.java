package com.zxl.test.concurrency.example;

//总结：
//join，一个线程等待另外一个线程结束,a.join，代表另外一个线程需要等待a运行结束，才能执行后面的代码
public class ThreadJoinTest
{
    public static void main(String... strings)
    {
        Runnable r = new Runnable()
        {
            
            @Override
            public void run()
            {
                try
                {
                    Thread.sleep(5000);
                }
                catch(InterruptedException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                System.out.println("I'm running!");
                
            }
            
        };
        
        Thread t = new Thread(r);
        t.start();
        try
        {
            t.join();
        }
        catch(InterruptedException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        System.out.println("finished");
    }
}
