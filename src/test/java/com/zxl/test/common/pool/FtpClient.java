package com.zxl.test.common.pool;

public class FtpClient implements FtpCommond
{
    private String host;
    
    private int port;
    
    private String user;
    
    private String password;
    
    public FtpClient(String host, int port, String user, String password)
    {
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
    }
    
    public String getHost()
    {
        return host;
    }
    
    public void setHost(String host)
    {
        this.host = host;
    }
    
    public int getPort()
    {
        return port;
    }
    
    public void setPort(int port)
    {
        this.port = port;
    }
    
    public String getUser()
    {
        return user;
    }
    
    public void setUser(String user)
    {
        this.user = user;
    }
    
    public String getPassword()
    {
        return password;
    }
    
    public void setPassword(String password)
    {
        this.password = password;
    }
    
    @Override
    public int connect()
    {
        return 0;
    }
    
    @Override
    public int disconnect()
    {
        return 0;
    }
    
    @Override
    public boolean isconnected()
    {
        // TODO Auto-generated method stub
        return true;
    }
    
    public String toString()
    {
        return "ftp://" + this.user + ":" + this.password + "@" + this.host;
    }
    
}
