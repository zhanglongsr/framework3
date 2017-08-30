package com.zxl.test.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import org.apache.log4j.Logger;

public class DefaultSocketHandler implements SocketHandler
{
    private static final Logger logger = Logger.getLogger(DefaultSocketHandler.class);
    
    private Socket socket;
    
    private InputStream in;
    
    private OutputStream out;
    
    private boolean closed = false;
    
    public DefaultSocketHandler(Socket socket) throws IOException
    {
        this.socket = socket;
        this.in = socket.getInputStream();
        this.out = socket.getOutputStream();
    }
    
    @Override
    public void run()
    {
        while(!Thread.currentThread().isInterrupted())
        {
            byte[] m = new byte[128];
            
            try
            {
                in.read(m);
                String commond = new String(m);
                if(commond.startsWith("login"))
                {
                    String[] logins = commond.split("\\|")[1].split(":");
                    System.out.println("name:" + logins[0] + ",password:" + logins[1]);
                    
                    String friends = "20001:春天:01,20002:petter:03";
                    byte[] r = friends.getBytes("utf-8");
                    byte[] content = new byte[r.length + 16];
                    Protecol.copy(r, 0, content, 16, r.length);
                    byte[] head = Protecol.genPacketHeader(EnumServeType.RESPONSE, 100, r.length);
                    for(int i = 0; i < head.length; i++)
                    {
                        content[i] = head[i];
                    }
                    logger.info("response packet info: body_length-" + r.length + "、packet_length:" + content.length);
                    out.write(content);
                    out.flush();
                }
                else if(commond.startsWith("exit"))
                {
                    close();
                }
            }
            catch(IOException e)
            {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
        
        try
        {
            close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    
    public void close() throws IOException
    {
        out.close();
        in.close();
        socket.close();
        
        // out = null;
        // in = null;
        // socket = null;
    }
    
    @Override
    public void handle(Socket socket)
    {
        
    }
    
}
