package dong;

import java.lang.reflect.Modifier;

/**
 * Created by DONGSHILEI on 2017/6/23.
 */
public class ReflectionDemo {
    public static void main(String[] args) {
        Class<User> userClass = User.class;
        //判断是否为基础类型
        System.out.println(userClass.isPrimitive());
        System.out.println(int.class.isPrimitive());
        //获取类的全限定名称，带包路径
        System.out.println(userClass.getName());
        //获取类的简称
        System.out.println(userClass.getSimpleName());
        //获取访问权限
        System.out.println(userClass.getModifiers()+"  "+Modifier.toString(userClass.getModifiers()));
    }
}
