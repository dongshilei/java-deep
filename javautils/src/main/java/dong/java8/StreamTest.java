package dong.java8;

import org.junit.Test;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

/**
 * Created by DONGSHILEI on 2017/9/19
 */
public class StreamTest {

    @Test
    //整型遍历
    public  void intStreamTest(){
        //简单遍历
        IntStream.range(1,10).forEach(i-> System.out.println(i));
        //在内部类使用lambda参数
        IntStream.range(1,10).forEach(i-> new Thread(() -> System.out.println(Thread.currentThread().getName()+" "+i)).start());
        //等差求和
        int sum = IntStream.iterate(1, e -> e + 3).limit(10).sum();
        System.out.println("等差求和，从1开始，等差为3的10个数的和："+sum);

    }
    public  void longStreamTest(){
        LongStream.range(1,10).forEach(i-> System.out.println(i));
    }

    //lambda遍历list
    public  void arry(){
        List<String> list = Arrays.asList("aaa", "bbb", "ccc", "ddd");
        list.forEach(str->{
            System.out.println(str);
        });
    }
    //lambda遍历map
    public  void map(){
        Map<String,Integer> mp = new HashMap<>();
        mp.put("aaa",1);
        mp.put("bbb",2);
        mp.put("ccc",3);
        mp.forEach((k,v)->{
            System.out.println(mp.get(k));
        });
    }

    /**
     * lambda遍历set
     */
    public  void set(){
       Set<String> set = new HashSet<>();
       set.add("aaa");
       set.add("bbb");
       set.add("ccc");
       set.forEach((v)->{
           System.out.println(v);
       });
    }

    @Test
    public  void test() {
        IntStream.range(0,10).forEach(i-> System.out.println(i));

        Long lon = 155L;
        System.out.println(lon==155);
    }
}