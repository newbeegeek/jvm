package com.newbee.jvm.study;


/**
 * @Description: 一个普通的算数类
 * @Title: Math
 * @Package com.newbee.jvm.study
 * @Author: NewBee.Geek
 * @CreateTime: 2023/3/26 10:57 AM
 */
public class Math {

    public static final int initNum = 666;

    public static User user = new User();

    /***
     * @author [NewBee.Geek]
     * @description 一个简单的运算
     * @date 2023/3/26 10:59 AM
     */
    public int compute(){
        int a = 1;
        int b = 2;
        int c = 6 * (a + b);
        return c;
    }

    public static void main(String[] args) {
        Math math = new Math();
        math.compute();
    }



}
