package com.zxl.app.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import com.zxl.app.entity.Web_link;
import com.zxl.app.persistence.WebLinkMapper;
import com.zxl.framework.mybatis.SqlSessionFactoryBean;

public class WebLinkServiceImpl
{
    
    @SuppressWarnings("unchecked")
    public void testGetObject()
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("link_our_href", "http://a.lietou.com/home/");
        SqlSession session = null;
        try
        {
            session = SqlSessionFactoryBean.openSqlSession();
            List<Web_link> list = session.selectList("Linkx.selectWebLink", map);
            if(list != null)
            {
                System.out.println(list.size());
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(session != null)
            {
                session.close();
                session = null;
            }
            
        }
        
    }
    
    public void testGetOneByMapper()
    {
        SqlSession session = null;
        try
        {
            session = this.getSqlSession();
            Web_link link = (Web_link) session.selectOne("com.zxl.app.persistence.WebLink.selectWebLink", new Integer(10));
            if(link != null)
            {
                System.out.println(link.getLink_createtime());
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(session != null)
            {
                session.close();
                session = null;
            }
        }
    }
    
    /**
     * 测试SQl_Mapper
     */
    @Test
    public void testGetMapper()
    {
        SqlSession session = null;
        try
        {
            session = this.getSqlSession();
            WebLinkMapper mapper = session.getMapper(WebLinkMapper.class);
            Web_link link = mapper.getWeblinkById(new Integer(10));
            if(link != null)
            {
                System.out.println(link.getLink_our_href());
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(session != null)
            {
                session.close();
                session = null;
            }
        }
    }
    
    private SqlSession getSqlSession()
    {
        return SqlSessionFactoryBean.openSqlSession();
    }
    
}
