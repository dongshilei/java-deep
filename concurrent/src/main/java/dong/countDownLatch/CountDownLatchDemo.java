package dong.countDownLatch;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 通过CountDownLatch实现最大并行
 * Created by DONGSHILEI on 2017/6/30.
 */
public class CountDownLatchDemo {
    /**
     * 裁判员
     */
    static class Referee implements Runnable{
        private CountDownLatch latch;

        public Referee(CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public void run() {
            try {
                System.out.println("赛前准备工作。。");
                Thread.sleep(3000);
                System.out.println("裁判员：各就各位，预备 开始！");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                latch.countDown();
            }
        }
    }

    /**
     * 运动员
     */
    static class Sportsman implements Runnable{
        private CountDownLatch latch;
        private String name;

        public Sportsman(CountDownLatch latch, String name) {
            this.latch = latch;
            this.name = name;
        }

        @Override
        public void run() {
            System.out.println("远动员："+this.name+" 准备好了");
            try {
                //等待
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("远动员："+this.name+" 开始比赛 "+ System.currentTimeMillis());
            try {
                Thread.sleep(new Random().nextInt(10));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("远动员："+this.name+" 结束比赛 "+ System.currentTimeMillis());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService threadPool = Executors.newCachedThreadPool();
        CountDownLatch latch = new CountDownLatch(1);
        Referee referee = new Referee(latch);
        Sportsman s1 = new Sportsman(latch, "博尔特");
        Sportsman s2 = new Sportsman(latch, "刘翔");
        Sportsman s3 = new Sportsman(latch, "苏炳添");
        Sportsman s4 = new Sportsman(latch, "姚明");
        threadPool.execute(referee);
        threadPool.execute(s1);
        threadPool.execute(s2);
        threadPool.execute(s3);
        threadPool.execute(s4);
        Thread.sleep(1000*10);
        System.out.println("比赛结束");
        threadPool.shutdown();
    }
}
