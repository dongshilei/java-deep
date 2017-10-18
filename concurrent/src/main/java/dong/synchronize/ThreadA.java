package dong.synchronize;

/**
 * Created by DONGSHILEI on 2017/10/17
 */
public class ThreadA implements Runnable{
    private Info info;

    public ThreadA(Info info) {
        this.info = info;
    }

    @Override
    public void run() {
        while (true){
            synchronized (info){
                if (!info.flag){
                    try {
                        //线程等待
                        info.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println(Thread.currentThread().getName()+"--"+info.msg);
                    info.flag = false;
                    info.msg = "B";
                    //唤醒等待info的线程，选择一个
                    info.notify();
                }
            }
        }
    }
}
