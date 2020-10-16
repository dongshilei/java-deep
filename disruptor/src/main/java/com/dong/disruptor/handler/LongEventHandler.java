package com.dong.disruptor.handler;


import com.dong.disruptor.event.LongEvent;
import com.lmax.disruptor.EventHandler;

/**
 * @program: java-deep
 * @description
 * @author: DONGSHILEI
 * @create: 2020/6/30 17:51
 **/

public class LongEventHandler implements EventHandler<LongEvent> {
    @Override
    public void onEvent(LongEvent longEvent, long sequence, boolean endOfBatch) throws Exception {
        System.out.println((Thread.currentThread().getName() + "接收到事件：" + longEvent.getValue()));
    }
}
