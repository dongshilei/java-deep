package com.dong.disruptor.producer;

/**
 * @program: java-deep
 * @description 生产者抽象接口
 * @author: DONGSHILEI
 * @create: 2020/7/1 18:01
 **/
public interface EventProducer {

    /**
     * 发布事件接口
     * @param data
     */
    public void onData(Object data);
}
