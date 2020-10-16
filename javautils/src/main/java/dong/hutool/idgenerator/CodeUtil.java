package dong.hutool.idgenerator;


import net.sourceforge.pinyin4j.PinyinHelper;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class CodeUtil {
    public static String getCode(String name) {

        StringBuilder demo = new StringBuilder("");
        String merchant = getUpperCase(name);

        demo = demo.append(merchant);
        DateTimeFormatter ofPatternShort = DateTimeFormatter.ofPattern("HHmmssSSS");
        String format = ofPatternShort.format(LocalTime.now());
        demo = demo.append(format);
        Random random = new Random();
        demo = demo.append(StringUtils.leftPad(Integer.toString(random.nextInt(9999)), 4, "0"));
        return demo.toString();
    }

    private static String getUpperCase(String str) {
        String upper = "";
        String temp = "";
        String convert = "";
        for (int j = 0; j < str.length(); j++) {
            char word = str.charAt(j);
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
            if (pinyinArray != null) {
                convert += pinyinArray[0].charAt(0);
            } else {
                convert += word;
            }
        }
        for (int i = 0; i < convert.length(); i++) {//convert目前为小写首字母,下面是将小写首字母转化为大写
            if (convert.charAt(i) >= 'a' && convert.charAt(i) <= 'z') {
                temp = convert.substring(i, i + 1).toUpperCase();
                upper = upper + temp;
            }
        }
        return upper;
    }

    public static void main(String[] args) {
        String code = getCode("合利屋来广营店sdfsdfs");
        System.out.println(code);
    }
}
