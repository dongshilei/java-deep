package dong.disruptor;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.nio.ByteBuffer;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Disruptor性能比BlockingQueue至少高出一个数量级以上
 * Created by DONGSHILEI on 2017/6/29.
 */
public class DataMain {
    public static void main(String[] args) throws InterruptedException {
        Executor executor = Executors.newCachedThreadPool();
        TaskDataFactory factory = new TaskDataFactory();
        //设置缓冲区大小，必须为2的整数次幂
        int bufferSize = 1024;
        //创建Disruptor对象
        Disruptor<TaskData> disruptor = new Disruptor<TaskData>(factory, bufferSize, executor,
                ProducerType.MULTI, new BlockingWaitStrategy());
        //设置用于处理数据的消费者，disruptor会为每个消费者者实例映射到一个线程中
        disruptor.handleEventsWithWorkerPool(new Consumer(),new Consumer(),new Consumer());
        //启动并初始化disruptor系统
        disruptor.start();
        //从Disruptor对象中获取环形缓冲区
        RingBuffer<TaskData> ringBuffer = disruptor.getRingBuffer();
        //创建一个生产者线程，不断向缓冲区存入数据
        Producer producer = new Producer(ringBuffer);
        ByteBuffer bb = ByteBuffer.allocate(8);
        for (long i=0;true;i++){
            bb.putLong(0,i);
            producer.pushData(bb);
            Thread.sleep(100);
            System.out.println(" add data "+i);
        }
    }
}
