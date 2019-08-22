package dong.behavior.chain;

/**
 * @author DONGSHILEI
 * @create 2019-08-22 17:54
 * @since 1.0
 */
public class ChainMain {

    public static AbstractLogger init(){
        DebugLogger debugLogger = new DebugLogger(AbstractLogger.DEBUG);
        InfoLogger infoLogger = new InfoLogger(AbstractLogger.INFO);
        ErrorLogger errorLogger = new ErrorLogger(AbstractLogger.ERROR);
        errorLogger.setNextLogger(infoLogger);
        infoLogger.setNextLogger(debugLogger);
        return errorLogger;
    }

    public static void main(String[] args) {
        AbstractLogger chain = init();
        chain.logMessage(AbstractLogger.ERROR,"error message");
        System.out.println("===========================================");
        chain.logMessage(AbstractLogger.INFO,"info message");
        System.out.println("===========================================");
        chain.logMessage(AbstractLogger.DEBUG,"debug message");
        System.out.println("===========================================");
        chain.logMessage(0,"unknown message");

    }
}
