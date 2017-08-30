package com.zxl.util;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javassist.Modifier;

public class Reflector {
    public static final Map<Class<?>, List<Field>> CLASS_FIELD_CACHE = new java.util.concurrent.ConcurrentHashMap<Class<?>, List<Field>>();

    public static final Map<Class<?>, Map<String, Method[]>> CLASS_METHOD_CACHE = new java.util.concurrent.ConcurrentHashMap<Class<?>, Map<String, Method[]>>();

    // ----------------- myself Reflector method --------------//

    /**
     * 获取某类声明的方法
     * 
     * @param clazz
     * @return
     */
    public Method[] getAllDeclaredMethods(Class<?> clazz) {

        if (clazz == null)
            return null;

        Method[] parentClassMethods = getAllDeclaredMethods(clazz.getSuperclass());
        Method[] thisClassMethods = clazz.getDeclaredMethods();
        if (parentClassMethods != null && parentClassMethods.length > 0) {
            Method[] methods = new Method[parentClassMethods.length + thisClassMethods.length];
            System.arraycopy(parentClassMethods, 0, methods, 0, parentClassMethods.length);
            System.arraycopy(thisClassMethods, 0, methods, parentClassMethods.length, thisClassMethods.length);
            thisClassMethods = methods;
        }
        return thisClassMethods;

    }

    /**
     * 获得类的方法
     * 
     * @param clazz
     * @return
     */
    public Map<String, Method[]> getClassMethods(Class<?> clazz) {

        Map<String, Method[]> methods = CLASS_METHOD_CACHE.get(clazz);
        if (methods == null) {
            CLASS_METHOD_CACHE.put(clazz, methods = this.getMethods(clazz));
        }
        return methods;

    }

    private Map<String, Method[]> getMethods(Class<?> clazz) {
        Map<String, Method[]> methodMap = new HashMap<String, Method[]>();

        List<Field> fields = new ArrayList<Field>();
        List<Method> methods = new ArrayList<Method>();

        Class<?> superClass = clazz;
        while (superClass != null && !Object.class.equals(superClass)) {
            fields.addAll(Arrays.asList(superClass.getDeclaredFields()));
            methods.addAll(Arrays.asList(superClass.getDeclaredMethods()));
            superClass = superClass.getSuperclass();
        }

        for (Field field : fields) {
            int mod = field.getModifiers();
            if (Modifier.isFinal(mod)) {
                continue;
            }
            // 父类和子类中同名的字段只取子类的
            if (methodMap.containsKey(field.getName())) {
                continue;
            }

            String fieldName = field.getName();
            for (Method method : methods) {
                String methodName = method.getName();
                if (methodName.startsWith("set")) {
                    String tmpFieldName = methodName.substring("set".length()).toLowerCase();
                    if (fieldName.equals(tmpFieldName)) {
                        Method[] ms = methodMap.get(fieldName);
                        if (ms == null) {
                            ms = new Method[2];
                            methodMap.put(methodName, ms);
                        }
                        ms[0] = method;
                    }
                } else if (methodName.startsWith("get") || methodName.startsWith("is")) {
                    String getTmp = methodName.substring("get".length()).toLowerCase();
                    String isTmp = methodName.substring("is".length()).toLowerCase();
                    if (fieldName.equals(getTmp) || fieldName.equals(isTmp)) {
                        Method[] ms = methodMap.get(fieldName);
                        if (ms == null) {
                            ms = new Method[2];
                            methodMap.put(methodName, ms);
                        }
                        ms[1] = method;
                    }
                }
            }

        }

        return methodMap;
    }

    public List<Field> getClassFields(Class<?> clazz) {
        List<Field> fields = CLASS_FIELD_CACHE.get(clazz);
        if (fields == null) {
            CLASS_FIELD_CACHE.put(clazz, fields = getFields(clazz));
        }

        return fields;

    }

    private List<Field> getFields(Class<?> clazz) {

        List<Field> fields = new ArrayList<Field>();
        List<AccessibleObject> list = new ArrayList<AccessibleObject>();
        while (clazz != null && Object.class != clazz) {
            list.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }

        for (AccessibleObject accessObj : list) {
            accessObj.setAccessible(true);
            Field field = (Field) accessObj;
            if (Modifier.isFinal(field.getModifiers())) {
                continue;
            }

            boolean isAdded = true;
            for (Field tmp : fields) {
                if (tmp.getName().equals(field.getName())) {
                    isAdded = false;
                    break;
                }
            }

            if (isAdded) {
                fields.add(field);
            }
        }

        return fields;
    }

    public Object newObjectFromField(ResultSet rs, Class<?> clazz) throws SQLException {
        Object clazzObj = null;
        try {
            clazzObj = clazz.newInstance();
        } catch (Exception e) {
            // TODO log
        }

        if (clazzObj != null) {
            List<Field> fields = CLASS_FIELD_CACHE.get(clazz);
            try {
                for (Field field : fields) {
                    Object value = rs.getObject(field.getName());
                    if (value == null)
                        continue;
                    field.set(clazzObj, value);
                }
            } catch (Exception e) {
                throw new SQLException(e);
            }
        }

        return clazzObj;
    }

    public Object newObjectFromMethod(ResultSet rs, Class<?> clazz) throws SQLException {
        Object clazzObj = null;
        try {
            clazzObj = clazz.newInstance();
        } catch (Exception e) {
            // TODO log
        }

        if (clazzObj != null) {
            Map<String, Method[]> methods = CLASS_METHOD_CACHE.get(clazz);
            try {
                for (Map.Entry<String, Method[]> entry : methods.entrySet()) {
                    Object value = rs.getObject(entry.getKey());
                    if (value == null)
                        continue;
                    entry.getValue()[0].invoke(clazzObj, new Object[] { value });
                }
            } catch (Exception e) {
                throw new SQLException(e);
            }

        }

        return clazzObj;
    }

}