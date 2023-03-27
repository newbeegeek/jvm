package com.newbee.jvm.classloader;

/**
 * @Description: 测试类加载器加载路径
 * @Title: TestClassLoadPath
 * @Package com.newbee.jvm.classloader
 * @Author: NewBee.Geek
 * @CreateTime: 2023/3/26 4:20 PM
 */
public class TestClassLoadPath {

    public static void main(String[] args) {

        //String类路径 /Home/jre/lib/rt.jar
        System.out.println(String.class.getClassLoader());

        //DESKeyFactory类路径 /Home/jre/lib/ext/sunjce_provider.jar
        System.out.println(com.sun.crypto.provider.DESKeyFactory.class.getClassLoader());

        //本类路径 /target/classes/com/newbee/jvm/classloader/TestClassLoadPath.class
        System.out.println(TestClassLoadPath .class.getClassLoader());

        System.out.println("测试加载器Parent属性");
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        ClassLoader extClassLoader = systemClassLoader.getParent();
        ClassLoader bootstrapLoader = extClassLoader.getParent();

        System.out.println("系统默认类加载器为：" + systemClassLoader);
        System.out.println("系统默认类加载器Parent加载器为：" + extClassLoader);
        System.out.println("系统默认类加载器Parent的Parent加载器为：" + bootstrapLoader);



    }


}
