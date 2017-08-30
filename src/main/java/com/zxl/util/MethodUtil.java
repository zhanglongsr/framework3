package com.zxl.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.log4j.Logger;

public class MethodUtil
{
    private static final Logger logger = Logger.getLogger(MethodUtil.class);
    // Class的method缓存
    private static final Map<Class<?>, Map<String, Method>> METHOD_CACHE = new ConcurrentHashMap<Class<?>, Map<String, Method>>();
    // Method的returntype缓存
    private static final Map<Method, Class<?>> METHOD_RETURN_TYPE_CACHE = new ConcurrentHashMap<Method, Class<?>>();
    
    public static Method getMethod(Class<?> clazz, String methodName, Object[] pararm)
    {
        
        Map<String, Method> map = METHOD_CACHE.get(clazz);
        if(map == null)
        {
            map = new HashMap<String, Method>();
        }
        
        Method method = map.get(methodName);
        if(method != null)
            return method;
        
        List<Method> mList = getSpecialMethod(clazz, methodName);
        
        if(mList.size() == 0)
            return null;
        
        if(mList.size() == 1)
        {
            method = mList.get(0);
            map.put(methodName, method);
            return method;
        }
        
        for(Method m : mList)
        {
            Class[] classes = m.getParameterTypes();
            if(classes.length == 0 && (pararm == null || pararm.length == 0))
                return m;
            if(pararm == null || pararm.length == 0)
            {
                return null;
            }
            if(classes.length != pararm.length)
            {
                continue;
            }
            boolean flag = true;
            for(int i = 0; i < classes.length; i++)
            {
                Class clzss = classes[i];
                Class paramClzss = pararm[i].getClass();
                if(!clzss.toString().equals(paramClzss.toString()))
                {
                    flag = false;
                    break;
                }
            }
            
            if(flag)
            {
                method = m;
                break;
            }
        }
        
        return method;
    }
    
    private static List<Method> getSpecialMethod(Class<?> clazz, String methodName)
    {
        List<Method> mList = new ArrayList<Method>();
        Method[] methods = clazz.getMethods();
        
        for(Method method : methods)
        {
            if(method.getName().equals(methodName))
            {// add methodName some item
                mList.add(method);
            }
        }
        
        return mList;
    }
}
