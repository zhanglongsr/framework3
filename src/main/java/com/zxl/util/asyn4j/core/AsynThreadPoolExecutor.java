package com.zxl.util.asyn4j.core;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.log4j.Logger;

public class AsynThreadPoolExecutor extends ThreadPoolExecutor
{
    private static final Logger logger = Logger.getLogger(AsynThreadPoolExecutor.class);
    private AtomicLong executeTaskNum = new AtomicLong(0);// 执行的任务数量
    
    public AsynThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler)
    {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }
    
    public AsynThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory)
    {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }
    
    protected void beforeExecute(Thread t, Runnable r)
    {
        super.beforeExecute(t, r);
        executeTaskNum.incrementAndGet();
    }
    
    protected void afterExecute(Runnable r, Throwable t)
    {
        super.afterExecute(r, t);
    }
}
