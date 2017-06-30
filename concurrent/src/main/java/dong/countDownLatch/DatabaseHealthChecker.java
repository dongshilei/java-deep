package dong.countDownLatch;

import java.util.concurrent.CountDownLatch;

/**
 * 数据检测类，继承检测父类
 * Created by DONGSHILEI on 2017/6/30.
 */
public class DatabaseHealthChecker extends BaseHealthChecker {
    public DatabaseHealthChecker(CountDownLatch latch) {
        super(latch, "database service");
    }

    @Override
    public void verifyService() {
        System.out.println("checking " + this.getServiceName());
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(this.getServiceName() + " is up");
    }
}
