package dong.blockingQueue;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by DONGSHILEI on 2017/6/29.
 */
public class Producer implements Runnable {
    private volatile boolean isRunning = true;
    private BlockingQueue<PCData> queue; //内存缓冲区
    private static AtomicInteger count = new AtomicInteger();//总数，原子操作
    private static final int SLEEPTIME = 100;

    public Producer(BlockingQueue<PCData> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        PCData data = null;
        Random r = new Random();
        System.out.println(" start producer id =" + Thread.currentThread().getName());
        try {
            while (isRunning) {
                Thread.sleep(r.nextInt(SLEEPTIME));
                data = new PCData(count.incrementAndGet()); //构造任务数据
                boolean offer = queue.offer(data, 2, TimeUnit.SECONDS);//提交数据到缓冲区
                if (offer){
                    System.out.println(data+" is put into queue");
                } else {
                    System.out.println(data+" failed to put into queue");
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }
    public void stop(){
        isRunning = false;
    }
}
