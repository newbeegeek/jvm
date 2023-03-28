package com.newbee.jvm.classloader;

import java.io.FileInputStream;
import java.lang.reflect.Method;

/**
 * @Description: 打破双亲委派类加载器：重写loadclass来实现打破双亲委派机制
 * @Title: BreakClassLoader
 * @Package com.newbee.jvm.classloader
 * @Author: NewBee.Geek
 * @CreateTime: 2023/3/28 8:20 PM
 */
public class BreakClassLoader extends ClassLoader{

    private String classPath;

    public BreakClassLoader (String classPath) {
        this.classPath = classPath;
    }

    /***
     * @author [NewBee.Geek]
     * @description
     * @date 2023/3/28 8:23 PM
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
     * @description 加载类
     * @date 2023/3/28 8:24 PM
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
     * @description 打破双亲委派核心实现
     * @date 2023/3/28 8:25 PM
     */
    @Override
    protected Class<?> loadClass(String name, boolean resolve)
            throws ClassNotFoundException {
        synchronized (getClassLoadingLock(name)) {
            // First, check if the class has already been loaded
            Class<?> c = findLoadedClass(name);
            /**
             * 去除父类加载器实现
             */
            if (c == null) {
                // If still not found, then invoke findClass in order
                // to find the class.
                long t1 = System.nanoTime();
                if (!name.startsWith("com.newbee.jvm.classloader.BreakTest")){
                    //原生类或者jar包类,保持双亲委派机制委托
                    c = this.getParent().loadClass(name);
                } else {
                    //自己的类用打破双亲委派机制加载
                    c = findClass(name);
                }
                // this is the defining class loader; record the stats
                sun.misc.PerfCounter.getFindClassTime().addElapsedTimeFrom(t1);
                sun.misc.PerfCounter.getFindClasses().increment();
            }
            if (resolve) {
                resolveClass(c);
            }
            return c;
        }
    }

    /***
     * @author [NewBee.Geek]
     * @description 保留项目中的BreakTest.class
     * 1）如果打破，说明由BreakClassLoader加载
     * 2）不由AppClassLoader加载
     * 3）注释重写的loadclass方法，即由AppclassLoader加载
     * @date 2023/3/28 8:30 PM
     */
    public static void main(String[] args) throws Exception {
        BreakClassLoader classLoader = new BreakClassLoader("/Users/caijunhao/Java/study/jvm/jvm/target/classes");
        Class<?> clazz = classLoader.loadClass("com.newbee.jvm.classloader.BreakTest");
        Object instance = clazz.newInstance();
        Method method = clazz.getDeclaredMethod("sout", null);
        method.invoke(instance,null);
        System.out.println(clazz.getClassLoader().getClass().getName());
    }

}
