package dong.threadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Executors类，提供了一系列工厂方法用于创建线程池，返回的线程池都实现了ExecutorService接口。
 * Created by DONGSHILEI on 2017/6/30.
 */
public class ExecutorsTest {

    public static void test(ExecutorService threadPool){
        for (int i = 1; i < 5; i++) {
            final int taskId = i;
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    for (int j = 1; j < 5 ; j++) {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("第" + taskId + "次任务的第" + j + "次执行");
                    }
                }
            });
        }
        //threadPool.shutdown();
    }

    /**
     * ScheduledThreadPool可以定时的或延时的执行任务。
     */
    public static void testScheduledPool(){
        ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(1);
        //5秒后执行任务 --延时执行
        threadPool.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("爆炸");
            }
        },5, TimeUnit.SECONDS);
        // 5秒后执行，以后每2秒执行一次  --周期性执行
        threadPool.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("爆炸啦");
            }
        },5,2,TimeUnit.SECONDS);
    }

    public static void main(String[] args) {
        /**
         *  测试FixedThreadPool 结果如下
         *
         *
         第1次任务的第1次执行
         第2次任务的第1次执行
         第3次任务的第1次执行
         第3次任务的第2次执行
         第1次任务的第2次执行
         第2次任务的第2次执行
         第1次任务的第3次执行
         第3次任务的第3次执行
         第2次任务的第3次执行
         第3次任务的第4次执行
         第2次任务的第4次执行
         第1次任务的第4次执行
         第4次任务的第1次执行
         第4次任务的第2次执行
         第4次任务的第3次执行
         第4次任务的第4次执行

         *结论：
         * 前3个任务首先执行完，然后空闲下来的线程去执行第4个任务，在FixedThreadPool中，
         * 有一个固定大小的池，如果当前需要执行的任务超过了池大小，那么多于的任务等待状态，
         * 直到有空闲下来的线程执行任务，而当执行的任务小于池大小，空闲的线程也不会去销毁。
         */

        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        test(threadPool);
        /**
         *  测试CachedThreadPool 结果如下
         *
         第1次任务的第1次执行
         第3次任务的第1次执行
         第4次任务的第1次执行
         第2次任务的第1次执行
         第1次任务的第2次执行
         第3次任务的第2次执行
         第4次任务的第2次执行
         第2次任务的第2次执行
         第1次任务的第3次执行
         第4次任务的第3次执行
         第3次任务的第3次执行
         第2次任务的第3次执行
         第2次任务的第4次执行
         第4次任务的第4次执行
         第1次任务的第4次执行
         第3次任务的第4次执行
         *
         * 结论：4个任务是交替执行的，CachedThreadPool会创建一个缓存区，
         * 将初始化的线程缓存起来，如果线程有可用的，就使用之前创建好的线程，
         * 如果没有可用的，就新创建线程，终止并且从缓存中移除已有60秒未被使用的线程。
         */
        //ExecutorService threadPool = Executors.newCachedThreadPool();
        //test(threadPool);
        /**
         * 测试 SingleThreadExecutor  结果如下
         *
         第1次任务的第1次执行
         第1次任务的第2次执行
         第1次任务的第3次执行
         第1次任务的第4次执行
         第2次任务的第1次执行
         第2次任务的第2次执行
         第2次任务的第3次执行
         第2次任务的第4次执行
         第3次任务的第1次执行
         第3次任务的第2次执行
         第3次任务的第3次执行
         第3次任务的第4次执行
         第4次任务的第1次执行
         第4次任务的第2次执行
         第4次任务的第3次执行
         第4次任务的第4次执行
         *
         * 结论：  4个任务是顺序执行的，SingleThreadExecutor得到的是一个单个的线程，
         * 这个线程会保证你的任务执行完成，如果当前线程意外终止，会创建一个新线程继续执行任务，
         * 这和我们直接创建线程不同，也和newFixedThreadPool(1)不同
         */
        //ExecutorService threadPool = Executors.newSingleThreadExecutor();
        //test(threadPool);


        //testScheduledPool();
    }

}
