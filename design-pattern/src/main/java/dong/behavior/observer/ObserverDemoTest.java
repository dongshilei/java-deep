package dong.behavior.observer;

/**
 * @program: java-deep
 * @description
 * @author: DONGSHILEI
 * @create: 2020/10/15 18:35
 **/
public class ObserverDemoTest {

    public static void main(String[] args) {
        CriminalObservable criminal = new CriminalObservable();
        PoliceObserver police1 = new PoliceObserver("老王");
        PoliceObserver police2 = new PoliceObserver("老李");
        PoliceObserver police3 = new PoliceObserver("老张");
        criminal.addObserver(police1);
        criminal.addObserver(police2);
        criminal.addObserver(police3);

        criminal.crime("盗窃珠宝");
    }
}
