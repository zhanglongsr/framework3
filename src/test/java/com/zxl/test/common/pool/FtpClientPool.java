package com.zxl.test.common.pool;

import org.apache.commons.pool.PoolableObjectFactory;
import org.apache.commons.pool.impl.GenericObjectPool;

public class FtpClientPool<T>
{
    private final GenericObjectPool internalPool;
    
    public FtpClientPool(GenericObjectPool.Config config, PoolableObjectFactory factory)
    {
        internalPool = new GenericObjectPool(factory, config);
    }
    
    @SuppressWarnings("unchecked")
    public T getResource() throws Exception
    {
        return (T) internalPool.borrowObject();
    }
    
    public void returnResourceObject(Object obj) throws Exception
    {
        internalPool.returnObject(obj);
    }
}
