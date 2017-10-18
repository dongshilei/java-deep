package dong.io;

import java.io.*;
import java.nio.charset.Charset;

/**
 * Created by DONGSHILEI on 2017/10/18
 */
public class IODemo {

    /**
     * 操作字节
     * 将内容转换成字节数组，输出到指定文件中
     * @throws IOException
     */
    public static void testOutputStream() throws IOException {
        String msg = "add";
        byte[] bytes = msg.getBytes();
        // append = true 可以追加写入；不填append属性，则默认不可追加，新添加的内容会覆盖掉之前的内容
        FileOutputStream out = new FileOutputStream("D:\\aaaaa.txt",true);
        out.write(bytes);
        out.flush();
        out.close();
    }

    /**
     * 操作字节
     * 读取指定文件的内容
     * @throws IOException
     */
    public static void testInputStream() throws IOException {
        FileInputStream in = new FileInputStream("D:\\aaaaa.txt");
        StringBuffer buffer = new StringBuffer();
        byte[] bt = new byte[1024];
        while (in.read(bt)>0){
            buffer.append(new String(bt));
        }
        System.out.println(buffer.toString());
    }

    /**
     * 字符操作
     * 向指定文件写入内容
     * @throws IOException
     */
    public static void testWriter() throws IOException {
        FileWriter writer = new FileWriter("D:\\cccc.txt");
        for (int i=0;i<5;i++){
            writer.write("dong"+i+"\r\n");
        }
        writer.flush();
        writer.close();
    }

    /**
     * 操作字符
     * 读取文件内容
     * @throws IOException
     */
    public static void testReader() throws IOException{
        FileReader reader = new FileReader("D:\\cccc.txt");
        StringBuffer sb = new StringBuffer();
        char[] buffer = new char[1024];
        while (reader.read(buffer)>0) {
            sb.append(buffer);
        }
        System.out.println(sb.toString());
    }

    /**
     * 字节流到字符的转换
     * @throws IOException
     */
    public static void testInputStreamRead() throws IOException {
        InputStreamReader isr = new InputStreamReader(new FileInputStream("D:\\cccc.txt"), Charset.forName("GBK"));
        StringBuffer sbuffer = new StringBuffer();
        char[] buffer = new char[1024];
        while (isr.read(buffer)>0){
            sbuffer.append(buffer);
        }
        System.out.println(sbuffer.toString());
    }

    public static void main(String[] args) throws IOException {
        //testOutputStream();
        //testInputStream();
//        testWriter();
//        testReader();
        testInputStreamRead();
    }
}
