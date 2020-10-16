package dong.other;

import cn.hutool.core.io.FileUtil;
import org.junit.Test;
import sun.misc.BASE64Encoder;

import java.io.File;
import java.text.DecimalFormat;

/**
 * @program: java-deep
 * @description
 * @author: DONGSHILEI
 * @create: 2020/6/8 12:03
 **/
public class OtherTest {
    /**
     * 设置保留位数
     */
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0");

    @Test
    public void test1(){
        Integer centerNum = 597;
        Integer totalNum = 600;
        final Integer precent = Integer.valueOf(DECIMAL_FORMAT.format((float) centerNum / totalNum * 100));
        System.out.println(precent);
    }

    @Test
    public void test2(){
        BASE64Encoder encoder = new BASE64Encoder();
        byte[] bytes = FileUtil.readBytes(new File("C:\\Users\\DONGSHILEI\\Desktop\\jquerypdf\\pdf1.pdf"));
        String encode = encoder.encode(bytes);
        encode = encode.replace("\r\n", "");
        encode = encode.replace("\\+","%2B");
        encode = "data:application/pdf;base64,".concat(encode);
        System.out.println(encode);
    }
}
