package com.zxl.framework.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import com.zxl.util.PropertyUtil;

public class ConnectionFactory
{
    private static ConnectionFactory factory;
    private Map<String, JDBC> pool = null;
    
    public static synchronized ConnectionFactory getInstance()
    {
        
        if(factory == null)
        {
            factory = new ConnectionFactory();
        }
        
        return factory;
    }
    
    public ConnectionFactory()
    {
        pool = new HashMap<String, JDBC>();
    }
    
    public Connection getConnection()
    {
        
        Connection connection = null;
        String dbConfig = "db.properties";
        JDBC jdbc = pool.get(dbConfig);
        if(jdbc == null)
        {
            jdbc = new JDBC();
            jdbc.ds_name = PropertyUtil.getInstance().get(dbConfig, "DS_NAME");
            jdbc.db_type = PropertyUtil.getInstance().get(dbConfig, "DB_TYPE");
            jdbc.jdbc_url = PropertyUtil.getInstance().get(dbConfig, "JDBC_URL");
            jdbc.jdbc_user = PropertyUtil.getInstance().get(dbConfig, "JDBC_USER");
            jdbc.jdbc_pass = PropertyUtil.getInstance().get(dbConfig, "JDBC_PASS");
            jdbc.jdbc_driver = PropertyUtil.getInstance().get(dbConfig, "JDBC_DRIVER");
            pool.put(dbConfig, jdbc);
        }
        
        try
        {
            connection = this.getConnection(jdbc);
        }
        catch(Throwable e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return connection;
        
    }
    
    private Connection getConnection(JDBC jdbc) throws Throwable
    {
        InitialContext ctx;
        try
        {
            ctx = new InitialContext();
            
            DataSource ds = (DataSource) ctx.lookup(jdbc.ds_name);
            return ds.getConnection();
        }
        catch(Throwable e1)
        {
            try
            {
                Class.forName(jdbc.jdbc_driver);
                Connection conn = DriverManager.getConnection(jdbc.jdbc_url, jdbc.jdbc_user, jdbc.jdbc_pass);
                return conn;
            }
            catch(Throwable e2)
            {
                throw e2;
            }
        }
    }
    
    class JDBC
    {
        public String ds_name;
        public String jdbc_url;
        public String jdbc_user;
        public String jdbc_pass;
        public String jdbc_driver;
        public String db_type;
    }
    
}
