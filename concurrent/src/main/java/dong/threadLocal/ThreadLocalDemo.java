package dong.threadLocal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ThreadLocal 线程局部变量，只有当前线程可以访问，自然保证访问的数据是线程安全的
 * Created by DONGSHILEI on 2017/7/16.
 */
public class ThreadLocalDemo {
    // SimpleDateFormat.parse()不是线程安全的
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    static class ParseDate implements Runnable{
        private int i= 0;
        public ParseDate(int i) {
            this.i = i;
        }
        @Override
        public void run() {
            try {
                Date parse = sdf.parse("2017-07-16 20:56:" + i % 60);
                System.out.println(i+":"+parse);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    private static ThreadLocal<SimpleDateFormat> t1 = new  ThreadLocal<SimpleDateFormat>();

    static class ParseDate2 implements Runnable{
        int i = 0;

        public ParseDate2(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            try {
                // 如果当前线程不持有SimpleDateFormat对象实例就创建一个并设置到当前线程中，如果已经持有，则直接使用
                if (t1.get()==null){
                    t1.set(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"));
                    System.out.println(Thread.currentThread().getName()+" 创建了SimpleDateFormat");
                }
                Date parse = t1.get().parse("2017-07-16 20:56:" + i % 60);
                System.out.println(i+" : "+ parse);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 1000; i++) {
            //threadPool.execute(new ParseDate(i));
            threadPool.execute(new ParseDate2(i));
        }
    }
}
