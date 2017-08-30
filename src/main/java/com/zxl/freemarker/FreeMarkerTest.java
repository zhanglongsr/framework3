package com.zxl.freemarker;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import org.junit.Test;
import freemarker.template.TemplateException;

public class FreeMarkerTest
{
    
    @Test
    public void testSelfDirective()
    {
        
        Map<String, String> map = System.getenv();
        for(Map.Entry<String, String> entry : map.entrySet())
        {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
        
        System.out.println("----------------------------------");
        
        Properties ps = System.getProperties();
        for(Entry<Object, Object> entry : ps.entrySet())
        {
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            System.out.println(key + " - " + value);
        }
        
        String content = null;
        Map<String, Object> data = new HashMap<String, Object>();
        try
        {
            content = FreemarkerUtil.outputContent("form.ftl", "UTF-8", data);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        catch(TemplateException e)
        {
            e.printStackTrace();
        }
        
        System.out.println(content);
    }
}
