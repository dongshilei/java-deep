package dong.lock;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by DONGSHILEI on 2017/7/13.
 */
public class ReadWriteLockDemo {
    private double price1;
    private double price2;
    private ReadWriteLock lock;

    public ReadWriteLockDemo() {
        price1 = 1.0;
        price2 = 2.0;
        lock = new ReentrantReadWriteLock();
    }
    public double getPrice1(){
        // 读取资源锁定
        lock.readLock().lock();
        double value = price1;
        lock.readLock().unlock();
        return value;
    }
    public double getPrice2(){
        // 读取资源锁定
        lock.readLock().lock();
        double value = price2;
        lock.readLock().unlock();
        return value;
    }
    public void setPrice(double price1,double price2){
        lock.writeLock().lock();
        this.price1 = price1;
        this.price2 = price2;
        lock.writeLock().unlock();
    }

    static class Reader implements Runnable{
        private ReadWriteLockDemo demo;

        public Reader(ReadWriteLockDemo demo) {
            this.demo = demo;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.printf("%s: Price 1: %f\n", Thread.currentThread()
                        .getName(), demo.getPrice1());
                System.out.printf("%s: Price 2: %f\n", Thread.currentThread()
                        .getName(), demo.getPrice2());
            }
        }
    }

    static class Writer implements Runnable{
        private ReadWriteLockDemo demo;

        public Writer(ReadWriteLockDemo demo) {
            this.demo = demo;
        }

        @Override
        public void run() {
            for (int i = 0; i < 3; i++) {
                System.out.printf("Writer: Attempt to modify the prices.\n");
                demo.setPrice(Math.random() * 10, Math.random( ) * 8);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.printf("Writer: Prices have been modified.\n");
                try {
                    Thread.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        ReadWriteLockDemo demo = new ReadWriteLockDemo();
        Reader readers[] = new Reader[5];
        Thread threadsReader[] = new Thread[5];
        for (int i = 0; i < 5; i++){
            readers[i] = new Reader(demo);
            threadsReader[i] = new Thread(readers[i]);
        }
        Writer writer = new Writer(demo);
        Thread threadWriter = new Thread(writer);
        for (int i = 0; i < 5; i++){
            threadsReader[i].start();
        }
        threadWriter.start();
    }
}
