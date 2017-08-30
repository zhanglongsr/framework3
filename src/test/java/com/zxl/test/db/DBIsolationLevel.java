package com.zxl.test.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBIsolationLevel
{
    
    private String driverClassName = "com.mysql.jdbc.Driver";
    
    private String url = "jdbc:mysql://10.10.10.62:21002/h?useUnicode=true&characterEncoding=gbk";
    
    private String user = "root";
    
    private String password = "@WSX#EDC6yhn";
    
    /**
     * @param args
     */
    public static void main(String[] args)
    {
        DBIsolationLevel t = new DBIsolationLevel();
        t.test();
        
    }
    
    // 通过验证可以得知:
    // 1.mysql 5.1的默认事务隔离级别为 TRANSACTION_READ_COMMITTED。
    // 脏读：
    public void test()
    {
        Connection c1 = null;
        Connection c2 = null;
        Connection c3 = null;
        
        try
        {
            c1 = this.getConnection();
            c1.setAutoCommit(false);
            c1.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            logDatabaseMeta(c1);
            c2 = this.getConnection();
            c2.setAutoCommit(false);
            c2.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            
            c3 = this.getConnection();
            Statement stmt = c1.createStatement();
            c3.setAutoCommit(false);
            c3.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            
            ResultSet rs1 = stmt.executeQuery("select * from user_age where age>20");
            while(rs1.next())
            {
                System.out.println(rs1.getString("user_name") + "-" + rs1.getInt("age"));
            }
            rs1.close();
            // c1.commit();
            
            Statement stmt_update = c3.createStatement();
            stmt_update.executeUpdate("update user_age set user_name='newsss' where user_id=1");
            c3.commit();
            
            Statement stmt_insert = c2.createStatement();
            stmt_insert.executeUpdate("insert into user_age(user_id,user_name,age)values(5,'laoer',25)");
            c2.commit();
            
            Statement stmt2 = c1.createStatement();
            ResultSet rs2 = stmt2.executeQuery("select * from user_age where age>20");
            while(rs2.next())
            {
                System.out.println(rs2.getString("user_name") + "-" + rs2.getInt("age"));
            }
            rs2.close();
            c1.commit();
        }
        catch(ClassNotFoundException e)
        {
            
            e.printStackTrace();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(c1 != null)
            {
                try
                {
                    c1.close();
                }
                catch(SQLException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if(c2 != null)
            {
                try
                {
                    c2.close();
                }
                catch(SQLException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            
            if(c3 != null)
            {
                try
                {
                    c3.close();
                }
                catch(SQLException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
    
    private Connection getConnection() throws ClassNotFoundException, SQLException
    {
        Class.forName(driverClassName);
        Connection c = DriverManager.getConnection(url, user, password);
        return c;
        
    }
    
    private void logDatabaseMeta(Connection c) throws SQLException
    {
        DatabaseMetaData dm = c.getMetaData();
        if(dm != null)
        {
            
            System.out.println("mysql的默认事务隔离级别：" + dm.getDefaultTransactionIsolation());
        }
    }
}
