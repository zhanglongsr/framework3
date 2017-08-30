package com.zxl.table;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.mysql.jdbc.Connection;

public class BuildTable
{
    public static void main(String[] args)
    {
        // TODO Auto-generated method stub
        Connection c = ConnectionHelper.getConnection("10.10.10.11", 21003, "lietou", "lietou", "lietou", true, "gbk");
        if(c != null)
        {
            try
            {
                loadTables(c);
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
            finally
            {
                try
                {
                    c.close();
                }
                catch(SQLException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
    
    public static List<Table> loadTables(Connection c) throws SQLException
    {
        List<Table> tables = new ArrayList<Table>();
        
        DatabaseMetaData metaData = c.getMetaData();
        ResultSet rs = metaData.getTables(c.getCatalog(), "%", "user_resume3_rv_%", new String[]{"TABLE"});
        while(rs.next())
        {
            String tableCat = rs.getString("TABLE_CAT");
            String tableName = rs.getString("TABLE_NAME");
            String tableSchema = rs.getString("TABLE_SCHEM");
            String tableType = rs.getString("TABLE_TYPE");
            // String SELF_REFERENCING_COL_NAME = rs.getString("SELF_REFERENCING_COL_NAME");
            System.out.println("tableName:" + tableName + ",tableCat:" + tableCat + ",tableSchema:" + tableSchema + ",tableType:" + tableType);
            Table t = new Table(rs.getString("TABLE_NAME"));
            tables.add(t);
            t.addTableFields(loadFiledsInTable(tableName, c));
        }
        
        return tables;
        
    }
    
    public static List<TableField> loadFiledsInTable(String tableName, Connection c) throws SQLException
    {
        List<TableField> fields = new ArrayList<TableField>();
        DatabaseMetaData metaData = c.getMetaData();
        ResultSet cs = metaData.getColumns(c.getCatalog(), "%", tableName, "%");
        while(cs.next())
        {
            String COLUMN_NAME = cs.getString("COLUMN_NAME");
            int DATA_TYPE = cs.getInt("DATA_TYPE");
            String TYPE_NAME = cs.getString("TYPE_NAME");
            int COLUMN_SIZE = cs.getInt("COLUMN_SIZE");
            int NULLABLE = cs.getInt("NULLABLE");
            
            System.out.println("COLUMN_NAME:" + COLUMN_NAME + ",TYPE_NAME:" + TYPE_NAME + ",COLUMN_SIZE:" + COLUMN_SIZE);
            TableField field = new TableField(COLUMN_NAME, TYPE_NAME);
            fields.add(field);
            
        }
        return fields;
        
    }
    
    public static class ConnectionHelper
    {
        
        public static Connection getConnection(String ip, int port, String schema, String user, String password, boolean useUnicode, String characterEncoding)
        {
            Connection con = null;
            
            try
            {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                String url = "jdbc:mysql://" + ip + ":" + port + "/" + schema + "?" + "useUnicode" + useUnicode + "&characterEncoding" + characterEncoding;
                con = (Connection) java.sql.DriverManager.getConnection(url, user, password);
            }
            catch(InstantiationException e)
            {
                e.printStackTrace();
            }
            catch(IllegalAccessException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            catch(ClassNotFoundException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            catch(SQLException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
            return con;
            
        }
        
    }
    
    public static class Table
    {
        private String tableName;
        private List<TableField> fields = new ArrayList<TableField>(15);
        private List<TableIndex> indexs = new ArrayList<TableIndex>(2);
        
        public Table(String tableName)
        {
            this.tableName = tableName;
        }
        
        public int addTableField(TableField field)
        {
            
            if(fields.contains(field))
            {
                throw new RuntimeException("table[" + this.tableName + "],field[" + field.getFieldName() + "],alread exist!");
            }
            
            fields.add(fields.size(), field);
            
            return fields.size();
            
        }
        
        public void addTableFields(List<TableField> fields)
        {
            for(TableField field : fields)
            {
                this.addTableField(field);
            }
        }
        
        public int addTableIndex(TableIndex index)
        {
            
            if(indexs.contains(index))
            {
                throw new RuntimeException("table[" + this.tableName + "],field[" + index + "],alread exist!");
            }
            return 0;
            
        }
        
        public String getCreateSql()
        {
            StringBuilder b = new StringBuilder();
            b.append("CREATE TABLE");
            return tableName;
            
        }
    }
    
    public static class TableField
    {
        private String fieldName;
        private String fieldType;
        private long fieldLength;
        
        public TableField(String fieldName, String fieldType)
        {
            this.fieldName = fieldName;
            this.fieldType = fieldType;
        }
        
        public String getFieldName()
        {
            return fieldName;
        }
        
        public void setFieldName(String fieldName)
        {
            this.fieldName = fieldName;
        }
        
        public String getFieldType()
        {
            return fieldType;
        }
        
        public void setFieldType(String fieldType)
        {
            this.fieldType = fieldType;
        }
        
    }
    
    class TableIndex
    {
        private String indexName;
        
    }
}
