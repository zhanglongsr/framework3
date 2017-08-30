package com.zxl.test.common.pool;

import org.apache.commons.pool.BasePoolableObjectFactory;

public class FtpClientPoolableFactory extends BasePoolableObjectFactory
{
    private String host;
    
    private int port;
    
    private String user;
    
    private String password;
    
    public FtpClientPoolableFactory(String host, int port, String user, String password)
    {
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
    }
    
    @Override
    public Object makeObject() throws Exception
    {
        final FtpClient ftpClient = new FtpClient(host, port, user, password);
        ftpClient.connect();
        
        return ftpClient;
    }
    
    public void destroyObject(Object obj) throws Exception
    {
        if(obj instanceof FtpClient)
        {
            final FtpClient ftpClient = (FtpClient) obj;
            ftpClient.disconnect();
        }
    }
    
    public boolean validateObject(final Object obj)
    {
        if(obj instanceof FtpClient)
        {
            final FtpClient ftpClient = (FtpClient) obj;
            return ftpClient.isconnected();
        }
        else
        {
            return false;
        }
    }
    
}
