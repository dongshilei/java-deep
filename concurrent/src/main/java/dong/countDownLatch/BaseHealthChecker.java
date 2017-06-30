package dong.countDownLatch;

import java.util.concurrent.CountDownLatch;

/**
 * 负责所有特定的外部服务健康的检测
 * Created by DONGSHILEI on 2017/6/30.
 */
public abstract class BaseHealthChecker implements Runnable {

    private CountDownLatch latch;
    private String serviceName;
    private boolean serviceUp;

    public BaseHealthChecker(CountDownLatch latch, String serviceName) {
        this.latch = latch;
        this.serviceName = serviceName;
    }
    //检测方法，待子类实现
    public abstract void verifyService();


    @Override
    public void run() {
        try {
            verifyService();
            serviceUp = true;
        } catch (Throwable t) {
            t.printStackTrace(System.err);
            serviceUp = false;
        } finally {
            if (latch != null) {
                //检测任务结束
                latch.countDown();
            }
        }
    }

    public CountDownLatch getLatch() {
        return latch;
    }

    public String getServiceName() {
        return serviceName;
    }

    public boolean isServiceUp() {
        return serviceUp;
    }
}
