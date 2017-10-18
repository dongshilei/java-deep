package dong.behavior.strategy.context;

import dong.behavior.strategy.strategy.Strategy;

/**
 * 上下文
 * Created by DONGSHILEI on 2017/9/29
 */
public class Context {

    private Strategy strategy;

    public Context(Strategy strategy) {
        this.strategy = strategy;
    }

    public int executeStragegy(int a,int b){
        return strategy.doOperation(a,b);
    }
}
