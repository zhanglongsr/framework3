package com.zxl.test.concurrency.example;

public class NoVisibility
{
    private static boolean ready;
    
    private static int number;
    
    /**
     * @param args
     */
    public static void main(String[] args)
    {
        new ReaderThread().start();
        // try
        // {
        // Thread.sleep(10);
        // }
        // catch(InterruptedException e)
        // {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
        number = 42;
        ready = true;
    }
    
    public static class ReaderThread extends Thread
    {
        public void run()
        {
            while(!ready)
            {
                System.out.println("--yield");
                Thread.yield();// yield方法，能够把当前线程由running->runnable.
            }
            
            System.out.println(number);
        }
    }
    
}
