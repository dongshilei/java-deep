package dong.countDownLatch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 主启动类，负责初始化闭锁，然后等待，直到所有服务都被检测完。
 * Created by DONGSHILEI on 2017/6/30.
 */
public class ApplicationStartupUtil {
    //需要检测的服务集合
    private static List<BaseHealthChecker> services;
    private static CountDownLatch latch;
    private static ExecutorService threadPool;

    public static boolean checkExternalServices() throws InterruptedException {
        latch = new CountDownLatch(3);
        services = new ArrayList<>();
        //初始化服务集合 分别将要检测的服务放入集合
        services.add(new NetworkHealthChecker(latch));
        services.add(new DatabaseHealthChecker(latch));
        services.add(new CacheHealthChecker(latch));
        //初始化线程池，固定大小为服务集合的大小
        threadPool = Executors.newFixedThreadPool(services.size());
        // 遍历服务集合，将待检测服务放入线程池
        for (BaseHealthChecker bhc : services) {
            threadPool.execute(bhc);
        }
        //主线程被阻塞，直到其他线程完成，通过await()方法恢复主线程执行
        latch.await();
        // 再次验证检测服务是否完成
        for (BaseHealthChecker bhc : services) {
            if (!bhc.isServiceUp()) {
                return false;
            }
        }
        return true;
    }

    public static void shutdown(){
        if (threadPool!=null){
            threadPool.shutdown();
        }
    }

}
