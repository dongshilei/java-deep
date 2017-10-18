package dong.behavior.strategy.strategy;

/**
 * 加操作
 * Created by DONGSHILEI on 2017/9/29
 */
public class StrategyAdd implements Strategy {
    @Override
    public int doOperation(int a, int b) {
        return a+b;
    }
}
