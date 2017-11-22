package dong.io;

import java.io.*;
import java.util.Enumeration;
import java.util.Properties;

/**
 * Created by DONGSHILEI on 2017/11/20
 */
public class PropertiesDemo {

    public static void main(String[] args) throws IOException {
        Properties properties = new Properties();
        properties.setProperty("a","aaa");
        properties.setProperty("b","bbb");
        properties.list(System.out);
        //properties.list(new PrintStream("D:/dong.txt"));
        Properties properties1 = new Properties();
        properties1.load(new FileReader("D:/dong.txt"));
        Enumeration<?> enumeration = properties1.propertyNames();
        while (enumeration.hasMoreElements()){
            System.out.println(enumeration.nextElement().toString());
        }
    }
}
