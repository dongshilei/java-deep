package dong.rateLimit;

import com.google.common.util.concurrent.RateLimiter;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by DONGSHILEI on 2017/9/8
 */
public class RateLimiterDemo {
    public static void test(){
        List<String> queryNos = Arrays.asList("1","2","3","4","5","6","7");
        //每秒不会超过5个任务
        RateLimiter rateLimiter = RateLimiter.create(5);
        //线程池并发数量，与限流数量没有太大关联
        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        for (String s:queryNos){
            rateLimiter.acquire();
            threadPool.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println(s+":"+System.currentTimeMillis());
                }
            });
        }
        threadPool.shutdown();
    }
    public static void test1(){
        RateLimiter rateLimiter = RateLimiter.create(50);
        ExecutorService pool = Executors.newFixedThreadPool(5);
        while (true){
            rateLimiter.acquire();
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName()+":  "+System.currentTimeMillis());
                }
            });
        }
    }

    public static void test2(){
        //每秒不会超过2个任务
        RateLimiter rateLimiter = RateLimiter.create(2);
        while (true){
            rateLimiter.acquire();
            System.out.println(System.currentTimeMillis());
        }
    }

    public static void main(String[] args) {
        test1();
    }
}
