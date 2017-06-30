package dong.countDownLatch;

import java.util.concurrent.CountDownLatch;

/**
 * 网络检测类，继承检测父类
 * Created by DONGSHILEI on 2017/6/30.
 */
public class NetworkHealthChecker extends BaseHealthChecker {
    public NetworkHealthChecker(CountDownLatch latch) {
        super(latch, "network service");
    }

    //实现检测方法
    @Override
    public void verifyService() {
        System.out.println("checking " + this.getServiceName());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(this.getServiceName() + " is up");
    }
}
