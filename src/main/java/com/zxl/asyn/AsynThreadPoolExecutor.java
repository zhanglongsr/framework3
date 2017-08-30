package com.zxl.asyn;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class AsynThreadPoolExecutor extends ThreadPoolExecutor
{
    private AtomicLong execWorkNum = new AtomicLong(0);// 记录处理的任务数量
    
    public AsynThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler)
    {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
    }
    
    protected void afterExecute(Runnable r, Throwable t)
    {
        super.afterExecute(r, t);
    }
    
    protected void beforeExecute(Runnable r, Thread t)
    {
        super.beforeExecute(t, r);
        execWorkNum.incrementAndGet();
    }
    
    /**
     * 获得执行的任务数量
     * 
     * @return
     */
    public long getExecWorkNum()
    {
        return execWorkNum.longValue();
    }
    
}
