package dong.threadPool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 验证ArrayList和CopyOnWriteArrayList线程安全性
 */
public class CopyOnWriteArrayListTest implements Runnable{

    private List<String> list;
    private CountDownLatch latch;
    @Override
    public void run() {
        list.add("test"+System.currentTimeMillis());
        latch.countDown();
    }
    public CopyOnWriteArrayListTest(List<String> list,CountDownLatch latch) {
        this.list = list;
        this.latch = latch;
    }

    /**
     * 测试ArrayList并发
     * 使用线程池，向ArrayList集合中插入20000个对象
     * 当所有线程执行完时，多试几次，会发现集合中不足20000个
     * ArrayList 线程不安全
     */
    public static void testArrayList(){
        ExecutorService threadPool = Executors.newFixedThreadPool(50);
        List<String> list = new ArrayList<>();
        CountDownLatch latch = new CountDownLatch(20000);
        for (int i=0;i<20000;i++) {
            threadPool.execute(new CopyOnWriteArrayListTest(list,latch));
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("ArrayList  size:"+list.size());
    }

    /**
     * 测试ArrayList并发
     * 使用线程池，向CopyOnWriteArrayList集合中插入20000个对象
     * 当所有线程执行完时，多试几次，会发现集合中肯定有20000个
     * CopyOnWriteArrayList 线程安全
     */
    public static void testCopyOnWriteArrayList(){
        ExecutorService threadPool = Executors.newFixedThreadPool(50);
        List<String> list = new CopyOnWriteArrayList<>();
        CountDownLatch latch = new CountDownLatch(20000);
        for (int i=0;i<20000;i++) {
            threadPool.execute(new CopyOnWriteArrayListTest(list,latch));
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("CopyOnWriteArrayList size:"+list.size());

    }
    public static void main(String[] args)  {
        testArrayList();
        testCopyOnWriteArrayList();
        System.exit(1);
    }

}
