package dong.java8;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

/**
 * Created by DONGSHILEI on 2017/9/19
 */
public class StreamTest {
    //整型遍历
    public static void intStreamTest(){
        //简单遍历
        IntStream.range(1,10).forEach(i-> System.out.println(i));
        //在内部类使用lambda参数
        IntStream.range(1,10).forEach(i-> new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+" "+i);
            }
        }).start());
        //等差求和
        int sum = IntStream.iterate(1, e -> e + 3).limit(10).sum();
        System.out.println("等差求和，从1开始，等差为3的10个数的和："+sum);

    }
    public static void longStreamTest(){
        LongStream.range(1,10).forEach(i-> System.out.println(i));
    }

    //lambda遍历list
    public static void arry(){
        List<String> list = Arrays.asList("aaa", "bbb", "ccc", "ddd");
        list.forEach(str->{
            System.out.println(str);
        });
    }
    //lambda遍历map
    public static void map(){
        Map<String,Integer> mp = new HashMap<>();
        mp.put("aaa",1);
        mp.put("bbb",2);
        mp.put("ccc",3);
        mp.forEach((k,v)->{
            System.out.println(mp.get(k));
        });
    }

    public static void main(String[] args) {
        //intStreamTest();
        //longStreamTest();
        arry();
        map();
    }
}
