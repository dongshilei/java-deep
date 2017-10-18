package dong.core_java;

/**
 * 歌曲
 * Created by DONGSHILEI on 2017/9/26
 */
public class Melody {
    private String name;

    public Melody(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "Melody{" +
                "name='" + name + '\'' +
                '}';
    }
}
