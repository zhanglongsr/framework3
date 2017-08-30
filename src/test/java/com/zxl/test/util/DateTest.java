package com.zxl.test.util;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTest
{
    
    SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    
    /**
     * @param args
     */
    public static void main(String[] args)
    {
        DateTest test = new DateTest();
        test.testCalendar();
        
    }
    
    public void testCalendar()
    {
        Calendar cal = Calendar.getInstance();
        cal.set(2014, 2, 1, 0, 0, 1);
        String datetime = simpleFormat.format(cal.getTime());
        System.out.println("datetime:" + datetime);
    }
    
    public void calTime()
    {
        // Calendar a = Calendar.getInstance();
        // String datetime = simpleFormat.format(a.getTime());
        // System.out.println("datetime:" + datetime);
        
        // try
        // {
        // Date d = simpleFormat.parse(datetime);
        // }
        // catch(ParseException e)
        // {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
        
        // String time = "20130829134000";
        //
        // System.out.println(this.genExpire(time, 1, 0, 0, 0));
        
        Date date = new Date(System.currentTimeMillis());
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, -3);
        
        Date d1 = cal.getTime();
        
        System.out.println(d1);
    }
    
    private int genExpire(String _time, int day, int hour, int minute, int second)
    {
        Calendar baseCal = null;// 起始基准时间
        // if(StringUtil.isNotBlank(_time))
        // {
        // baseCal = DateUtil.getCalendar(_time);
        // }
        // else
        // {
        // baseCal = Calendar.getInstance();
        // }
        //
        long curTime = Calendar.getInstance().getTimeInMillis();// 当前时间
        
        if(day > 0)
            baseCal.add(Calendar.DAY_OF_MONTH, day);
        if(hour > 0)
            baseCal.add(Calendar.HOUR_OF_DAY, hour);
        if(minute > 0)
            baseCal.add(Calendar.MINUTE, minute);
        if(second > 0)
            baseCal.add(Calendar.SECOND, second);
        
        long afterTime = baseCal.getTimeInMillis();
        
        return (int) (afterTime - curTime) / 1000;
    }
    
    public void test2()
    {
        BigDecimal decimal = new BigDecimal(16.585, new MathContext(2));
        // decimal = decimal.divide(new BigDecimal(30), 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
        System.out.println(decimal.doubleValue());
        System.out.println(decimal.intValue());
    }
}
