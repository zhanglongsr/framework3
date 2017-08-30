package com.zxl.test.common.pool;

import org.apache.commons.pool.PoolableObjectFactory;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.junit.Test;

public class CommonPoolTest
{
    private PoolableObjectFactory factory = new FtpClientPoolableFactory("192.168.1.126", 6680, "zhangxl", "1qaz2wsx");
    private final FtpClientPool<FtpClient> pool = new FtpClientPool<FtpClient>(new GenericObjectPool.Config(), factory);
    
    @Test
    public void test1()
    {
        FtpClient ftpClient = null;
        try
        {
            ftpClient = pool.getResource();
            System.out.println(ftpClient.toString());
        }
        catch(Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
