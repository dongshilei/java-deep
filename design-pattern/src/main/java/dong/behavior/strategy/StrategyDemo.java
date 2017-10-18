package dong.behavior.strategy;

import dong.behavior.strategy.context.Context;
import dong.behavior.strategy.strategy.StrategyAdd;
import dong.behavior.strategy.strategy.StrategyMultiply;
import dong.behavior.strategy.strategy.StrategySubStract;

/**
 * Created by DONGSHILEI on 2017/9/29
 */
public class StrategyDemo {
    public static void main(String[] args) {
        //加
        Context context = new Context(new StrategyAdd());
        int add = context.executeStragegy(8, 6);
        System.out.println("8+6="+add);
        //减
        context = new Context(new StrategySubStract());
        int sub = context.executeStragegy(8, 6);
        System.out.println("8-6="+sub);

        context = new Context(new StrategyMultiply());
        int mul = context.executeStragegy(8, 6);
        System.out.println("8*6="+mul);


    }
}
