package dong.threadGroup;

/**
 * Created by DONGSHILEI on 2017/6/4.
 */
public class ThreadGroupName implements Runnable {
    public void run() {
        String groupAndName = Thread.currentThread().getThreadGroup().getName()+"--"
                +Thread.currentThread().getName();
        while (true){
            System.out.println("I am "+groupAndName);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {
        ThreadGroup threadGroup = new ThreadGroup("PrintGroup");
        Thread t1 = new Thread(threadGroup,new ThreadGroupName(),"t1");
        Thread t2 = new Thread(threadGroup,new ThreadGroupName(),"t2");
        t1.start();
        t2.start();
        System.out.println(threadGroup.activeCount());
        threadGroup.list();
    }
}
