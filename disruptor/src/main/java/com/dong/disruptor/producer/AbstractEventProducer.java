package com.dong.disruptor.producer;

import com.dong.disruptor.event.DemoEvent;
import com.lmax.disruptor.RingBuffer;

/**
 * @program: java-deep
 * @description  生产者抽象类
 * @author: DONGSHILEI
 * @create: 2020/7/1 18:03
 **/
public abstract class AbstractEventProducer<T> implements EventProducer {

    /**
     * 环形队列
     */
    protected final RingBuffer<T> ringBuffer;

    public AbstractEventProducer(RingBuffer<T> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    @Override
    public abstract void onData(Object o) ;


}
