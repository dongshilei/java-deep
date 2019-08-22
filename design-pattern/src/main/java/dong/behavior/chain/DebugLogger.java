package dong.behavior.chain;

/**
 * @author DONGSHILEI
 * @create 2019-08-22 17:49
 * @since 1.0
 */
public class DebugLogger extends AbstractLogger {
    public DebugLogger(int level) {
        this.level = level;
    }

    @Override
    protected void write(String message) {
        System.out.println("Standard Debug::Logger:"+message);
    }
}
