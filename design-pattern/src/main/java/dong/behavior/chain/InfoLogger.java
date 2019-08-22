package dong.behavior.chain;

/**
 * @author DONGSHILEI
 * @create 2019-08-22 17:51
 * @since 1.0
 */
public class InfoLogger extends AbstractLogger {
    public InfoLogger(int level) {
        this.level = level;
    }

    @Override
    protected void write(String message) {
        System.out.println("Standard Info::Logger:"+message);
    }
}
