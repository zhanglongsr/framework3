package com.zxl.util.asyn4j;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;
import com.zxl.util.StringUtil;
import com.zxl.util.asyn4j.core.ApplicationContext;
import com.zxl.util.asyn4j.task.AsynCallBack;
import com.zxl.util.asyn4j.task.AsynTask;
import com.zxl.util.asyn4j.task.AsynTaskEntity;
import com.zxl.util.asyn4j.task.RunnableAsynTaskProcessor;

public class AsynService extends ApplicationContext
{
    private static final Logger logger = Logger.getLogger(AsynService.class);
    
    public AsynService(int maxCacheWork, int workThreadNum, long keepAliveTime, long addWorkWaitTime)
    {
        
    }
    
    public void addTask(Object target, String method)
    {
        
    }
    
    public void addTask(Object target, String method, Object[] params)
    {
        
    }
    
    public void addTask(Object target, String method, Object[] params, AsynCallBack callback)
    {
        if(target == null || StringUtil.isBlank(method))
        {
            throw new IllegalArgumentException("task target is null,or target's method not exist!");
        }
        
        AsynTask asynTask = new AsynTaskEntity(target, method, params, callback);
        
        try
        {
            if(semaphore.tryAcquire(super.addWorkWaitTime, TimeUnit.MILLISECONDS))
            {
                RunnableAsynTaskProcessor taskProcessor = new RunnableAsynTaskProcessor(asynTask, this);
                super.workExecutor.execute(taskProcessor);
            }
            else
            {
                logger.warn("work queue is already full!");
                // TODO 处理满载
            }
            
        }
        catch(InterruptedException e)
        {
            logger.error("task[" + asynTask.getTaskName() + "] has been Interrupted when waiting to add to work queue!", e);
        }
        
    }
    
    /**
     * 
     * @param target
     * @param method
     * @return
     */
    public Future<?> addTaskAndReturn(Object target, String method)
    {
        return null;
    }
    
    /**
     * 
     * @param target
     * @param method
     * @param params
     * @return
     */
    public Future<?> addTaskAndReturn(Object target, String method, Object[] params)
    {
        return null;
    }
    
}
