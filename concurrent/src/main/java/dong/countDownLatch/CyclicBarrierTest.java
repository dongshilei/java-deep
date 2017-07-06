package dong.countDownLatch;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 使用CyclicBarrier，各线程相互等待
 * Created by DONGSHILEI on 2017/7/6.
 */
public class CyclicBarrierTest {

    public static void main(String[] args) {
        CyclicBarrier barrier = new CyclicBarrier(3);
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        threadPool.submit(new Runner(barrier,"1号选手"));
        threadPool.submit(new Runner(barrier,"2号选手"));
        threadPool.submit(new Runner(barrier,"3号选手"));
        threadPool.shutdown();
    }

    static class Runner implements Runnable{
        // 一个同步辅助类，它允许一组线程互相等待，直到到达某个公共屏障点
        private CyclicBarrier barrier;
        private String name;

        public Runner(CyclicBarrier barrier, String name) {
            this.barrier = barrier;
            this.name = name;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(1000*(new Random().nextInt(8)));
                System.out.println(name+"准备好了...");
                // barrier的await方法，在所有参与者都已经在此 barrier 上调用 await 方法之前，将一直等待。
                barrier.await();
                System.out.println(name+"起跑！");
                Thread.sleep(1000*(new Random().nextInt(8)));
                System.out.println(name+"到达终点！");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }
}
