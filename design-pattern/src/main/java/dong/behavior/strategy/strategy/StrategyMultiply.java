package dong.behavior.strategy.strategy;

/**
 * 乘操作
 * Created by DONGSHILEI on 2017/9/29
 */
public class StrategyMultiply implements Strategy {
    @Override
    public int doOperation(int a, int b) {
        return a*b;
    }
}
