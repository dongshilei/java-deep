package com.dong.disruptor.translator;

import com.dong.disruptor.event.LongEvent;
import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;

/**
 * @program: java-deep
 * @description
 * @author: DONGSHILEI
 * @create: 2020/7/1 11:39
 **/
public class LongEventProducerWithTranslator {

    private final RingBuffer<LongEvent> ringBuffer;

    public LongEventProducerWithTranslator(RingBuffer<LongEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    private static final EventTranslatorOneArg<LongEvent,Long> TRANSLATOR = (event, sequence, arg0) -> {
        event.setValue(arg0);
    };
    public void onData(Long data){
        ringBuffer.publishEvent(TRANSLATOR,data);
    }

}
