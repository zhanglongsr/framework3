package com.zxl.util.test;

import java.lang.reflect.Method;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.zxl.asyn.AsynThreadPoolExecutor;
import com.zxl.util.Reflector;

public class ReflectTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetAllDeclaredMethods() {
        Reflector reflector = new Reflector();
        Method[] methods = reflector.getAllDeclaredMethods(AsynThreadPoolExecutor.class);
        for (Method m : methods) {
            StringBuffer buffer = new StringBuffer(m.getName());
            buffer.append("(");
            Class<?>[] paramClasseses = m.getParameterTypes();
            if (paramClasseses != null && paramClasseses.length > 0) {
                for (int i = 0; i < paramClasseses.length; i++) {
                    buffer.append(paramClasseses.toString()).append(" ").append("i" + i);
                    if (i < paramClasseses.length - 1) {
                        buffer.append(",");
                    }
                }
            }
            buffer.append(")");
            System.out.println(buffer.toString());
        }
    }

}
