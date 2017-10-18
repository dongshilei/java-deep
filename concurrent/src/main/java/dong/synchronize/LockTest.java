package dong.synchronize;

/**
 * 两个线程，交替打印A、B
 * Created by DONGSHILEI on 2017/10/17
 */
public class LockTest {

    public static void main(String[] args) {
        //公共对象，在线程获得该对象时加锁
        Info info = new Info();
        ThreadA a = new ThreadA(info);
        ThreadB b = new ThreadB(info);
        Thread t1 = new Thread(a);
        Thread t2 = new Thread(b);
        t1.start();
        t2.start();

    }
}
