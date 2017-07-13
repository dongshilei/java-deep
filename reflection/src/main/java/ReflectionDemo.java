import java.lang.reflect.Method;

/**
 * Created by DONGSHILEI on 2017/6/23.
 */
public class ReflectionDemo {
    public static void main(String[] args) {
        Method[] methods = User.class.getMethods();
        for (Method method : methods){
            System.out.println(method);
        }
    }
}
