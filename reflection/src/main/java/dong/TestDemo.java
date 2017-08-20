package dong;

import sun.misc.Launcher;

import java.net.URL;

/**
 * Created by DONGSHILEI on 2017/7/21.
 */
public class TestDemo {

    public static void main(String[] args) {
        URL[] urLs = Launcher.getBootstrapClassPath().getURLs();
        for (int i = 0; i < urLs.length ; i++) {
            System.out.println(urLs[i].toExternalForm());
        }

        System.out.println(System.getProperty("java.ext.dirs"));
    }
}
