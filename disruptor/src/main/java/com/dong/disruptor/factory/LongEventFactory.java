package com.dong.disruptor.factory;

import com.dong.disruptor.event.LongEvent;
import com.lmax.disruptor.EventFactory;

/**
 * @program: java-deep
 * @description
 * @author: DONGSHILEI
 * @create: 2020/6/30 17:50
 **/
public class LongEventFactory implements EventFactory<LongEvent> {
    @Override
    public LongEvent newInstance() {
        return new LongEvent();
    }
}
