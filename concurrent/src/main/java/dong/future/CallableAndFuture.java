package dong.future;

import java.util.Random;
import java.util.concurrent.*;

/**
 * Callable 产生结果
 * FutureTask 获取结果
 * Created by DONGSHILEI on 2017/6/30.
 */
public class CallableAndFuture {

    /**
     * 测试Calllable与FutureTask结合使用
     * 未使用线程池
     */
    public static void test1(){
        Callable<Integer> callable = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return new Random().nextInt(100);
            }
        };
        FutureTask<Integer> future = new FutureTask<Integer>(callable);
        new Thread(future).start();
        try {
            //模拟做其他事情
            Thread.sleep(2000);
            //获取数据
            System.out.println(future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
    /**
     * 测试Calllable与FutureTask结合使用
     * 使用线程池
     */
    public static void test2(){
        //创建线程池
        ExecutorService service = Executors.newSingleThreadExecutor();
        Future<Integer> future = service.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return new Random().nextInt(100);
            }
        });
        try {
            //模拟做其他事情
            Thread.sleep(2000);
            //获取数据
            System.out.println(future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            service.shutdown();
        }
    }

    /**
     *  执行多个带返回值的任务，并取得多个返回值
     */
    public static  void test3(){
        ExecutorService service = Executors.newCachedThreadPool();
        ExecutorCompletionService<Integer> es = new ExecutorCompletionService<>(service);
        for (int i = 0; i < 5; i++) {
            final int taskId = i;
            es.submit(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    return taskId;
                }
            });
        }
        for (int i = 0; i < 5; i++) {
            try {
                System.out.println(es.take().get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            service.shutdown();
        }
    }
    public static void main(String[] args) {
//        test1();
//        test2();
        test3();
    }
}
