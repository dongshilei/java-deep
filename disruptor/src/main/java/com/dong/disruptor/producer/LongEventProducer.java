package com.dong.disruptor.producer;

import com.dong.disruptor.event.LongEvent;
import com.lmax.disruptor.RingBuffer;

/**
 * @program: java-deep
 * @description
 * @author: DONGSHILEI
 * @create: 2020/6/30 17:54
 **/
public class LongEventProducer {

    private final RingBuffer<LongEvent> ringBuffer;

    public LongEventProducer(RingBuffer<LongEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    /**
     * onData用来发布事件
     */
    public void onData(long data) {
        //1、从 RingBuffer 获取下一个可以写入的事件的索引
        long sequence = ringBuffer.next();
        try {
            //2、用上面的索引取出一个空的事件（获取该序号对应的事件对象）
            LongEvent longEvent = ringBuffer.get(sequence);
            // 将数据写入事件对象
            longEvent.setValue(data);
        } finally {
            //3、发布事件到RingBuffer
            //注意，最后的 ringBuffer.publish 方法必须包含在 finally 中以确保必须得到调用；
            // 如果某个请求的 sequence 未被提交，将会堵塞后续的发布操作或者其它的 producer。
            ringBuffer.publish(sequence);
        }
    }
}
