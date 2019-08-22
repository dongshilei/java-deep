package dong.behavior.template;

/**
 * @author DONGSHILEI
 * @create 2019-08-21 20:19
 * @since 1.0
 */
public class Cricket extends Game {
    @Override
    void init() {
        System.out.println("Cricket init..");
    }

    @Override
    void startPlay() {
        System.out.println("Cricket startPlay..");
    }

    @Override
    void endPlay() {
        System.out.println("Cricket endPlay..");
    }
}
