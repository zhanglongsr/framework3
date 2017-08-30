package com.zxl.test.math;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import org.junit.Test;

public class MathTest
{
    // 舍入模式总结(java.math.RoundingMode)：
    // RoundingMode的舍入模式有以下几种
    // UP : 远离零方向舍入的舍入模式。始终对非零舍弃部分前面的数字加 1;
    // DOWN:向零方向舍入的舍入模式。从不对舍弃部分前面的数字加 1（即截尾）。
    // CEILING:向正无限大方向舍入的舍入模式。如果结果为正，则舍入行为类似于 RoundingMode.UP；如果结果为负，则舍入行为类似于 RoundingMode.DOWN。
    // FLOOR:向负无限大方向舍入的舍入模式。如果结果为正，则舍入行为类似于 RoundingMode.DOWN；如果结果为负，则舍入行为类似于 RoundingMode.UP。
    // HALF_UP:向最接近数字方向舍入的舍入模式，如果与两个相邻数字的距离相等，则向上舍入。如果被舍弃部分 >= 0.5，则舍入行为同 RoundingMode.UP；否则舍入行为同 RoundingMode.DOWN。此舍入模式就是通常学校里讲的四舍五入。
    // HALF_DOWN:
    
    public void testRoundMode()
    {
        
        MathContext mc1 = new MathContext(3, RoundingMode.HALF_UP);
        BigDecimal con = new BigDecimal(1);
        
        BigDecimal b1 = new BigDecimal(4.2);
        BigDecimal b2 = new BigDecimal(16.5);
        BigDecimal b3 = new BigDecimal(1.0);
        BigDecimal b4 = new BigDecimal(-1.2);
        BigDecimal b5 = new BigDecimal(-1.8);
        
        System.out.println(b2.multiply(con, mc1).setScale(1, RoundingMode.HALF_UP));
        System.out.println(b2.multiply(con, mc1).intValue());
        
    }
    
    public void testDecimal2FloatPercision()
    {
        // BigDecimal decimal = new BigDecimal(0.75);
        // decimal = decimal.multiply(new BigDecimal(100)).add(new BigDecimal(0.00)).setScale(2, RoundingMode.HALF_UP);
        // System.out.println(Float.valueOf(decimal.toString()));
        // System.out.println(decimal.doubleValue());
        // System.out.println(decimal.floatValue() + 0.00);
        //
        // Float f = new Float(0);
        
        BigDecimal decimal = new BigDecimal(-10).divide(new BigDecimal(3), 2, RoundingMode.HALF_UP);
        
        // decimal = decimal.multiply(new BigDecimal(0.7)).setScale(2, RoundingMode.HALF_UP);
        // decimal = decimal.add(new BigDecimal(0.1));
        decimal = decimal.subtract(new BigDecimal(0.1));
        System.out.println(decimal);
    }
    
    @Test
    public void testFloat()
    {
        Float f = new Float(-10.0);
        
        if(f.floatValue() > 0)
        {
            System.out.println("ii");
        }
    }
    
}
