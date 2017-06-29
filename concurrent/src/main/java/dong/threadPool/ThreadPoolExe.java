package dong.threadPool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by DONGSHILEI on 2017/6/22.
 */
public class ThreadPoolExe implements Runnable{

    private int index;

    public ThreadPoolExe(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public static void main(String[] args)  {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(4, 8,
                3, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(5),
                new ThreadPoolExecutor.DiscardOldestPolicy());
        for (int i=0;i<100;i++) {
            String task = "task@ " + i;
            System.out.println("创建任务并提交到线程池中：" + task);
            threadPoolExecutor.execute(new ThreadPoolExe(i));
        }
    }
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+"    "+getIndex());
    }
}
