package dong.io;

import java.io.*;

/**
 * 利用字节流 复制图片
 * Created by DONGSHILEI on 2017/11/15
 */
public class BufferedStreamDemo {
    public static void main(String[] args) throws IOException {
        BufferedInputStream bufis = new BufferedInputStream(new FileInputStream("E:/a.jpg"));
        BufferedOutputStream bufos = new BufferedOutputStream(new FileOutputStream("D:/b.jpg"));
        int ch = 0;
        while ((ch = bufis.read())!=-1){
            bufos.write(ch);
        }
        bufos.close();
        bufis.close();
    }
}
