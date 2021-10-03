package com.test;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

/**
 * @author 吴振
 * @since 2021/9/21 下午11:28
 */
public class MyClassLoader extends ClassLoader{
    public static void main(String[] args) throws Exception{
        // 得到class对象
        MyClassLoader myClassLoader = new MyClassLoader();
        Class<?> clazz = myClassLoader.loadClass("resource/Hello.xlass");
        Object hello = clazz.getDeclaredConstructor().newInstance();

        // 调用hello方法
        Method method = clazz.getMethod("hello");
        method.invoke(hello);
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {

        File classFile = new File(name);
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(classFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            // 读取数据
            int length = inputStream.available();
            byte[] byteArray = new byte[length];
            inputStream.read(byteArray);
            // 转换
            byte[] classBytes = decode(byteArray);
            // 通知底层定义这个类
            return defineClass("Hello", classBytes, 0, classBytes.length);
        } catch (IOException e) {
            throw new ClassNotFoundException(name, e);
        } finally {
            close(inputStream);
        }
    }

    /**
     * 解密  取反码
     *
     * @param byteArray 加密的字节数组
     * @return 解密的字节数组
     */
    private static byte[] decode(byte[] byteArray) {
        byte[] targetArray = new byte[byteArray.length];
        for (int i = 0; i < byteArray.length; i++) {
            targetArray[i] = (byte) (255 - byteArray[i]);
        }
        return targetArray;
    }

    /**
     * 关闭io
     *
     * @param res 资源
     */
    private static void close(Closeable res) {
        if (null != res) {
            try {
                res.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}


