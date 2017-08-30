package com.zxl.util.asyn4j.handler;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

public class AsynRejectedExecutionHandler implements RejectedExecutionHandler
{
    
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor)
    {
        
    }
    
}
