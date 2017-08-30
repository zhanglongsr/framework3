package com.zxl.test;

public class MyFinalClass {
    final int type;
    public String name;

    public Integer age;

    public MyFinalClass(final int type) {
        this.type = type;
    }

    public static void main(String[] args) {

        MyFinalClass a = new MyFinalClass(1);
        MyFinalClass b = new MyFinalClass(2);
        MyFinalClass c = new MyFinalClass(3);

        System.out.println(a.type);
        System.out.println(b.type);
        System.out.println(c.type);
    }
}
