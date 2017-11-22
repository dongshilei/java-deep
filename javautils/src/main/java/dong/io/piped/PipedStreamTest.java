package dong.io.piped;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * Created by DONGSHILEI on 2017/11/21
 */
public class PipedStreamTest {
    public static void main(String[] args) {
        Sender s1 = new Sender();
        Receiver r1 = new Receiver();
        PipedOutputStream out = s1.getOutputStream();
        PipedInputStream in = r1.getInputStream();

        try {
            //等价于 out.connect(in)
            in.connect(out);
            s1.start();
            r1.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
