package com.zxl.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * IO工具类
 * 
 * @author zhangxl
 * 
 */
public class IOUtil
{
    private static final int DEFAUL_BLOCK_SIZE = 1024;// 默认数据块，1024byte
    
    /**
     * 从一个输入流中，按字节读取流中全部的内容到内存
     * 
     * @param is
     *            输入流
     * @return 输入流中全部的字节
     * @throws IOException
     */
    public static byte[] readAll(InputStream is) throws IOException
    {
        if(is == null)
            return null;
        
        int len = 0;
        byte[] bs = new byte[DEFAUL_BLOCK_SIZE];
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while((len = is.read(bs)) != -1)
        {
            bos.write(bs, 0, len);
        }
        
        byte[] reBytes = bos.toByteArray();
        bos.close();
        return reBytes;
    }
}
