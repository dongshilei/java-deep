package dong.threadPool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author DONGSHILEI
 * @create 2019-09-17 14:25
 * @since 1.0
 */
public class ScheduledExecutorServiceTest {

    public static void main(String[] args) {
        ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(1);
        User user = new User();
        user.setAge(100);
        user.setName("hhh ");
        threadPool.schedule(new Runnable() {
            @Override
            public void run() {
                print(user);
            }
        },5, TimeUnit.SECONDS);
    }


    public static void print(User user){
        System.out.println(user.getAge());
        System.out.println(user.getName());
    }
}
