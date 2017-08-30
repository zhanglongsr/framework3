package com.zxl.test.concurrency.example;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import com.zxl.util.MethodUtil;

public class ThreadPoolService
{
    private ExecutorService service = null;
    
    private ThreadPoolExecutor executor = null;
    
    public static ThreadPoolService instance;
    
    public static synchronized ThreadPoolService getInstance()
    {
        instance = new ThreadPoolService();
        return instance;
    }
    
    private ThreadPoolService()
    {
        service = Executors.newFixedThreadPool(10, Executors.defaultThreadFactory());
    }
    
    /**
     * 加入工作队列，并返回target执行的返回值
     * 
     * @param target
     * @param methodName
     * @param params
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public Object addWork(Object target, String methodName, Object[] params) throws InterruptedException, ExecutionException
    {
        Method method = MethodUtil.getMethod(target.getClass(), methodName, params);
        CallableTaskEntity<Object> task = new CallableTaskEntity<Object>(target, method, params);
        Future<Object> f = service.submit(task);
        
        return f.get();
    }
    
    public void addWorkAndCallBack(Object target, String methodName, Object[] params, TaskReturnResultHandler handler)
    {
        Method method = MethodUtil.getMethod(target.getClass(), methodName, params);
        CallableTaskEntity<Object> task = new CallableTaskEntity<Object>(target, method, params);
        Future<Object> f = service.submit(task);
        if(handler != null)
        {
            
        }
    }
    
    public Future<?> addWork(Runnable r)
    {
        
        return service.submit(r);
    }
    
    public Future<String> addWorkAndCallBack(Callable<String> c)
    {
        return service.submit(c);
    }
    
    public void shutdown()
    {
        service.shutdown();
    }
}
