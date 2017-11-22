package dong.io.piped;

import java.io.IOException;
import java.io.PipedInputStream;

/**
 * 接收者线程
 * Created by DONGSHILEI on 2017/11/21
 */
public class Receiver extends Thread {

    /**
     * 管道输入流对象
     * 与管道输出流对象绑定，接收数据
     */
    private PipedInputStream in = new PipedInputStream();

    public PipedInputStream getInputStream() {
        return in;
    }

    @Override
    public void run() {
        //readMessageOnce();
        readMessageContinued();
    }

    /**
     * 从管道输入流中读取数据，仅一次
     * 然后处理
     */
    private void readMessageOnce() {
        //虽然buf的大小是2048个字节，但最多只会从“管道输入流”中读取1024个字节。
        //管道输入流的缓冲区大小默认只有1024个字节。
        byte[] buf = new byte[2048];
        try {
            //从缓冲区读取数据
            int len = in.read(buf);
            System.out.println("收到消息：" + new String(buf, 0, len));
            //关闭管道输入流
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从管道输入流中读取数据，循环多次，直到数据结束
     */
    public void readMessageContinued() {
        byte[] buf = new byte[512];
        int len;
        try {
            while ((len = in.read(buf)) != -1) {
                System.out.println("收到消息：" + new String(buf, 0, len));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
