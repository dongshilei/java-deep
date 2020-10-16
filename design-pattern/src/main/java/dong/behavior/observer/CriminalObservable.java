package dong.behavior.observer;

import java.util.Date;
import java.util.Observable;

/**
 * @program: java-deep
 * @description  罪犯 被观察者
 * @author: DONGSHILEI
 * @create: 2020/10/15 18:32
 **/
public class CriminalObservable extends Observable {

    public void crime(String event){
        System.out.println("我是罪犯，我要"+event);
        // 尝试伪装
        camouflage();
        // 可能暴露
        notifyObservers(event);
    }

    /**
     * 伪装一下
     */
    public void camouflage(){
        long flag = System.currentTimeMillis()&1;
        if (flag == 0) {
            setChanged();
            System.out.println("伪装失败");
        } else {
            clearChanged();
            System.out.println("伪装成功");
        }
    }
}
