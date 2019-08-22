package dong.behavior.template;

/**
 * 模板模式
 * @author DONGSHILEI
 * @create 2019-08-21 20:16
 * @since 1.0
 */
public abstract class Game {
    abstract void init();
    abstract void startPlay();
    abstract void endPlay();

    /**
     * 重点  封装方法要使用final,为防止恶意操作
     */
    public final void play(){
        //初始化
        init();
        //游戏开始
        startPlay();
        //游戏结束
        endPlay();
    }
}
