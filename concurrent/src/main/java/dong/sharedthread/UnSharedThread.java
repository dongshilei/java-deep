package dong.sharedthread;

/**
 * @program: java-deep
 * @description  线程不共享数据示例
 * @author: DONGSHILEI
 * @create: 2020/4/30 10:35
 **/
public class UnSharedThread extends Thread {
    private int count = 5;

    public UnSharedThread(String name) {
        this.setName(name);
    }

    @Override
    public void run() {
        while (count > 0) {
            count--;
            System.out.println(currentThread().getName() + " , count=" + count);
        }
    }

    public static void main(String[] args) {
        UnSharedThread a = new UnSharedThread("a");
        UnSharedThread b = new UnSharedThread("b");
        UnSharedThread c = new UnSharedThread("c");
        a.start();
        b.start();
        c.start();
    }
}
