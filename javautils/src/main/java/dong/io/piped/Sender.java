package dong.io.piped;

import java.io.IOException;
import java.io.PipedOutputStream;

/**
 * 发送者线程
 * Created by DONGSHILEI on 2017/11/21
 */
public class Sender extends Thread {
    /**
     * 管道输出流对象
     * 与输入流对象绑定，将数据发送给输入流
     */
    private PipedOutputStream out = new PipedOutputStream();

    public PipedOutputStream getOutputStream(){
        return out;
    }

    @Override
    public void run() {
        //writeShortMessage();
        writeLongMessage();
    }

    /**
     *  向管道输出流中写入一则较简短的消息
     */
    public void writeShortMessage() {
        String strInfo = "this is a short message";
        try {
            out.write(strInfo.getBytes());
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeLongMessage(){
        StringBuffer sb = new StringBuffer();
        for (int i=0;i<102;i++){
            sb.append("0123456789");
        }
        sb.append("asdfghjklqwertyuiop");
        String str = sb.toString();
        try {
            out.write(str.getBytes());
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
