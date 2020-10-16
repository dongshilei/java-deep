package dong;

import cn.hutool.core.util.ReUtil;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: java-deep
 * @description
 * @author: DONGSHILEI
 * @create: 2020/9/25 14:13
 **/
public class ReUtilTest {

    @Test
    public void test1(){
        String tel = "15321517655";
        String regex = "^1[3|4|5|7|8|9][0-9]{9}$";
        boolean match = ReUtil.isMatch(regex, tel);
        System.out.println(match);
    }
    @Test
    public void test2(){
        String tel = "(010)66483939";
        String regex = "^((0\\d{2,3}\\-)|(\\(0\\d{2,3}\\)))?([1-9]\\d{6,7})+(\\-\\d{1,6})?$";
        boolean match = ReUtil.isMatch(regex, tel);
        System.out.println(match);
    }

    @Test
    public void test3(){
        String dd = "400";
        BigDecimal bd = new BigDecimal(dd);
        BigDecimal bigDecimal = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
        System.out.println(bigDecimal);
    }

    /**
     * String.join 用法
     */
    @Test
    public void test4(){
        List<String> list = new ArrayList<>();
        list.add("123");
        list.add("123");
        list.add("123");
        String ss = String.join(",", list);
        System.out.println(ss);
    }

    @Test
    public void pow(){
        Double pow = Math.pow(10, 10);
        System.out.println(pow.longValue());
    }
}
