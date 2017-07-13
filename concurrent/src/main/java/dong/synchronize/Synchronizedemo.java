package dong.synchronize;

/**
 * Created by DONGSHILEI on 2017/7/12.
 */
public class Synchronizedemo {
    public void sync() {
        synchronized (this) {
            for (int i=0;i<5;i++){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+ " sync "+i);
            }
        }
    }
    public void sync2() {
        synchronized (this) {
            for (int i=0;i<5;i++){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+ " sync2 "+i);
            }
        }
    }
    public void noSync(){
        for (int i=0;i<5;i++){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+ " noSync "+i);
        }
    }
    public static void main(String[] args) {
        Synchronizedemo sync = new Synchronizedemo();
        Thread s1 = new Thread(new Runnable() {
            @Override
            public void run() {
                sync.sync();
            }
        }, "s1");
        Thread s2 = new Thread(new Runnable() {
            @Override
            public void run() {
                sync.sync2();
            }
        }, "s2");
        s1.start();
        s2.start();
    }
}
