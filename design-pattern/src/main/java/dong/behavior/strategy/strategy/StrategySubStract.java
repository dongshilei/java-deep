package dong.behavior.strategy.strategy;

/**
 * 减操作
 * Created by DONGSHILEI on 2017/9/29
 */
public class StrategySubStract implements Strategy {
    @Override
    public int doOperation(int a, int b) {
        return a-b;
    }
}
