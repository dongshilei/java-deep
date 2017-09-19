package dong.singleton;

/**
 * Created by DONGSHILEI on 2017/9/13
 */
public class Singleton {
    private static Singleton singleton;
    static {
        singleton = new Singleton();
    }

    public Singleton() {
        System.out.println("实例化");
        //模拟耗时操作
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static Singleton getInstance(){
        return singleton;
    }

    public static void main(String[] args) {
        Singleton singleton = Singleton.getInstance();
        Singleton singleton1 = Singleton.getInstance();
        System.out.println(singleton==singleton1);

    }
}
