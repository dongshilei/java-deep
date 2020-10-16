package dong.behavior.observer;

import java.util.Observable;
import java.util.Observer;

/**
 * @program: java-deep
 * @description 警察  观察者
 * @author: DONGSHILEI
 * @create: 2020/10/15 18:30
 **/
public class PoliceObserver implements Observer {

    private String name;

    public PoliceObserver(String name) {
        this.name = name;
    }

    /**
     * This method is called whenever the observed object is changed. An
     * application calls an <tt>Observable</tt> object's
     * <code>notifyObservers</code> method to have all the object's
     * observers notified of the change.
     *
     * @param o   the observable object.
     * @param arg an argument passed to the <code>notifyObservers</code>
     */
    @Override
    public void update(Observable o, Object arg) {
        System.out.println(name+"发现罪犯正在"+ arg);
    }
}
