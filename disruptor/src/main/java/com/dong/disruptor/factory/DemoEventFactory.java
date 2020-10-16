package com.dong.disruptor.factory;

import com.dong.disruptor.event.DemoEvent;
import com.lmax.disruptor.EventFactory;

/**
 * @program: java-deep
 * @description  事件工厂类
 * @author: DONGSHILEI
 * @create: 2020/7/1 17:54
 **/
public class DemoEventFactory implements EventFactory<DemoEvent> {
    @Override
    public DemoEvent newInstance() {
        return new DemoEvent();
    }
}
