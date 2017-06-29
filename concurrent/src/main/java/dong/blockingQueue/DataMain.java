package dong.blockingQueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by DONGSHILEI on 2017/6/29.
 */
public class DataMain {
    public static void main(String[] args) throws InterruptedException {
        //建立缓冲区
        BlockingQueue<PCData> queue = new LinkedBlockingQueue<PCData>(10);
        Producer p1 = new Producer(queue); //建立生产者
        Producer p2 = new Producer(queue); //建立生产者
        Producer p3 = new Producer(queue); //建立生产者

        Consumer c1 = new Consumer(queue);//建立消费者
        Consumer c2 = new Consumer(queue);//建立消费者
        Consumer c3 = new Consumer(queue);//建立消费者

        //建立线程池
        ExecutorService service = Executors.newCachedThreadPool();
        //运行生产者
        service.execute(p1);
        service.execute(p2);
        service.execute(p3);
        //运行消费者
        service.execute(c1);
        service.execute(c2);
        service.execute(c3);
        Thread.sleep(10*1000);
        //停止生产者
        p1.stop();
        p2.stop();
        p3.stop();
        Thread.sleep(3000);
        //关闭线程池
        service.shutdown();
    }
}
