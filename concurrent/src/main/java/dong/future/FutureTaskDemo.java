package dong.future;

import java.util.concurrent.*;

/**
 * 在线程执行结束后做其他事情
 * 比如生成报表，发送通知邮件或者释放一些系统资源，FutureTask类给于我们最好的支持
 * Created by DONGSHILEI on 2017/7/7.
 */
public class FutureTaskDemo {

    static class Task implements Callable<String>{
        private String name;

        public Task(String name) {
            this.name = name;
        }

        @Override
        public String call() throws Exception {
            long d = (long) (Math.random()*10);
            System.out.println(String.format(" Task:%s will delay %d ", name,d));
            Thread.sleep(d);
            return "Hello I am "+name;
        }

        public String getName() {
            return name;
        }

    }

    //继承FutureTask并重写done方法
    static class ResultTask extends FutureTask<String>{
        private String name;

        public ResultTask(Callable<String> callable) {
            super(callable);
            this.name = ((Task)callable).getName();
        }

        // 重写done方法，定义任务执行完成后的处理逻辑
        @Override
        protected void done() {
            if (isCancelled()) {
                System.out.printf("%s: Has been canceled.\n", name);
            } else {
                System.out.printf("%s: Has been finished.\n", name);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService threadPool = Executors.newCachedThreadPool();
        ResultTask[] tasks = new ResultTask[5];
        for (int i = 0; i < 5; i++) {
            Task task = new Task("Task"+String.valueOf(i));
            tasks[i] = new ResultTask(task);
            threadPool.submit(tasks[i]);
        }

        Thread.sleep(3000);
        for (int i = 0; i <5 ; i++) {
            tasks[i].cancel(true);
        }
        for (int i = 0; i <5 ; i++) {
            if (!tasks[i].isCancelled()){
                System.out.println(tasks[i].get());
            }
        }
        threadPool.shutdown();

    }

}
