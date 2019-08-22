package dong.behavior.chain;

/**
 * @author DONGSHILEI
 * @create 2019-08-22 17:41
 * @since 1.0
 */
public abstract class AbstractLogger {
    public static int DEBUG = 1;
    public static int INFO = 2;
    public static int ERROR = 3;

    protected int level;

    //责任链中下一个元素
    protected AbstractLogger nextLogger;

    public void setNextLogger(AbstractLogger nextLogger) {
        this.nextLogger = nextLogger;
    }

    public void logMessage(int level, String message) {
        if (this.level <= level) {
            write(message);
            return;
        }
        if (nextLogger != null) {
            nextLogger.logMessage(level,message);
        } else {
            System.out.println("未处理："+message);
        }
    }

    protected abstract void write(String message);
}
