package dong.countDownLatch;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 通过CountDownLatch实现最大并行
 * 当裁判员发口令后，所有运动员同时出发；
 * 当所有运动员到达重点后，裁判员宣布比赛结束
 * Created by DONGSHILEI on 2017/6/30.
 */
public class CountDownLatchDemo {
    /**
     * 裁判员
     */
    static class Referee implements Runnable{
        private CountDownLatch latch;
        private CountDownLatch latch2;

        public Referee(CountDownLatch latch,CountDownLatch latch2) {
            this.latch = latch;
            this.latch2 = latch2;
        }

        @Override
        public void run() {
            try {
                System.out.println("赛前准备工作。。");
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("裁判员：各就各位，预备 开始！");
                latch.countDown();
                try {
                    latch2.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("裁判员：比赛结束");
            }
        }
    }

    /**
     * 运动员
     */
    static class Sportsman implements Runnable{
        private CountDownLatch latch;
        private CountDownLatch latch2;
        private String name;

        public Sportsman(CountDownLatch latch,CountDownLatch latch2, String name) {
            this.latch = latch;
            this.latch2 = latch2;
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
            System.out.println("远动员："+this.name+" 起跑 "+ System.currentTimeMillis());
            try {
                Thread.sleep(new Random().nextInt(1*1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("远动员："+this.name+" 到达终点 "+ System.currentTimeMillis());
            latch2.countDown();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService threadPool = Executors.newCachedThreadPool();
        CountDownLatch latch = new CountDownLatch(1);
        CountDownLatch latch2 = new CountDownLatch(4);
        Referee referee = new Referee(latch,latch2);
        Sportsman s1 = new Sportsman(latch,latch2, "博尔特");
        Sportsman s2 = new Sportsman(latch,latch2, "刘翔");
        Sportsman s3 = new Sportsman(latch,latch2, "苏炳添");
        Sportsman s4 = new Sportsman(latch,latch2, "姚明");
        threadPool.execute(referee);
        threadPool.execute(s1);
        threadPool.execute(s2);
        threadPool.execute(s3);
        threadPool.execute(s4);
        Thread.sleep(1000*10);
        //System.out.println("比赛结束");
        if (latch2.getCount()==0) {
            threadPool.shutdown();
        }
    }
}
