package com.dong.disruptor.producer;

import com.dong.disruptor.event.DemoEvent;
import com.lmax.disruptor.RingBuffer;

/**
 * @program: java-deep
 * @description 自定义事件生产者
 * @author: DONGSHILEI
 * @create: 2020/7/1 19:56
 **/
public class DemoEventProducer extends AbstractEventProducer<DemoEvent>{

    public DemoEventProducer(RingBuffer<DemoEvent> ringBuffer) {
        super(ringBuffer);
    }

    @Override
    public void onData(Object data) {
        //1、从 RingBuffer 获取下一个可以写入的事件的索引
        long sequence = ringBuffer.next();
        try {
            //2、用上面的索引取出一个空的事件（获取该序号对应的事件对象）
            DemoEvent demoEvent = ringBuffer.get(sequence);
            // 将数据写入事件对象
            demoEvent.getParams().add((String) data);
        } finally {
            //3、发布事件到RingBuffer
            //注意，最后的 ringBuffer.publish 方法必须包含在 finally 中以确保必须得到调用；
            // 如果某个请求的 sequence 未被提交，将会堵塞后续的发布操作或者其它的 producer。
            ringBuffer.publish(sequence);
        }
    }
}
