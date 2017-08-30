package com.zxl.test.common.pool;

/**
 * ftp命令接口
 * 
 * @author zhangxl
 * 
 */
public interface FtpCommond
{
    // 连接
    public int connect() throws java.net.UnknownHostException;
    
    // 取消连接
    public int disconnect();
    
    public boolean isconnected();
    
}
