package dong.io;

import java.io.*;

/**
 * Created by DONGSHILEI on 2017/11/8
 */
public class FileDemo{

    public static void testFileWriter(){
        FileWriter writer = null;
        try {
            writer = new FileWriter("D:\\aaa.txt");
            writer.write("aaa");
            writer.write("bbb");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer!=null){
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void testFileReader(){
        FileReader reader = null;
        try {
            reader = new FileReader("D:\\aaa.txt");
            //第一种 读到一个字符返回输出一个
            int ch = 0;
            while ((ch = reader.read())!=-1){
                System.out.println((char) ch);
            }
            //第二种 读完1kb后输出 注意 运行时需注释掉上面第一种，因为reader已经读到流的末尾
            char[] buf = new char[1024];//同常都是1024整数倍
            int len = 0;
            while ((len=reader.read(buf))!=-1){
                System.out.println(new String(buf,0,len));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader!=null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        //testFileWriter();
        testFileReader();
    }
}
