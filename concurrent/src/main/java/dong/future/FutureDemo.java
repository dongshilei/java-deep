package dong.future;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 在执行器中取消任务
 * Created by DONGSHILEI on 2017/7/7.
 */
public class FutureDemo {
    static class Task implements Callable<String>{
        private String name;

        public Task(String name) {
            this.name = name;
        }

        @Override
        public String call() throws Exception {
            while (true){
                System.out.println("Task:"+name+" is running");
                Thread.sleep(500);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService threadPool = Executors.newCachedThreadPool();
        Future<String> future1 = threadPool.submit(new Task("任务1"));
        Future<String> future2 = threadPool.submit(new Task("任务2"));
        Future<String> future3 = threadPool.submit(new Task("任务3"));
        Thread.sleep(5000);
        //取消任务
        future1.cancel(true);
        future2.cancel(true);
        future3.cancel(true);
        System.out.println("future1 "+future1.isCancelled()+" "+future1.isDone());
        System.out.println("future2 "+future2.isCancelled()+" "+future2.isDone());
        System.out.println("future3 "+future3.isCancelled()+" "+future3.isDone());
        threadPool.shutdown();
    }
}
