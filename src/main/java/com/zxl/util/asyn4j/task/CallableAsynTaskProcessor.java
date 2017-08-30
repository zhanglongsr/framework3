package com.zxl.util.asyn4j.task;

import java.io.Serializable;
import java.util.concurrent.Callable;

public class CallableAsynTaskProcessor implements Callable<Object>, Serializable
{
    
    private static final long serialVersionUID = 1734307197496540305L;
    
    private AsynTask asynTask;
    
    @Override
    public Object call() throws Exception
    {
        AsynCallBack callback = asynTask.call();
        return callback == null ? null : callback.getMethodResult();
    }
    
}
