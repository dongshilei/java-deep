package dong.threadPool;

import java.util.concurrent.*;

/**
 * @author DONGSHILEI
 * @create 2018-08-28 16:51
 */
public class CallbackPool {

    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        CallDemo hello = new CallDemo("hello");
        Future<String> submit = threadPool.submit(hello);
        System.out.println("我打印了");
        try {
            System.out.println(submit.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.exit(1);
    }

    static class CallDemo implements Callable<String>{

        private String name;

        public CallDemo(String name) {
            this.name = name;
        }

        @Override
        public String call() throws Exception {
            Thread.sleep(5000);
            return name+"  world";
        }
    }
}
