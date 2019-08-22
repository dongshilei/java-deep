package dong.behavior.template;

/**
 * @author DONGSHILEI
 * @create 2019-08-21 20:21
 * @since 1.0
 */
public class Football extends Game {
    @Override
    void init() {
        System.out.println("Football init..");
    }

    @Override
    void startPlay() {
        System.out.println("Football startPlay..");
    }

    @Override
    void endPlay() {
        System.out.println("Football endPlay..");
    }
}
