package com.zxl.test.util;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

public class UUIDTest {

    public static void main(String[] args) {
        UUID uuid = UUID.randomUUID();
        System.out.println(uuid.getMostSignificantBits());
        System.out.println(uuid.getLeastSignificantBits());
        System.out.println(uuid.toString());
        System.out.println(uuid.variant());
        System.out.println(uuid.version());

        String string = "1c35cbe6850b4301aba307a6249788b2";

        byte[] b1 = null;
        try {
            b1 = string.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(b1.length);

    }
}
