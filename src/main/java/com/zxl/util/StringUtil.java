package com.zxl.util;

import java.text.DecimalFormat;

public class StringUtil
{
    public static boolean isBlank(String str)
    {
        if(str == null || "".equals(str.trim()))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    public static boolean isNotBlank(String str)
    {
        if(str != null && !"".equals(str.trim()))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    public static String trim(String str)
    {
        if(str == null)
        {
            return str;
        }
        str = str.trim();
        for(int i = 0; i < str.length(); i++)
        {
            int tmp = str.charAt(i);
            if(tmp == 9 || tmp == 13 || tmp == 10 || tmp == 32 || tmp == 12288)
            {
                continue;
            }
            else
            {
                str = str.substring(i);
                break;
            }
        }
        for(int i = str.length() - 1; i >= 0; i--)
        {
            int tmp = str.charAt(i);
            if(tmp == 9 || tmp == 13 || tmp == 10 || tmp == 32 || tmp == 12288)
            {
                continue;
            }
            else
            {
                str = str.substring(0, i + 1);
                break;
            }
        }
        return str;
    }
    
    public static String return2Br(String arg)
    {
        if(arg == null || "".equals(arg))
        {
            return "";
        }
        else
        {
            arg = arg.replaceAll("\r\n", "<br/>");
            arg = arg.replaceAll("\n", "<br/>");
            return arg;
        }
    }
    
    public static String br2Return(String arg)
    {
        if(arg == null || "".equals(arg))
        {
            return "";
        }
        else
        {
            arg = arg.replaceAll("<br>", "\r\n");
            arg = arg.replaceAll("<br/>", "\r\n");
            return arg;
        }
    }
    
    public static String whiteSpace2nbsp(String arg)
    {
        if(arg == null || "".equals(arg))
        {
            return "";
        }
        else
        {
            arg = arg.replaceAll("\t", "&nbsp;&nbsp;&nbsp;&nbsp;");
            arg = arg.replaceAll(" ", "&nbsp;");
            return arg;
        }
    }
    
    public static String format(float d, int scale)
    {
        return format(new Double(d), scale);
    }
    
    public static String format(double d, int scale)
    {
        StringBuffer sb = new StringBuffer("0");
        if(scale > 0)
        {
            sb.append(".");
        }
        for(int i = 0; i < scale; i++)
        {
            sb.append("0");
        }
        DecimalFormat df = new DecimalFormat(sb.toString());
        return df.format(d);
    }
    
    public static String supplyChar(String str, int len, char c)
    {
        String ret = str;
        if(ret != null && !"".equals(ret))
        {
            for(int i = 0; i < len - str.length(); i++)
            {
                ret = c + ret;
            }
        }
        return ret;
    }
    
    public static String supply8Zero(Object str)
    {
        return supplyChar(String.valueOf(str), 8, '0');
    }
    
    public static String supply8Zero(String str)
    {
        return supplyChar(str, 8, '0');
    }
    
    public static String supply8Zero(Integer str)
    {
        if(str != null)
        {
            return supplyChar(String.valueOf(str), 8, '0');
        }
        else
        {
            return null;
        }
    }
    
    public static String supply8Zero(int str)
    {
        return supplyChar(String.valueOf(str), 8, '0');
    }
}
