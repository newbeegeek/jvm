package com.newbee.jvm.classloader;

import java.io.FileInputStream;
import java.lang.reflect.Method;

/**
 * @Description: 手写类加载器
 * @Title: MyClassLoader
 * @Package com.newbee.jvm.classloader
 * @Author: NewBee.Geek
 * @CreateTime: 2023/3/27 9:38 PM
 */
public class MyClassLoader extends ClassLoader{


    private String classPath;

    public MyClassLoader (String classPath) {
        this.classPath = classPath;
    }

    /***
     * @author [NewBee.Geek]
     * @description 写入本地文件到内存中
     * @date 2023/3/27 9:43 PM
     */
    private byte[] loadByte(String name) throws Exception {
        name = name.replaceAll("\\.", "/");
        FileInputStream fis = new FileInputStream(classPath + "/" + name + ".class");
        int len = fis.available();
        byte[] data = new byte[len];
        fis.read(data);
        fis.close();
        return data;
    }

    /***
     * @author [NewBee.Geek]
     * @description 自定义类加载器->最重要的是要重写findlclass方法
     * @date 2023/3/27 9:41 PM
     */
    @Override
    protected Class<?> findClass(String name) {
        byte[] data = new byte[0];
        try {
            data = loadByte(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defineClass(name, data, 0, data.length);
    }

    /***
     * @author [NewBee.Geek]
     * @description 需要自己写一个test类 编译成class字节码文件，此类只有一个sout的方法，通过反射调用测试
     * @date 2023/3/27 10:06 PM
     */
    public static void main(String[] args) throws Exception {
        MyClassLoader classLoader = new MyClassLoader("/Users/caijunhao/Java/study/jvm");
        Class<?> clazz = classLoader.loadClass("com.newbee.jvm.classloader.Test");
        Object instance = clazz.newInstance();
        Method method = clazz.getDeclaredMethod("sout", null);
        method.invoke(instance,null);
        System.out.println(clazz.getClassLoader().getClass().getName());
    }
}
