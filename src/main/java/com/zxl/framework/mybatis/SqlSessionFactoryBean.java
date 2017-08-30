package com.zxl.framework.mybatis;

import java.io.IOException;
import java.io.Reader;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class SqlSessionFactoryBean
{
    private static String resource = "db_config.xml";
    
    private static SqlSessionFactory sqlSessionFactory = null;
    
    static
    {
        String evironment_id = "lietou";
        Reader reader = null;
        try
        {
            reader = Resources.getResourceAsReader(resource);
            SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
            sqlSessionFactory = builder.build(reader, evironment_id);
            if(sqlSessionFactory.getConfiguration().getEnvironment() == null)
            {
                System.out.println("sessionFactory load failed!");
            }
        }
        catch(IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
    
    public static SqlSessionFactory getSqlSessionFactory()
    {
        return sqlSessionFactory;
    }
    
    public static SqlSession openSqlSession()
    {
        return sqlSessionFactory.openSession();
    }
    
    public static void main(String[] str)
    {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.getSqlSessionFactory();
    }
    
}
