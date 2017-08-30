package com.zxl.test.text.format;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.junit.Test;

public class MessageFormatTest {
    Integer in = 100;

    @Test
    public void testCommon() {
        Map<String, Boolean> map = new TreeMap<String, Boolean>();
        map.put("X", Boolean.FALSE);
        map.put("A", Boolean.TRUE);
        map.put("F", Boolean.FALSE);
        map.put("C", Boolean.TRUE);

        for (Entry<String, Boolean> entry : map.entrySet()) {
            System.out.println(entry.getKey());
        }

        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            f.parse("2017-03-10 12:30:18");
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(f.getCalendar().getTimeInMillis());
        System.out.println(f.getCalendar().getTime().getTime());
    }
}
