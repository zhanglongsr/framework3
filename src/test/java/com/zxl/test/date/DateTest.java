package com.zxl.test.date;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.junit.Test;

public class DateTest
{
    @Test
    public void testWeek()
    {
        String[] weeks = this.getWeekTime(2);
        System.out.println(weeks[0]);
        System.out.println(weeks[1]);
    }
    
    private String[] getWeekTime(int weekIndex)
    {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.WEEK_OF_YEAR, weekIndex);
        c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String startOfWeek = sdf.format(c.getTime());
        c.add(Calendar.DAY_OF_WEEK, 7);
        String endOfWeek = sdf.format(c.getTime());
        c.add(Calendar.DAY_OF_WEEK, -14);
        String startOfLastWeek = sdf.format(c.getTime());
        return new String[]{startOfWeek, startOfLastWeek, endOfWeek};
    }
}
