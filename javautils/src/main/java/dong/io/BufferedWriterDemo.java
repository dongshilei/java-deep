package dong.io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author DONGSHILEI
 * @create 2018-01-29 17:29
 */
public class BufferedWriterDemo {

    public static void main(String[] args) {
        try {
            FileReader reader = new FileReader("D:/9fbank/银行存管账户相关授权委托书_定稿.pdf");
            BufferedReader br = new BufferedReader(reader);
            String line = null;
            while((line = br.readLine()) != null){
                System.out.println(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
