package com.zxl.test.net;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import org.apache.log4j.Logger;
import com.zxl.test.net.packet.PacketHeader;

public class ClientSocket
{
    
    private static final Logger logger = Logger.getLogger(ClientSocket.class);
    
    private Socket socket;
    
    static final String IP = "localhost";
    
    static final int PORT = 9977;
    
    private InputStream in;
    
    private OutputStream out;
    
    /**
     * @param args
     */
    public static void main(String[] args)
    {
        try
        {
            ClientSocket cs = new ClientSocket();
            cs.login("james", "123456");
            // cs.login("petter zhangxl jetty", "123456");
            cs.exit();
        }
        catch(UnknownHostException e)
        {
            logger.error("client socket error!", e);
        }
        catch(IOException e)
        {
            logger.error("client socket error!", e);
        }
    }
    
    public ClientSocket() throws UnknownHostException, IOException
    {
        socket = new Socket(IP, PORT);
        socket.setTcpNoDelay(true);// 禁用nagle算法,即关闭套接字buffer,立即发送。产生小的封包
        in = socket.getInputStream();
        out = socket.getOutputStream();
        
    }
    
    public String login(String userName, String password) throws IOException
    {
        String login_str = "login|" + userName + ":" + password;
        byte[] by = login_str.getBytes("utf-8");
        out.write(by);
        
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        byte[] buffer = new byte[64];
        int readBodyLenth = 0;
        int num = in.read(buffer);
        readBodyLenth = readBodyLenth + (num - 16);
        bo.write(buffer, 16, num - 16);
        PacketHeader header = new PacketHeader();
        header.parse(buffer);
        if(num < header.getBodyLength() + 16)
        {
            while((num = in.read(buffer)) != -1)
            {
                bo.write(buffer, 16, num - 16);
                readBodyLenth = readBodyLenth + (num - 16);
                if(readBodyLenth >= header.getBodyLength())
                    break;
            }
        }
        // int available = in.available();
        // bo.write(buffer, 0, available);
        // System.out.println("num:" + num);
        
        // while((num = in.read(buffer)) != -1)
        // {
        // bo.write(buffer, 0, num);
        // }
        
        byte[] login_result = bo.toByteArray();
        String str = new String(login_result, "utf-8");
        System.out.println("login-result:" + str);
        
        return str;
    }
    
    public String exit() throws IOException
    {
        
        String str = "exit";
        byte[] c_s = str.getBytes("utf-8");
        out.write(c_s);
        
        try
        {
            Thread.sleep(1000);
        }
        catch(InterruptedException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        in.close();
        out.close();
        socket.close();
        return "OK";
        
    }
    
}
