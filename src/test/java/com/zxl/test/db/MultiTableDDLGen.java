package com.zxl.test.db;

import java.util.ArrayList;
import java.util.List;

public class MultiTableDDLGen
{
    
    public static void main(String[] args)
    {
        MultiTableDDLGen gen = new MultiTableDDLGen();
        List<String> list = gen.genSelect("cv_view_uv_", " where cvu_createtime>'20160323160000' and cvu_createtime<'20160323170000' and cvu_ip='101.230.211.229'", 0, 100);
        // List<String> list = gen.genSelect("cv_view_rv_", " where res_user_id=13887350", 0, 100);
        // List<String> list = gen.genSelect("cv_download_", " where =23981147", 0, 100);
        for(String sql : list)
        {
            System.out.println(sql);
        }
    }
    
    private static String SELECT = "select * from ";
    
    public List<String> genSelect(String table, String where, int start, int end)
    {
        List<String> list = new ArrayList<String>();
        for(int i = start; i < end; i++)
        {
            StringBuffer buffer = new StringBuffer();
            buffer.append(SELECT).append(table).append(i).append(" ");
            buffer.append(where);
            if(i < end - 1)
            {
                buffer.append(" union ");
            }
            
            list.add(buffer.toString());
        }
        
        return list;
    }
}
