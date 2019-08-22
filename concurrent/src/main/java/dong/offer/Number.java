package dong.offer;

/**
 * @author DONGSHILEI
 * @create 2019-03-15 13:15
 */
public class Number {

    /**
     * 判断某个整数是否为奇数
     * @param i
     * @return
     */
    public static boolean isOdd(int i){
        return (i&1) == 1;
    }


    public static void main(String[] args) {
        boolean odd = isOdd(1);
        System.out.println(odd);
    }
}
