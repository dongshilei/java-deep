package dong.future;

/**
 * Created by DONGSHILEI on 2017/6/29.
 */
public class RealData implements Data {
    protected String data;

    public RealData(String data) {
        //利用sleep模拟RealData构造过程非常缓慢
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.data = data;
    }

    @Override
    public String getResult() throws InterruptedException {
        return data;
    }
}
