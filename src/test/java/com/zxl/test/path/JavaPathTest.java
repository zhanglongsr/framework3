package com.zxl.test.path;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

public class JavaPathTest {
    @Test
    public void test() {
        String x = JavaPathTest.class.getResource("/").getPath();
        System.out.println(x);
        x = x.substring(0, x.length() - 1);

        // int index = x.lastIndexOf(File.separator);
        // x = x.substring(0, index - 1);
        // System.out.println(x);

        System.out.println(Thread.currentThread().getContextClassLoader().getResource("").getPath());
        System.out.println(Thread.currentThread().getContextClassLoader().getResource("/").getPath());
    }

    public void test1() {
        String packageName = "com.zxl.test";
        String packagePath = packageName.replace(".", "/");
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try {
            List<URL> list = Collections.list(loader.getResources(packagePath));
            for (URL url : list) {
                System.out.println(url.getPath());
                System.out.println(url.getFile());
            }
        } catch (IOException e) {
            // TODO_GOD Auto-generated catch block
            e.printStackTrace();
        }

    }

}
