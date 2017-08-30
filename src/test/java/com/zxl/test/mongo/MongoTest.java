package com.zxl.test.mongo;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import com.mongodb.MongoException;
import com.zxl.mongo.EnumReadStrategy;
import com.zxl.mongo.IMongoClient;
import com.zxl.mongo.MongoClientFactory;

public class MongoTest
{
    private String host = "10.10.10.18";
    private String port = "15525";
    private String database = "test";
    
    IMongoClient mongoClient = null;
    
    @Before
    public void init()
    {
        String uri = "mongodb://10.10.10.18:18001,10.10.10.18:18002,10.10.10.18:18003/blog?maxpoolsize=128;waitqueuetimeoutms=5000;connecttimeoutms=2000;sockettimeoutms=10000;autoconnectretry=true";
        try
        {
            mongoClient = MongoClientFactory.newInstance(uri, EnumReadStrategy.Secondary);
        }
        catch(MongoException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch(UnknownHostException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    @Test
    public void saveBlog()
    {
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("author", "hala");
        map.put("article_name", "java并发实践");
        map.put("article_content", "是老大减肥了撒分了撒地方拉萨发了撒的弗利萨发了倒萨发的撒拉弗倒萨啦发了的撒发的撒拉弗倒萨发了的撒范德萨了发了的撒飞的萨芬的撒拉弗了倒萨发了的撒发了的撒发了的撒发了的撒");
        
        mongoClient.insert("article", map);
    }
    
    // @Test
    public void testSaveHugeFile()
    {
        String filePath = "F:\\pic\\898.jpg";
        IMongoClient mc = null;
        try
        {
            mc = MongoClientFactory.newInstance(host, port, database);
            byte[] contents = this.getFileContent(filePath);
            mc.createHugeFile(null, contents, "11111.jpg", null);
        }
        catch(MongoException e)
        {
            e.printStackTrace();
        }
        catch(UnknownHostException e)
        {
            e.printStackTrace();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        
        // try
        // {
        // byte[] x = this.getFileContent(file);
        // String file2 = "F:\\pic\\899.jpg";
        // this.writeFile(file2, x);
        // }
        // catch(IOException e)
        // {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
        
    }
    
    private byte[] getFileContent(String filePath) throws IOException
    {
        
        FileInputStream input = new FileInputStream(filePath);
        byte[] bs = new byte[1024];
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int length = 0;
        while((length = input.read(bs)) != -1)
        {
            if(length != 1024)
            {
                System.out.println("到尾巴了");
            }
            bos.write(bs, 0, length);
        }
        
        byte[] n = bos.toByteArray();
        input.close();
        bos.close();
        
        return n;
        
    }
    
    private void writeFile(String filepath, byte[] bs) throws IOException
    {
        FileOutputStream fos = new FileOutputStream(filepath);
        fos.write(bs);
        fos.close();
    }
}
