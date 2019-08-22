package dong.behavior.chain;

/**
 * @author DONGSHILEI
 * @create 2019-08-22 17:53
 * @since 1.0
 */
public class ErrorLogger extends AbstractLogger {

    public ErrorLogger(int level) {
        this.level = level;
    }

    @Override
    protected void write(String message) {
        System.out.println("Standard Error::Logger:" + message);
    }
}
