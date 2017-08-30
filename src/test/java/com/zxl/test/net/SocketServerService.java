package com.zxl.test.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.log4j.Logger;

public class SocketServerService
{
    private static final Logger logger = Logger.getLogger(SocketServerService.class);
    
    private ServerSocket s = null;
    
    private final ExecutorService service = Executors.newFixedThreadPool(10);
    
    public static void main(String... s)
    {
        SocketServerService service = new SocketServerService();
        try
        {
            service.start();
        }
        catch(IOException e)
        {
            logger.error("socket service failed to start!", e);
        }
    }
    
    public SocketServerService()
    {
        
    }
    
    public void start() throws IOException
    {
        s = new ServerSocket(9977, 2);// backlog:
        
        // s.setSoTimeout(3000);//
        Socket socket = s.accept();
        
        SocketHandler handler = new DefaultSocketHandler(socket);
        service.execute(handler);
        
    }
    
    // 关于backlog
    // backlog参数用来显式设置连结请求队列的长度，它将覆盖操作系统限定的队列的最大长度。值得注意的是，在以下几种情况，仍然会采用操作系统限定的队列的最大长度：
    // l backlog参数的值大于操作系统限定的队列的最大长度。
    // l backlog参数的值小于或等于0。
    
    public void close()
    {
        service.shutdown();
        if(s != null && !s.isClosed())
        {
            try
            {
                s.close();
                logger.info("service socket has successly closed!");
            }
            catch(IOException e)
            {
                logger.error("service socket failed to close", e);
            }
        }
    }
    
}
