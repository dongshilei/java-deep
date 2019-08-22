package dong.arithmetic;

import java.math.BigDecimal;

/**
 * 奇偶判断
 * @author DONGSHILEI
 * @create 2019-06-03 10:27
 */
public class OddEvenCheck {

    public static void main(String[] args) {
        check(new BigDecimal(500001.00).intValue());
        check(11);
    }
    public static void check(int parm){
        if ((parm&1)==0){
            System.out.println(parm+" 是偶数");
        } else {
            System.out.println(parm+" 是奇数");
        }

    }
}
