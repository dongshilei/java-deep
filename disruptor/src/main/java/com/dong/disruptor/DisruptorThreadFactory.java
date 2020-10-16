package com.dong.disruptor;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @program: java-deep
 * @description 线程工厂，为了保证创建出来的线程都具有相同的特性，譬如是否为守护进程，优先级设置等
 *  每个handler在处理事件消息时，都会单独启用一个线程
 * @author: DONGSHILEI
 * @create: 2020/7/1 11:58
 **/
public class DisruptorThreadFactory implements ThreadFactory {
    //线程数目
    private static final AtomicInteger poolnum = new AtomicInteger(1);
    private final ThreadGroup group;
    //线程数目
    private final AtomicInteger threadnum = new AtomicInteger(1);
    //为每个创建的线程添加前缀
    private final String namePrefix;

    public DisruptorThreadFactory() {
        SecurityManager sm = System.getSecurityManager();
        //取得线程组
        group = (sm != null) ? sm.getThreadGroup() : Thread.currentThread().getThreadGroup();
        namePrefix = "pool-" + poolnum.getAndIncrement() + "-thread-";
    }

    @Override
    public Thread newThread(Runnable r) {
        //创建线程，设置线程组和线程名
        Thread t = new Thread(group, r, namePrefix + threadnum.getAndIncrement(), 0);
        if (t.isDaemon()) {
            t.setDaemon(false);
        }
        if (t.getPriority() != Thread.NORM_PRIORITY) {
            t.setPriority(Thread.NORM_PRIORITY);
        }
        return t;
    }
}
