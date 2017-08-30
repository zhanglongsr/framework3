package com.zxl.test.httpclient;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;

public class TestHttpClient extends TestCase
{
    
    @Before
    protected void setUp() throws Exception
    {
        super.setUp();
    }
    
    @After
    protected void tearDown() throws Exception
    {
        super.tearDown();
    }
    
    // @Test
    // public void testHttpGet()
    // {
    // HttpClient client = new DefaultHttpClient();
    //
    // HttpContext context = new BasicHttpContext();
    //
    // HttpGet get = new HttpGet("http://www.baidu.com");
    // HttpResponse response = null;
    // try
    // {
    // response = client.execute(get, context);
    //
    // HttpConnection conn = (HttpConnection) context.getAttribute(ExecutionContext.HTTP_CONNECTION);
    // long responsesize = conn.getMetrics().getResponseCount();
    // System.out.println("/***--  socketTimeOut:" + conn.getSocketTimeout() + "   ---*/");
    //
    // System.out.println("/***-- response size: " + responsesize + "   ---*/");
    //
    // HttpEntity entity = response.getEntity();
    // if(entity != null)
    // {
    // InputStream stream = entity.getContent();
    // // InputStreamReader reader = new InputStreamReader(stream,"gb2312");
    // // BufferedReader breader = new BufferedReader(reader);
    //
    // StringBuffer sb = new StringBuffer();
    // byte[] buffer = new byte[1024];
    // int l = 0;
    // while((l = stream.read(buffer)) != -1)
    // {
    // sb.append(new String(buffer, "gb2312"));
    // }
    //
    // System.out.println(sb.toString());
    // }
    //
    // HttpHost host = (HttpHost) context.getAttribute(ExecutionContext.HTTP_TARGET_HOST);
    // System.out.println("/***--  " + host + "   ---*/");
    //
    // }
    // catch(ClientProtocolException e)
    // {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    // catch(IOException e)
    // {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    // finally
    // {
    //
    // }
    // }
    
}
