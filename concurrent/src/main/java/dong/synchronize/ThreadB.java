package dong.synchronize;

/**
 * Created by DONGSHILEI on 2017/10/17
 */
public class ThreadB implements Runnable{
    private Info info;

    public ThreadB(Info info) {
        this.info = info;
    }

    @Override
    public void run() {
        while (true){
            synchronized (info){
                if (info.flag){
                    try {
                        info.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println(Thread.currentThread().getName()+"--"+info.msg);
                    info.msg = "A";
                    info.flag = true;
                    info.notify();
                }
            }
        }
    }
}
