package dong.future;

/**
 * Created by DONGSHILEI on 2017/6/29.
 */
public class AppMain {

    public static void main(String[] args) throws InterruptedException {
        Client client = new Client();
        //这里会立即返回，因为获取的是FutureData，而非RealData
        Data data = client.request("name");
        //这里可以用一个sleep代替对其他业务逻辑的处理
        //在处理这些业务逻辑过程中，RealData也正在创建，从而充分了利用等待时间
        Thread.sleep(1000);
        System.out.println("数据="+data.getResult());
    }
}
