package dong.doc;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.ooxml.POIXMLDocument;
import org.apache.poi.ooxml.extractor.POIXMLTextExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;

/**
 * @program: java-deep
 * @description
 * @author: DONGSHILEI
 * @create: 2020/2/11 17:03
 **/
public class DocUtil {
    /**
     * 读取doc文件内容
     *
     * @param fs 想要读取的文件对象
     * @return 返回文件内容
     * @throws IOException
     */
    public static String doc2String(FileInputStream fs) throws IOException {
        StringBuilder result = new StringBuilder();
        WordExtractor re = new WordExtractor(fs);
        result.append(re.getText());
        re.close();
        return result.toString();
    }

    public static String doc2String(File file) throws IOException {
        return doc2String(new FileInputStream(file));
    }


    /**
     * 读取word文件内容
     *
     * @param path
     * @return buffer
     */

    public static String readWord(String path) {
        String buffer = "";
        try {
            if (path.endsWith(".doc")) {
                InputStream is = new FileInputStream(new File(path));
                WordExtractor ex = new WordExtractor(is);
                buffer = ex.getText();
                ex.close();
            } else if (path.endsWith("docx")) {
                OPCPackage opcPackage = POIXMLDocument.openPackage(path);
                POIXMLTextExtractor extractor = new XWPFWordExtractor(opcPackage);
                buffer = extractor.getText();
                extractor.close();
            } else {
                System.out.println("此文件不是word文件！");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer;
    }

    public static void main(String[] args) {
        String s = readWord("C:\\Users\\DONGSHILEI\\Desktop\\2019年执业药师药一真题-试题版.docx");
        //System.out.println(s);
        String[] split = s.split("\n");
        System.out.println(split.length);
        for (int i = 1;i<split.length;i++){
            String ss = split[i];
            //if ()
        }
       /* File file = new File("C:\\Users\\DONGSHILEI\\Desktop\\2019年执业药师药一真题-试题版.docx");
        try {
            System.out.println(doc2String(file));
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
}
