package dong.disruptor;

import com.lmax.disruptor.RingBuffer;

import java.nio.ByteBuffer;

/**
 * Created by DONGSHILEI on 2017/6/29.
 */
public class Producer {
    //引入环形缓冲区
    private final RingBuffer<TaskData> ringBuffer;

    public Producer(RingBuffer<TaskData> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    /**
     * 将产生的数据推入缓冲区
     * @param bb
     */
    public void pushData(ByteBuffer bb){
        // 得到下一个可用的序列号
        long sequence = ringBuffer.next();
        try {
            //得到空闲的数据结构，并赋值
            TaskData taskData = ringBuffer.get(sequence);
            taskData.setValue(bb.getLong(0));
        } finally {
            //数据发布，只有发布后，才能被消费者看到
            ringBuffer.publish(sequence);
        }
    }
}
