package com.dong.jvm;

/**
 * @program: java-deep
 * @description
 * @author: DONGSHILEI
 * @create: 2020/4/29 18:04
 **/
public class PrintGCDetails {
    /**
     * -Xms60m
     * -Xmx60m
     * -Xmn20m
     * -XX:PermSize=30m
     * -XX:MaxPermSize=30m
     * -XX:+PrintGCDetails
     */
    public static void main(String[] args) {
        doTest();
    }
    public static void doTest() {
        //单位, 兆(M)
        Integer M = new Integer(1024 * 1024 * 1);
        //申请 1M 大小的内存空间
        byte[] bytes = new byte[1 * M];
        //断开引用链
        bytes = null;
        //通知 GC 收集垃圾
        System.gc();
        System.out.println("1");
        //重新申请 1M 大小的内存空间
        bytes = new byte[1 * M];
        //再次申请 1M 大小的内存空间
        bytes = new byte[1 * M];
        System.gc();
        System.out.println("2");
    }
    public static void doTest2() {
        //单位, 兆(M)
        byte[] a,b,c;
        //申请 1M 大小的内存空间
        a = new byte[1 * 1024*1024];
        //通知 GC 收集垃圾
        System.gc();
        System.out.println("1");
        //重新申请 1M 大小的内存空间
        b = new byte[1 * 1024*1024];
        //再次申请 1M 大小的内存空间
        c = new byte[1 * 1024*1024];
        System.gc();
        System.out.println("2");
    }
}
