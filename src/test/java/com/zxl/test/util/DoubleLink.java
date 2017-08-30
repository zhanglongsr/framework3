package com.zxl.test.util;

import java.io.Serializable;

/**
 * 模拟实现双向链表,参考LinkedHashMap实现
 * 
 * @author zhangxl
 * 
 * @param <T>
 */
public class DoubleLink<T> implements Serializable
{
    private Entity<T> header;
    
    public DoubleLink()
    {
        header = new Entity<T>();
        header.before = header.after = header;
    }
    
    public void add(T value)
    {
        Entity<T> node = new Entity<T>();
        node.value = value;
        node.addBefore(header);
    }
    
    public String toString()
    {
        
        if(header == header.before)
            return "";
        
        Entity<T> node = header.before;
        StringBuffer buffer = new StringBuffer();
        while(node != header)
        {
            buffer.append(node.value.toString());
            node = node.before;
        }
        
        return buffer.toString();
    }
    
    private class Entity<T>
    {
        
        Entity<T> before;
        
        Entity<T> after;
        
        T value;
        
        /**
         * 在指定节点前插入
         * 
         * @param node
         */
        void addBefore(Entity<T> node)
        {
            after = node;
            before = node.before;
            after.before = this;
            before.after = this;
        }
        
        /**
         * 在指定节点后插入
         * 
         * @param node
         */
        void addAfter(Entity<T> node)
        {
            before = node;
            after = node.after;
            before.after = this;
            after.before = this;
        }
    }
    
    public static void main(String... arg)
    {
        DoubleLink<String> dl = new DoubleLink<String>();
        
        String[] str = {"1", "2", "3", "4", "5"};
        for(String s : str)
        {
            dl.add(s);
        }
        
        System.out.println(dl.toString());
        
    }
}
