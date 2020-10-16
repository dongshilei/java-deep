package com.dong.disruptor;

import com.dong.disruptor.event.LongEvent;
import com.dong.disruptor.factory.LongEventFactory;
import com.dong.disruptor.handler.LongEventHandler;
import com.dong.disruptor.handler.LongEventWorkHandler;
import com.dong.disruptor.producer.LongEventProducer;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.EventHandlerGroup;
import com.lmax.disruptor.dsl.ProducerType;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @program: java-deep
 * @description
 * @author: DONGSHILEI
 * @create: 2020/6/30 17:57
 **/
public class LongEventMain {
    private int bufferSize;
    private LongEventFactory factory;
    private Disruptor<LongEvent> disruptor;

    @Before
    public void init() {
        //指定环形缓冲区的大小，必须为2的幂次方，因此底层会通过取模运算来确定位置，2的幂次方性能会更好
        bufferSize = 1024*1024;
        //为Event创建一个Factory
        factory = new LongEventFactory();
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
        //第四个参数SINGLE(单个生产者)和MULTI(多个生产者)
        //第五个参数定义一种关于生成和消费的策略
        disruptor = new Disruptor<LongEvent>(
                factory,
                bufferSize,
                new DisruptorThreadFactory(),
                ProducerType.SINGLE,
                new YieldingWaitStrategy());
    }

    /**
     * 一个生产者，多个消费者重复消费消息事件
     *
     * @throws InterruptedException
     */
    @Test
    public void test1() throws InterruptedException {
        //创建2个消费者来处理同一个生产者发的消息事件，两个消费者处理相同的消息事件
        LongEventHandler[] handlers = new LongEventHandler[2];
        handlers[0] = new LongEventHandler();
        handlers[1] = new LongEventHandler();
        disruptor.handleEventsWith(handlers);
        disruptor.start();
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();
        LongEventProducer producer = new LongEventProducer(ringBuffer);
        for (int i = 0; i < 10; i++) {
            producer.onData(i);
            System.out.println(("Producer发布事件：" + i));
            Thread.sleep(100);
        }
    }

    /**
     * 多个消费者不重复、依次消费消息事件,
     */
    @Test
    public void test2() throws InterruptedException {
        //创建10个消费者来处理同一个生产者发的消息事件，10个消费者不重复、依次处理消息事件
        LongEventWorkHandler[] handlers = new LongEventWorkHandler[10];
        for (int i = 0; i < 10; i++) {
            handlers[i] = new LongEventWorkHandler();
        }
        disruptor.handleEventsWithWorkerPool(handlers);
        disruptor.start();
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();
        LongEventProducer producer = new LongEventProducer(ringBuffer);
        for (int i = 0; i < 10; i++) {
            producer.onData(i);
            System.out.println(("Producer发布事件：" + i));
            Thread.sleep(100);
        }
    }

    /**
     * 多个生产者多个消费者
     */
    @Test
    public void test3() {
        disruptor = new Disruptor<LongEvent>(
                factory,
                bufferSize,
                new DisruptorThreadFactory(),
                ProducerType.MULTI,
                new YieldingWaitStrategy());
        //创建10个消费者来处理同一个生产者发的消息事件，10个消费者不重复、依次处理消息事件
        LongEventWorkHandler[] handlers = new LongEventWorkHandler[10];
        for (int i = 0; i < 10; i++) {
            handlers[i] = new LongEventWorkHandler();
        }
        disruptor.handleEventsWithWorkerPool(handlers);
        RingBuffer<LongEvent> ringBuffer = disruptor.start();
        LongEventProducer producer1 = new LongEventProducer(ringBuffer);
        LongEventProducer producer2 = new LongEventProducer(ringBuffer);
        Random random = new Random(1000);
        for (int i = 0; i < 1000000; i++) {
            producer1.onData(random.nextLong());
            producer2.onData(random.nextLong());
        }
        while (true){
        }
    }

}
