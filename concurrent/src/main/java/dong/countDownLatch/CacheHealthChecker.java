package dong.countDownLatch;

import java.util.concurrent.CountDownLatch;

/**
 * 缓存检测类，继承检测父类
 * Created by DONGSHILEI on 2017/6/30.
 */
public class CacheHealthChecker extends BaseHealthChecker {
    public CacheHealthChecker(CountDownLatch latch) {
        super(latch, "cache service");
    }

    @Override
    public void verifyService() {
        System.out.println("checking " + this.getServiceName());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(this.getServiceName() + " is up");
    }
}
