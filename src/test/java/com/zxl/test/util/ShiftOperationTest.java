package com.zxl.test.util;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ShiftOperationTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test() {
       
//         int number = 10;
//         
//         //结论：左移运算符，num << n,相当于num乘以2的n次方
//         for(int i=1;i<9;i++){
//             int num_left = number << i;
//             System.out.println("原始值:"+number+"，左移"+i+"位,十进制结果:"+num_left+",二进制结果："+Integer.toBinaryString(num_left));
//         }
         
        //结论：右移一位，相当于num除以2;当移位n位时，相当于num除以2的n次方
         int number1 = -100;
         System.out.println(Integer.toBinaryString(number1));
         for(int i=1;i<9;i++){
             int num_right = number1 >>i;
             System.out.println("原始值:"+number1+"，右移"+i+"位,十进制结果:"+num_right+",二进制结果："+Integer.toBinaryString(num_right));
         }
         
    }
    
   

}
