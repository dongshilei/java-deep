package dong.hutool.date;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import org.junit.Test;

import java.util.Date;

/**
 * @program: java-deep
 * @description
 * @author: DONGSHILEI
 * @create: 2020/10/14 11:31
 **/
public class DateUtilTest {

    @Test
    public void test1(){
        DateTime dateTime1 = DateUtil.offsetDay(new Date(), 15);
        DateTime dateTime = DateUtil.endOfDay(dateTime1).offset(DateField.MILLISECOND,-999);
        System.out.println(dateTime);
    }
    @Test
    public void test2(){
        DateTime dateTime = cn.hutool.core.date.DateUtil.endOfDay(new Date()).offset(DateField.MILLISECOND,-999);
        System.out.println(dateTime);
    }
}
