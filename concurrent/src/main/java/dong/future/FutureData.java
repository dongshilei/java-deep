package dong.future;

/**
 * FutureData是Future模式的关键，
 * 它实际上是真实数据RealData的代理，
 * 封装了获取RealData的等待过程。
 * Created by DONGSHILEI on 2017/6/29.
 */
public class FutureData implements Data {
    // 封装的真实数据
    RealData realData =null;
    // 等待控制
    boolean isReady = false;

    public synchronized void setRealData(RealData realData){
        if (isReady) return;
        this.realData = realData;
        isReady = true;
        //RealData已经被注入到FutureData中了，通知getResult()方法
        notifyAll();
    }

    @Override
    public synchronized String getResult() throws InterruptedException {
        while (!isReady){
            //一直等到RealData注入到FutureData中
            wait();
        }
        return realData.getResult();
    }
}
