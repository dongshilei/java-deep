package dong.future;

/**
 * 主要完成的功能包括：
 * 1. 返回一个FutureData；
 * 2.开启一个线程用于构造RealData。
 * Created by DONGSHILEI on 2017/6/29.
 */
public class Client {

    public Data request(final String str) {
        final FutureData futureData = new FutureData();
        //获取真实数据的线程
        new Thread((new Runnable() {
            @Override
            public void run() {
                RealData realData = new RealData(str);
                futureData.setRealData(realData);
            }
        })).start();
        //返回Future数据，此时futureData.realData为null
        return futureData;
    }
}
