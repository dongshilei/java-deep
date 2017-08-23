package dong.exchanger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Exchanger 类表示一种会合点，两个线程可以在这里交换对象。
 * 两个线程各自调用 exchange 方法进行交换，当线程 A 调用 Exchange 对象的 exchange 方法后，
 * 它会陷入阻塞状态，直到线程B也调用了 exchange 方法，然后以线程安全的方式交换数据，之后线程A和B继续运行。
 * Created by DONGSHILEI on 2017/8/23
 */
public class ExchangerDemo {
    private static Exchanger<String> exchanger = new Exchanger<>();

    public static void print(String msg){
        SimpleDateFormat sdf = new SimpleDateFormat("[yyyy-MM-dd hh:mm:ss:SSS]");
        System.out.println(sdf.format(new Date())+msg);
    }

    public static void testRunner(String data){
        new Thread(new Runnable() {
            @Override
            public void run() {
                print(Thread.currentThread().getName()+" receive "+data);
                try {
                    //交换数据，设置超时时间（或者不设置）
                    String exchange = exchanger.exchange(data, 100, TimeUnit.SECONDS);
                    print(Thread.currentThread().getName()+" after exchange has "+exchange);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void main(String[] args) throws InterruptedException {
        testRunner("hello");
        Thread.sleep(5000);
        testRunner("world");
    }
}
