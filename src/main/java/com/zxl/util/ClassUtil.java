package com.zxl.util;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassUtil
{
    private static final Map<Class<?>, List<Field>> CLASS_FIELD_CACHE = new HashMap<Class<?>, List<Field>>();
    /**
     * class => field[setter,getter]
     */
    private static final Map<Class<?>, Map<String, Method[]>> CLASS_METHOD_CACHE = new HashMap<Class<?>, Map<String, Method[]>>();
    
    public static List<Field> collectClassFields(Class<?> clazz)
    {
        List<Field> fields = CLASS_FIELD_CACHE.get(clazz);
        if(fields == null)
        {
            synchronized(clazz)
            {
                if(!CLASS_FIELD_CACHE.containsKey(clazz))
                {
                    CLASS_FIELD_CACHE.put(clazz, fields = loadFields(clazz));
                }
                else
                {
                    fields = CLASS_FIELD_CACHE.get(clazz);
                }
            }
        }
        return fields;
    }
    
    public static List<Field> loadFields(Class<?> clazz)
    {
        List<Field> list = new ArrayList<Field>();
        List<AccessibleObject> fields = new ArrayList<AccessibleObject>();
        Class<?> superClazz = clazz;
        while(superClazz != null && superClazz != Object.class)
        {
            fields.addAll(Arrays.asList(superClazz.getDeclaredFields()));
            superClazz = superClazz.getSuperclass();
        }
        
        for(AccessibleObject ao : fields)
        {
            ao.setAccessible(true);
            Field field = (Field) ao;
            int mod = field.getModifiers();
            if(Modifier.isFinal(mod))
            {
                continue;
            }
            
            boolean add = true;
            for(Field tmp : list)
            {
                if(tmp.getName().equals(field.getName()))
                {
                    add = false;
                    break;
                }
            }
            
            if(add)
            {
                list.add(field);
            }
        }
        
        return list;
    }
    
    /**
     * 获取类的字段对应的get/set方法集合
     * 
     * @param clazz
     * @return Map<String, Method[]>
     */
    public static Map<String, Method[]> getClassMethods(Class<?> clazz)
    {
        Map<String, Method[]> methods = CLASS_METHOD_CACHE.get(clazz);
        if(methods == null)
        {
            synchronized(clazz)
            {
                if(!CLASS_METHOD_CACHE.containsKey(clazz))
                {
                    CLASS_METHOD_CACHE.put(clazz, methods = collectMethods(clazz));
                }
                else
                {
                    methods = CLASS_METHOD_CACHE.get(clazz);
                }
            }
        }
        return methods;
    }
    
    private static Map<String, Method[]> collectMethods(Class<?> clazz)
    {
        Map<String, Method[]> result = new HashMap<String, Method[]>();
        
        List<Field> fields = new ArrayList<Field>();
        List<Method> methods = new ArrayList<Method>();
        
        Class<?> superClazz = clazz;
        while(superClazz != null && superClazz != Object.class)
        {
            fields.addAll(Arrays.asList(superClazz.getDeclaredFields()));
            methods.addAll(Arrays.asList(superClazz.getDeclaredMethods()));
            superClazz = superClazz.getSuperclass();
        }
        
        for(Field field : fields)
        {
            
            int mod = field.getModifiers();
            if(Modifier.isFinal(mod))
            {
                continue;
            }
            // 父类和子类中同名的字段只取子类的(如果是@PrimaryKey键，子类也必须带)
            if(result.containsKey(field.getName()))
            {
                continue;
            }
            for(Method method : methods)
            {
                if(collect(method, field, result))
                {
                    break;
                }
            }
        }
        return result;
    }
    
    /**
     * 收集完成返回true否则false
     * 
     * @return boolean
     */
    private static boolean collect(Method method, Field field, Map<String, Method[]> cache)
    {
        String fieldname = field.getName().toLowerCase();
        String methodname = method.getName();
        if(methodname.startsWith("set"))
        {
            String setTmp = methodname.substring("set".length()).toLowerCase();
            if(fieldname.equals(setTmp))
            {
                Method[] methods = cache.get(field.getName());
                if(methods == null)
                {
                    methods = new Method[2];
                    cache.put(field.getName(), methods);
                }
                return isFill(methods, 0, method);
            }
        }
        else if(methodname.startsWith("get") || methodname.startsWith("is"))
        {
            String getTmp = methodname.substring("get".length()).toLowerCase();
            String getTmp1 = methodname.substring("is".length()).toLowerCase();
            if(fieldname.equals(getTmp) || fieldname.equals(getTmp1))
            {
                Method[] methods = cache.get(field.getName());
                if(methods == null)
                {
                    methods = new Method[2];
                    cache.put(field.getName(), methods);
                }
                return isFill(methods, 1, method);
            }
        }
        return false;
    }
    
    private static boolean isFill(Method[] methods, int i, Method m)
    {
        methods[i] = m;
        return (methods[0] == null || methods[1] == null) ? false : true;
    }
}
