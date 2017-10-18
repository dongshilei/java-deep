package dong.core_java;

/**
 * Created by DONGSHILEI on 2017/9/26
 */
public class MusicTest {


    public static void main(String[] args) {
        Piano piano = new Piano("钢琴");
        Liangzhu lz = new Liangzhu("梁祝");
        piano.play(lz);
    }
}
