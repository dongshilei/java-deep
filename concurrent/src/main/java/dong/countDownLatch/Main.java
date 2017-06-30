package dong.countDownLatch;

/**
 * Created by DONGSHILEI on 2017/6/30.
 */
public class Main {
    public static void main(String[] args) {
        boolean flag = false;
        try {
            flag = ApplicationStartupUtil.checkExternalServices();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("External services validation completed !! Result was :" + flag);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            ApplicationStartupUtil.shutdown();
        }
    }
}
