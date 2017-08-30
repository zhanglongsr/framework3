package com.zxl.test.io;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.junit.Test;

public class IOTest {
    @Test
    public void testWriteLine() {
        String path = "d:/line.txt";

        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path)));
            for (int i = 0; i < 100; i++) {
                String content = i + "=" + System.currentTimeMillis();
                writer.write(content);
                writer.newLine();
            }
            writer.flush();
            writer.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void byteTest() {
        byte[] bytes = { 49, 50, 51 };
        String a = new String(bytes);
        System.out.println(a);
    }
}
