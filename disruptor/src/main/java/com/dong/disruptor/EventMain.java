package com.dong.disruptor;

import com.dong.disruptor.event.DemoEvent;
import com.dong.disruptor.factory.DemoEventFactory;
import com.dong.disruptor.handler.DemoEventHandler;
import com.dong.disruptor.producer.DemoEventProducer;
import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.EventHandlerGroup;
import com.lmax.disruptor.dsl.ProducerType;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @program: java-deep
 * @description
 * @author: DONGSHILEI
 * @create: 2020/7/1 20:02
 **/
public class EventMain {

    private int bufferSize;
    private EventFactory<DemoEvent> factory;
    private Disruptor<DemoEvent> disruptor;
    private DemoEventHandler handler1;
    private DemoEventHandler handler2;
    private DemoEventHandler handler3;
    private DemoEventHandler handler4;
    private DemoEventHandler handler5;

    @Before
    public void init() {
        //指定环形缓冲区的大小，必须为2的幂次方，因此底层会通过取模运算来确定位置，2的幂次方性能会更好
        bufferSize = 16;
        //创建事件工厂类
        factory = new DemoEventFactory();
        /**
         *
         * BlockingWaitStrategy 是最低效的策略，但其对CPU的消耗最小并且在各种不同部署环境中能提供更加一致的性能表现
         * WaitStrategy BLOCKING_WAIT = new BlockingWaitStrategy();
         * SleepingWaitStrategy 的性能表现跟BlockingWaitStrategy差不多，对CPU的消耗也类似，但其对生产者线程的影响最小，适合用于异步日志类似的场景
         * WaitStrategy SLEEPING_WAIT = new SleepingWaitStrategy();
         * YieldingWaitStrategy 的性能是最好的，适合用于低延迟的系统。在要求极高性能且事件处理线数小于CPU逻辑核心数的场景中，推荐使用此策略；例如，CPU开启超线程的特性
         * WaitStrategy YIELDING_WAIT = new YieldingWaitStrategy();
         */
        //创建disruptor
        //第一个参数为工厂对象，用于创建LongEvent，LongEvent承载着消费的数据
        //第二个参数为环形缓冲区大小
        //第三个参数为线程工厂
        //第四个参数SINGLE(单个生产者)和MULTI(多个生产者)  SINGLE性能最优
        //第五个参数定义一种关于生成和消费的策略
        disruptor = new Disruptor<DemoEvent>(factory,
                bufferSize,
                new DisruptorThreadFactory(),
                ProducerType.SINGLE,
                new YieldingWaitStrategy());

        handler1 = new DemoEventHandler("handler1");
        handler2 = new DemoEventHandler("handler2");
        handler3 = new DemoEventHandler("handler3");
        handler4 = new DemoEventHandler("handler4");
        handler5 = new DemoEventHandler("handler5");
    }

    /**
     * Disruptor#handleEventsWith并行操作
     * 方法一：Disruptor#handleEventsWith(handler1,handler2,handler3)
     * 方法二：
     * Disruptor#handleEventsWith(handler1)
     * Disruptor#handleEventsWith(handler2)
     * Disruptor#handleEventsWith(handler3)
     */
    @Test
    public void test() {
        disruptor.handleEventsWith(handler1, handler2, handler3);
        RingBuffer<DemoEvent> ringBuffer = disruptor.start();
        DemoEventProducer producer = new DemoEventProducer(ringBuffer);
        producer.onData("hello");
    }

    /**
     * Disruptor#handleEventsWith顺序串行操作
     * handler2-->handler1-->handler3
     */
    @Test
    public void test2() {
        disruptor.handleEventsWith(handler2).handleEventsWith(handler1).handleEventsWith(handler3);
        RingBuffer<DemoEvent> ringBuffer = disruptor.start();
        DemoEventProducer producer = new DemoEventProducer(ringBuffer);
        producer.onData("hello");
    }

    /**
     * 菱形操作1:使用handleEventsWith方法 先执行handler1和handler2，都完成后再执行handler3
     */
    @Test
    public void test3() {
        disruptor.handleEventsWith(handler1, handler2).handleEventsWith(handler3);
        RingBuffer<DemoEvent> ringBuffer = disruptor.start();
        DemoEventProducer producer = new DemoEventProducer(ringBuffer);
        producer.onData("hello");
    }

    /**
     * 菱形操作2:使用disruptor#handleEventsWith方法和 先执行handler1和handler2，都完成后再执行handler3
     */
    @Test
    public void test4() {
        EventHandlerGroup<DemoEvent> handlerGroup = disruptor.handleEventsWith(handler1, handler2);
        handlerGroup.then(handler3);
        RingBuffer<DemoEvent> ringBuffer = disruptor.start();
        DemoEventProducer producer = new DemoEventProducer(ringBuffer);
        producer.onData("hello");
    }

    /**
     * 六边形操作：
     */
    @Test
    public void test5() {
        disruptor.handleEventsWith(handler1, handler4);
        disruptor.after(handler1).handleEventsWith(handler2);
        disruptor.after(handler4).handleEventsWith(handler5);
        disruptor.after(handler2, handler5).handleEventsWith(handler3);
        RingBuffer<DemoEvent> ringBuffer = disruptor.start();
        DemoEventProducer producer = new DemoEventProducer(ringBuffer);
        producer.onData("hello");
    }

    /**
     * 使用 EventProcessor 消息处理器
     */
    @Test
    public void test6() throws InterruptedException {

    }

}
