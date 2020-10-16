package dong.java8;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * @program: java-deep
 * @description lambda 表达式
 * @author: DONGSHILEI
 * @create: 2020/6/29 20:22
 **/
public class LambdaTest {

    @Test
    public void test1() {
        // 类型声明
        MathOperation addition = (int a, int b) -> a + b;
        // 不声明类型
        MathOperation subtraction = (a, b) -> a - b;
        // 大括号中的返回语句
        MathOperation multiplication = (a, b) -> {
            return a * b;
        };
        System.out.println("10 + 5 = " + addition.operation(10, 5));
        System.out.println("10 - 5 = " + subtraction.operation(10, 5));
        System.out.println("10 x 5 = " + multiplication.operation(10, 5));
        //接受一个 string 对象,并在控制台打印
        GreetingService gs1 = message -> {
            System.out.println("hello "+message);
        };
        GreetingService gs2 = message -> System.out.println("hello "+message);
        gs1.sayMessage("world");
        gs2.sayMessage("java");
    }

    interface MathOperation {
        int operation(int a, int b);
    }

    interface GreetingService {
        void sayMessage(String message);
    }

    /**
     * MapReduce
     * 使用 map() 给每个元素求平方，再使用 reduce() 将所有元素计入一个数值：
     */
    @Test
    public void test2(){
        List<Integer> list = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
        int sum = list.stream().map(x->x*x).reduce((x,y)->x+y).get();
        System.out.println(sum);
    }
}
