package dong.sharedthread;

/**
 * @program: java-deep
 * @description
 * @author: DONGSHILEI
 * @create: 2020/4/30 10:41
 **/
public class SharedThread extends Thread {
    private int count = 5;


    @Override
    public void run() {
        count--;
        System.out.println(currentThread().getName() + " , count=" + count);
    }

    public static void main(String[] args) {
        SharedThread sharedThread = new SharedThread();
        Thread a = new Thread(sharedThread, "aa");
        Thread b = new Thread(sharedThread, "bb");
        Thread c = new Thread(sharedThread, "cc");
        a.start();
        b.start();
        c.start();
    }
}
