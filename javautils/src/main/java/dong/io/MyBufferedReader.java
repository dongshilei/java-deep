package dong.io;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

/**
 * 自己实现字符流缓冲区对象
 * Created by DONGSHILEI on 2017/11/15
 */
public class MyBufferedReader {
    private Reader reader;

    public MyBufferedReader(Reader reader) {
        this.reader = reader;
    }

    public String myReadLine() throws IOException {
        // 创建临时容器
        StringBuilder sb = new StringBuilder();
        // 循环使用read方法不断读取字符
        int ch = 0;
        while ((ch = reader.read())!=-1){
            // '\r' 行开始标记
            if (ch=='\r'){
                continue;
            }
            // '\n' 行结束标记
            if (ch == '\n'){
                return sb.toString();
            } else {
                sb.append((char) ch);
            }
        }
        if (sb.length()!=0){
            return sb.toString();
        }
        return null;
    }

    public void myClose() throws IOException {
        reader.close();
    }

    public static void main(String[] args) throws IOException, CloneNotSupportedException {
        MyBufferedReader reader = new MyBufferedReader(new FileReader("E:/aaa.txt"));
        String line;
        while ((line = reader.myReadLine())!=null){
            System.out.println(line);
        }
        reader.clone();
    }

}
