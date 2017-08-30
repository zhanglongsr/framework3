package com.zxl.util.asyn4j.core;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Semaphore;
import com.zxl.util.asyn4j.handler.TaskQueueFullHandler;

public class ApplicationContext
{
    private static final int CPU_NUM = Runtime.getRuntime().availableProcessors();// 运行环境的CPU核数
    
    protected ExecutorService workExecutor = null;
    
    protected ExecutorService callbackExecutor = null;
    
    @SuppressWarnings("rawtypes")
    protected BlockingQueue workQueue = null;
    
    @SuppressWarnings("rawtypes")
    protected BlockingQueue callbackQueue = null;
    
    protected int maxCacheWork = 300;
    
    protected int workThreadNum = CPU_NUM / 2 + 1;
    
    protected int closeServiceWaitTime = 1 * 60 * 1000;
    
    protected long addWorkWaitTime = 100;
    
    protected Semaphore semaphore = null;
    
    protected TaskQueueFullHandler taskQueueFullHandler;
    
    public ApplicationContext()
    {
        
    }
    
    public ApplicationContext(int maxCacheWork, int workThreadNum, int closeServiceWaitTime)
    {
        this.maxCacheWork = maxCacheWork;
        this.workThreadNum = workThreadNum;
        this.closeServiceWaitTime = closeServiceWaitTime;
        semaphore = new Semaphore(maxCacheWork);
    }
    
}
