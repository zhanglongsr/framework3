package com.zxl.test.concurrency.example;

//总结：
//1.Thread的yield方法把当前线程由running->runnable状态，os会把当前线程加入到就绪线程队列中，等待下一次执行
//2.yield的线程不释放其占有的锁

//关于线程调度 :
//线程调度有两种模型：分时调度模型和抢占式调度模型(http://baike.baidu.com/view/1336364.htm),jvm采用抢占式线程调度模型。

public class ThreadYieldTest extends Thread
{
    Hints h = null;
    
    public ThreadYieldTest(String name, Hints h)
    {
        super(name);
        this.h = h;
    }
    
    public void run()
    {
        // for(int i = 0; i < 4; i++)
        // {
        // System.out.println(Thread.currentThread().getName());
        // System.out.println(" : " + i);
        // Thread.yield();
        // }
        
        h.count(100);
        
    }
    
    public static void main(String... arg)
    {
        Hints h = new Hints();
        
        ThreadYieldTest t1 = new ThreadYieldTest("t1", h);
        ThreadYieldTest t2 = new ThreadYieldTest("t2", h);
        t1.start();
        // // t1.start();
        t2.start();
    }
    
    public static class Hints
    {
        private int hints = 0;
        
        public Hints()
        {
            
        }
        
        public synchronized void count(int num)
        {
            
            for(int i = 0; i < num; i++)
            {
                hints++;
                System.out.println(Thread.currentThread().getName() + " : " + i);
                Thread.yield();
            }
            
        }
    }
    
}
