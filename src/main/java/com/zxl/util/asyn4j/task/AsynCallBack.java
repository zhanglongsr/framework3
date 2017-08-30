package com.zxl.util.asyn4j.task;

import java.io.Serializable;

/**
 * 异步任务的回调处理
 * 
 * @author zhangxl
 * 
 */
public abstract class AsynCallBack implements Runnable, Serializable
{
    /* 任务方法执行结果 */
    protected Object methodResult;
    
    public void run()
    {
        nextProcess();
    }
    
    public final void setMethodResult(Object methodResult)
    {
        this.methodResult = methodResult;
    }
    
    protected Object getMethodResult()
    {
        return methodResult;
    }
    
    /**
     * 对异步任务的处理结果的后续处理动作
     */
    public abstract void nextProcess();
    
}
