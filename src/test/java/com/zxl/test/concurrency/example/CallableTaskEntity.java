package com.zxl.test.concurrency.example;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class CallableTaskEntity<V> implements Callable<V>, Serializable
{
    private static final long serialVersionUID = 6045489162110769169L;
    
    private Object target;
    
    private Method method;
    
    private Object[] params;
    
    public CallableTaskEntity(Object target, Method method, Object[] params)
    {
        this.target = target;
        this.method = method;
        this.params = params;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public V call() throws Exception
    {
        return (V) method.invoke(target, params);
    }
    
}
