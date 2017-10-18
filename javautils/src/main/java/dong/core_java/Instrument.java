package dong.core_java;

/**
 * 乐器父类
 * Created by DONGSHILEI on 2017/9/26
 */
public class Instrument {

    private String name;

    public Instrument(String name) {
        this.name = name;
    }

    public void play(Melody melody){
        System.out.println("使用"+this.name+"演奏"+melody.toString());
    }
}
