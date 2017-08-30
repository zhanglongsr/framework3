package com.zxl.util.asyn4j.task;

public interface AsynTask extends java.io.Serializable
{
    /**
     * 获得任务名称
     * 
     * @return
     */
    public String getTaskName();
    
    /**
     * 调用执行任务
     */
    public AsynCallBack call() throws Exception;
    
    /**
     * 获得异步任务类型
     * 
     * @return
     */
    public EnumTaskType getTaskType();
    
    public void setTaskType(EnumTaskType taskType);
    
}
