package com.zxl.test;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MsTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test() {
        // System.out.println(getAge(2));
        // int[] months = { 201403, 201408, 201411, 201310 };
        // System.out.println(this.getDifference(months));

        String s1 = "weifwewefabc2wlelsimgsabcdefghij3rr32r2wopirewri3024r923i2irwepiriepwisdfweiewprikjsnvabcdefgwlosodiwabcdef";
        String s2 = "9343ksdfjsdfabc23lfselfjsdfsabcdefgkiewri302abcdefghijriewprikj233232323232r423rr32r23r32r";
        long l1 = new Date().getTime();
        System.out.println(this.getMaxLenEqualStr(s1, s2));
        long l2 = new Date().getTime();
        System.out.println(l2 - l1);
    }

    private int getAge(int i) {

        if (i == 1) {
            return 10;
        }

        return getAge(--i) + 2;
    }

    private int getDifference(int[] args) {

        int max = getMax(args);
        int min = getMin(args);

        int maxYear = Integer.valueOf(String.valueOf(max).substring(0, 4));
        int maxMonth = Integer.valueOf(String.valueOf(max).substring(4, 6));
        int maxMonths = maxYear * 12 + maxMonth;

        int minYear = Integer.valueOf(String.valueOf(min).substring(0, 4));
        int minMonth = Integer.valueOf(String.valueOf(min).substring(4, 6));
        int minMonths = minYear * 12 + minMonth;
        return maxMonths - minMonths;

    }

    private int getMax(int[] args) {
        int max = 0;
        for (int i = 0; i < args.length; i++) {
            if (args[i] > max)
                max = args[i];
        }
        return max;
    }

    private int getMin(int[] args) {
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < args.length; i++) {
            if (args[i] < min)
                min = args[i];
        }
        return min;
    }

    private String getMaxLenEqualStr(String s1, String s2) {

        char[] chars1 = s1.toCharArray(), chars2 = s2.toCharArray();
        int len1 = chars1.length, len2 = chars2.length;

        String maxEqualStr = ""; // 最大相等字符串
        for (int i = 0; i < len1; i++) {

            if (i + maxEqualStr.length() > len1)
                break;

            int next = i; // 当外层与内层char相等时，用于内层对照外层延长比较的临时外层索引
            String tempEqualStr = "";
            for (int j = 0; j < len2; j++) {
                if (chars1[i] == chars2[j]) {
                    tempEqualStr += chars1[i];
                    while (++j < len2 && ++next < len1 && chars1[next] == chars2[j]) {
                        tempEqualStr += chars1[next];
                    }
                    if (tempEqualStr.length() > maxEqualStr.length()) {
                        maxEqualStr = tempEqualStr;
                    }
                    tempEqualStr = ""; // 还原
                    next = i;
                }
            }
        }

        return maxEqualStr;
    }
}
