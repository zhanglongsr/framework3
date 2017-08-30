package com.zxl.util.asyn4j.task;

import java.io.Serializable;
import org.apache.log4j.Logger;
import com.zxl.util.asyn4j.core.ApplicationContext;

public class RunnableAsynTaskProcessor implements Runnable, Serializable
{
    
    private static final long serialVersionUID = -3763361223389558709L;
    
    private static final Logger logger = Logger.getLogger(RunnableAsynTaskProcessor.class);
    
    private AsynTask asynTask;
    
    private ApplicationContext context;
    
    public RunnableAsynTaskProcessor(AsynTask asynTask, ApplicationContext context)
    {
        this.asynTask = asynTask;
        this.context = context;
    }
    
    public void run()
    {
        AsynCallBack callback = null;
        try
        {
            callback = asynTask.call();
        }
        catch(Exception e)
        {
            logger.error("execute task[" + asynTask.getTaskName() + "] error!", e);
            return;
        }
        
        if(callback != null)
        {
            // TODO 加入到callback任务处理线程池
        }
    }
    
    public AsynTask getAsynTask()
    {
        return asynTask;
    }
    
}
