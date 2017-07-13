package dong.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by DONGSHILEI on 2017/7/12.
 */
public class LockDemo {
    private Lock lock = new ReentrantLock();
    private int count = 0;

    public int inc() {
        lock.lock();
        int newCount = ++count;
        lock.unlock();
        return newCount;
    }

    public static void main(String[] args) {
        LockDemo lockDemo = new LockDemo();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    int inc = lockDemo.inc();
                    System.out.println(String.format(" %s inc : %d", Thread.currentThread().getName(), inc));
                }
            }
        }, "t1").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    int inc = lockDemo.inc();
                    System.out.println(String.format(" %s inc : %d\n", Thread.currentThread().getName(), inc));
                }
            }
        }, "t2").start();
    }
}
