package com.zxl.test.io;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import org.junit.Test;
import com.zxl.util.RunTimeCalculator;

public class IOPerformanceTest
{
    private String SOURCE_DIR = "e:/data/testdata/";
    
    // 加速I/O访问的原则-----
    // 避免访问磁盘。
    // 避免访问底层操作系统。
    // 避免方法调用。
    // 避免单独处理字节和字符。
    @Test
    public void readTest()
    {
        RunTimeCalculator timeCalculator = new RunTimeCalculator();
        
        try
        {
            // timeCalculator.startTiming();
            // readByByte();
            // System.out.println(timeCalculator.stop());
            
            // timeCalculator.startTiming();
            // this.readByLargeBuffer();
            // System.out.println(timeCalculator.stop());
            
            timeCalculator.startTiming();
            this.readByDirectBuffer();
            System.out.println(timeCalculator.stop());
        }
        catch(IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    /**
     * 以字节的方式读取
     * 
     * @throws IOException
     */
    private void readByByte() throws IOException
    {
        int cnt = 0;
        String filepath = SOURCE_DIR + "f1.pdf";
        
        FileInputStream is = new FileInputStream(filepath);
        while(is.read() != -1)
        {
            cnt++;
        }
        System.out.println("cnt1:" + cnt);
        is.close();
    }
    
    /**
     * 以large buffer的方式读取
     * 
     * @throws IOException
     */
    private void readByLargeBuffer() throws IOException
    {
        int cnt = 0;
        String filepath = SOURCE_DIR + "f2.pdf";
        
        FileInputStream is = new FileInputStream(filepath);
        BufferedInputStream bis = new BufferedInputStream(is);
        int b = 0;
        while((b = bis.read()) != -1)
        {
            cnt++;
        }
        System.out.println("cnt2:" + cnt);
        bis.close();
    }
    
    /**
     * 直接缓冲读取
     * 
     * @throws IOException
     */
    private void readByDirectBuffer() throws IOException
    {
        int cnt = 0;
        String filepath = SOURCE_DIR + "f3.pdf";
        
        FileInputStream is = new FileInputStream(filepath);
        byte[] buffer = new byte[2048];
        int n = 0;
        while((n = is.read(buffer)) != -1)
        {
            for(int i = 0; i < buffer.length; i++)
            {
                if(buffer[i] != '\n')
                {
                    cnt++;
                }
            }
        }
        
        System.out.println("cnt3:" + cnt);
        is.close();
    }
}
