package com.zxl.util.asyn4j.task;

import java.lang.reflect.Method;
import org.apache.log4j.Logger;
import com.zxl.util.MethodUtil;
import com.zxl.util.StringUtil;

/**
 * 异步任务实体
 */
public class AsynTaskEntity implements AsynTask, java.io.Serializable
{
    private static final long serialVersionUID = -2319588861833534892L;
    private static final Logger logger = Logger.getLogger(AsynTaskEntity.class);
    private Object target;
    
    private Object[] params;
    
    private String methodName;
    
    private String taskName;
    
    private AsynCallBack callback;
    
    private EnumTaskType taskType = EnumTaskType.RUNNABLE;
    
    public AsynTaskEntity(Object target, String methodName, Object[] params, AsynCallBack callback)
    {
        this.target = target;
        this.methodName = methodName;
        this.params = params;
        
    }
    
    public AsynTaskEntity(Object target, String methodName, Object[] params, AsynCallBack callback, String taskName)
    {
        this.target = target;
        this.methodName = methodName;
        this.params = params;
        this.taskName = taskName;
        if(StringUtil.isBlank(this.taskName))
        {
            this.taskName = target.getClass().getSimpleName();
        }
        this.callback = callback;
    }
    
    @Override
    public String getTaskName()
    {
        return this.taskName;
    }
    
    @Override
    public AsynCallBack call() throws Exception
    {
        Method method = MethodUtil.getMethod(target.getClass(), methodName, params);
        try
        {
            Object result = method.invoke(target, params);
            if(callback != null)
                callback.setMethodResult(result);
        }
        catch(Exception e)
        {
            logger.error("invoking task-" + taskName + " errored!", e);
            throw e;
        }
        
        return callback;
    }
    
    @Override
    public EnumTaskType getTaskType()
    {
        return taskType;
    }
    
    @Override
    public void setTaskType(EnumTaskType taskType)
    {
        this.taskType = taskType;
    }
    
}
