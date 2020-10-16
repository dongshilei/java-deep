package com.dong.disruptor.handler;

import com.dong.disruptor.event.DemoEvent;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

/**
 * @program: java-deep
 * @description 事件消费者类
 * @author: DONGSHILEI
 * @create: 2020/7/1 17:55
 **/
public class DemoEventHandler implements EventHandler<DemoEvent>, WorkHandler<DemoEvent> {

    private final String handlerName;

    public DemoEventHandler(String handlerName) {
        this.handlerName = handlerName;
    }

    @Override
    public void onEvent(DemoEvent event, long sequence, boolean endOfBatch) throws Exception {
        event.getParams().add(Thread.currentThread().getName());
        System.out.println(Thread.currentThread().getName()+"_"+ handlerName +" 处理结果： "+ event.toString());
    }

    @Override
    public void onEvent(DemoEvent event) throws Exception {
        event.getParams().add(Thread.currentThread().getName());
        System.out.println(Thread.currentThread().getName()+"_"+ handlerName +" 处理结果： "+ event.toString());
    }
}
