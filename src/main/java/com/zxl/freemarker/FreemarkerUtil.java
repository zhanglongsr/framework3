package com.zxl.freemarker;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;
import org.apache.log4j.Logger;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FreemarkerUtil
{
    private static final Logger logger = Logger.getLogger(FreemarkerUtil.class);
    
    private static final Configuration cfg = new Configuration();
    
    static
    {
        cfg.setClassForTemplateLoading(FreemarkerUtil.class, "/template");
        cfg.setObjectWrapper(new DefaultObjectWrapper());
    }
    
    /**
     * 获得模板
     * 
     * @param templateName
     * @param encoding
     * @return
     * @throws IOException
     */
    public static Template getTemplate(String templateName, String encoding) throws IOException
    {
        try
        {
            return cfg.getTemplate(templateName, encoding);
        }
        catch(IOException e)
        {
            logger.error("it's  tfailedo load Template! templateName:" + templateName + ",encoding:" + encoding + ",message:" + e.getMessage(), e);
            throw e;
        }
    }
    
    /**
     * 输出模板+数据后形成的内容
     * 
     * @param templateName
     *            模板名称
     * @param encoding
     *            编码
     * @param data
     *            数据
     * @return
     * @throws IOException
     * @throws TemplateException
     */
    public static String outputContent(String templateName, String encoding, Map<String, Object> data) throws IOException, TemplateException
    {
        String templateContent = "";
        StringWriter writer = new StringWriter();
        Template template = getTemplate(templateName, encoding);
        try
        {
            template.process(data, writer);
            templateContent = writer.toString();
        }
        catch(TemplateException e)
        {
            logger.error("it's errored to output template content!templateName:" + templateName + ",encoding:" + encoding + ",message:" + e.getMessage(), e);
            throw e;
        }
        catch(IOException e)
        {
            logger.error("it's errored to output template content!templateName:" + templateName + ",encoding:" + encoding + ",message:" + e.getMessage(), e);
            throw e;
        }
        finally
        {
            try
            {
                writer.close();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
        
        return templateContent;
    }
}
