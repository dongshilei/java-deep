package com.dong.disruptor.handler;

import com.dong.disruptor.event.LongEvent;
import com.lmax.disruptor.WorkHandler;

/**
 * @program: java-deep
 * @description
 * @author: DONGSHILEI
 * @create: 2020/7/1 14:35
 **/
public class LongEventWorkHandler implements WorkHandler<LongEvent> {
    @Override
    public void onEvent(LongEvent event) {
        System.out.println(Thread.currentThread().getName() + "消费者消费了消息：" + event.getValue());
    }
}
