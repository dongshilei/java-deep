package dong.ognl;

import com.alibaba.fastjson.JSONObject;
import ognl.Ognl;
import ognl.OgnlContext;
import ognl.OgnlException;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

/**
 * Ognl学习
 *
 * @author DONGSHILEI
 * @create 2019-12-10 17:49
 * @since 1.0
 */
public class OgnlTest {

    private Person person;

    private Dog dog;

    private Dog dog2;

    /**
     * 初始化数据
     */
    @Before
    public void init() {
        this.person = new Person();
        this.person.setName("张三");

        this.dog = new Dog();
        this.dog.setName("旺财");
        this.person.setDog(dog);

        this.dog2 = new Dog();
        this.dog2.setName("大黄");
    }

    /*
     * Ognl.getValue(object,context,context.getRoot())
     * 它会到指定的上下文context中去寻找object,默认是到根元素中寻找
     * 在OGNL中，如果表达式没有使用#号，那么OGNL会从根对象中寻找该属性对应的get()
     * 方法，如果寻找的不是根对象的中的属性，那么需要以#开头，告诉OGNL，去寻找指定对象中的属性
     */


    /**
     * OgnlContext放入基本变量数据
     */
    @Test
    public void test1() {
        OgnlContext ognlContext = new OgnlContext();
        //放入数据
        ognlContext.put("test", "测试");
        //获取数据
        Object test = ognlContext.get("test");
        System.out.println(test); // 测试
    }

    /**
     * OgnlContext放入对象数据
     */
    @Test
    public void test2() {
        OgnlContext ognlContext = new OgnlContext();
        // 非根元素 取值的时候表达式要用“#”
        ognlContext.put("person", person);
        try {
            //构建Ognl表达式
            Object ognl = Ognl.parseExpression("#person.name");
            // 解析表达式
            Object value = Ognl.getValue(ognl, ognlContext, ognlContext.getRoot());
            System.out.println(value); // 张三
            //构建Ognl表达式
            Object ognl2 = Ognl.parseExpression("#person.dog.name");
            // 解析表达式
            Object value2 = Ognl.getValue(ognl2, ognlContext, ognlContext.getRoot());
            System.out.println(value2); // 旺财
        } catch (OgnlException e) {
            e.printStackTrace();
        }

        // 根元素 取值不用#号
        ognlContext.setRoot(dog2);
        try {
            Object ognl = Ognl.parseExpression("name");
            // 解析表达式
            Object value = Ognl.getValue(ognl, ognlContext, ognlContext.getRoot());
            System.out.println(value);  // 大黄
        } catch (OgnlException e) {
            e.printStackTrace();
        }

        try {
            // 直接使用表达式字符串获取元素，非根节点带#
            Object value = Ognl.getValue("name", ognlContext, ognlContext.getRoot());
            System.out.println(value);  // 大黄
        } catch (OgnlException e) {
            e.printStackTrace();
        }
    }

    /**
     * ognl对静态方法调用的支持
     *  语法格式 @package.classname@method(parameters)
     *  默认类:java.lang.Math是OGNL的默认的类，即调用java.lang.Math的静态方法时，无需指定类的名称 @@method(parameters)
     */
    @Test
    public void test3(){
        OgnlContext context = new OgnlContext();
        try {
            //Ognl表达式语言，调用类的静态方法  @类@方法
            Object ognl = Ognl.parseExpression("@Math@floor(10.9)");
            Object value = Ognl.getValue(ognl, context, context.getRoot());
            System.out.println(value);
            //由于Math类在开发中比较常用，所有也可以这样写
            Object ognl2 = Ognl.parseExpression("@@floor(10.9)");
            Object value2 = Ognl.getValue(ognl2, context, context.getRoot());
            System.out.println(value2);

            Object integerTest = Ognl.getValue("@java.lang.Integer@toBinaryString(10)", context, context.getRoot());
            System.out.println(integerTest);
        } catch (OgnlException e) {
            e.printStackTrace();
        }
    }

    /**
     * 数组：通过下标访问
     */
    @Test
    public void test4(){
        OgnlContext context = new OgnlContext();
        String[] strs = new String[]{"aa","bb","cc"};
        try {
            context.put("strs",strs);
            Object value = Ognl.getValue("#strs[0]",context, context.getRoot());
            System.out.println(value); // aa
        } catch (OgnlException e) {
            e.printStackTrace();
        }

        Dog[] dogs = new Dog[]{dog,dog2};
        try {
            context.put("dogs",dogs);
            Object value = Ognl.getValue("#dogs[0].name", context, context.getRoot());
            System.out.println(value);
        } catch (OgnlException e) {
            e.printStackTrace();
        }
    }

    /**
     * Map 和 JSON
     *
     */
    @Test
    public void test5(){
        OgnlContext context = new OgnlContext();
        Map<String,Object> map = new HashMap<>();
        map.put("a","aa");
        map.put("b","bb");
        map.put("c","cc");
        context.put("map",map);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("a","aa");
        jsonObject.put("b","bb");
        jsonObject.put("c","cc");
        context.put("jsonObject",jsonObject);
        try {
            Object mapValue = Ognl.getValue("#map.a1", context,context.getRoot());
            Object jsonValue = Ognl.getValue("#jsonObject.a", context,context.getRoot());
            System.out.println(mapValue);//aa
            System.out.println(jsonValue);//aa
            //简化 将对象作为上下文root解析
            // 获取键为a的值
            Object value = Ognl.getValue("a", jsonObject);
            System.out.println(value);
            // 获取key为a的值的长度
            Object value1 = Ognl.getValue("a.length()", jsonObject);
            System.out.println(value1);
            Object value2 = Ognl.getValue("a.toString()", jsonObject);
            System.out.println(value2);
        } catch (OgnlException e) {
            e.printStackTrace();
        }
    }

    /**
     * 集合过滤
     */
    @Test
    public void test6(){
        OgnlContext context = new OgnlContext();
        Person zhangsan =  new Person("zhangsan");
        Person lisi = new Person("lisi");
        Person wangwu = new Person("wangwu");
        List<Person> list = new ArrayList<>();
        list.add(zhangsan);
        list.add(lisi);
        list.add(wangwu);
        context.put("list",list);
        try {
            //获取list中第一个元素的name
            Object exp = Ognl.parseExpression("#list.get(0).getName()");
            Object value = Ognl.getValue(exp, context, context.getRoot());
            System.out.println(value); //zhangsan

            //获取list中name长度大于4的元素的个数
            Object exp1 = Ognl.parseExpression("#list.{? #this.name.length()>4}.size()");
            Object value1 = Ognl.getValue(exp1, context, context.getRoot());
            System.out.println(value1); //2
            //获取list中name长度大于4的元素集合中第二个的name
            Object exp2 = Ognl.parseExpression("#list.{? #this.name.length()>4}.get(1).getName()");
            Object value2 = Ognl.getValue(exp2, context, context.getRoot());
            System.out.println(value2); //wangwu
            // 获取list中name长度大于4的元素集合中第一个  collection.{^ expression} 返回的是集合
            Object exp3 = Ognl.parseExpression("#list.{^ #this.name.length()>4}.get(0).getName()");
            Object value3 = Ognl.getValue(exp3, context, context.getRoot());
            System.out.println(value3); //zhangsan
            // 获取list中name长度大于4的元素集合中最后一个  collection.{$ expression} 返回的是集合
            Object exp4 = Ognl.parseExpression("#list.{$ #this.name.length()>4}.get(0).getName()");
            Object value4 = Ognl.getValue(exp4, context, context.getRoot());
            System.out.println(value4); //wangwu
        } catch (OgnlException e) {
            e.printStackTrace();
        }
    }

    /**
     * 集合投影 获取集合中的某列值的集合
     */
    @Test
    public void test7(){
        OgnlContext context = new OgnlContext();
        Person zhangsan =  new Person("zhangsan");
        Person lisi = new Person("lisi");
        Person wangwu = new Person("wangwu");
        List<Person> list = new ArrayList<>();
        list.add(zhangsan);
        list.add(lisi);
        list.add(wangwu);
        context.put("list",list);
        try {
            Object value = Ognl.getValue("#list.{name}", context, context.getRoot());
            System.out.println(value); //[zhangsan, lisi, wangwu]


            // TODO 还有问题，没有完全替换
            Object value2 = Ognl.getValue("#list.{#this.name.length()>4?'33':#this.name}", context, context.getRoot());
            System.out.println(value2);
        } catch (OgnlException e) {
            e.printStackTrace();
        }
    }

    /**
     *  将json数据通过json模版映射成新的json
     */
    @Test
    public void test8(){
        JSONObject srs = JSONObject.parseObject("{\"name\":\"zhangsan\",\"age\":12}");
        JSONObject tmp = JSONObject.parseObject("{\"tar_name\":\"#name\",\"tar_age\":\"@@round(#age*1000)\"}");
        JSONObject target = new JSONObject();
        OgnlContext context = new OgnlContext();
        Set<Map.Entry<String, Object>> entries = tmp.entrySet();
        Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
        while (iterator.hasNext()){
            Map.Entry<String, Object> next = iterator.next();
            String key = next.getKey();
            Object value = next.getValue();
            if (value.toString().contains("#")){
                try {
                    String replace = value.toString().replace("#", "");
                    Object src_value = Ognl.getValue(replace, context,srs);
                    System.out.println(src_value);
                    target.put(key,src_value);
                } catch (OgnlException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println(target.toJSONString());
    }

    /**
     *  将json数据通过json模版映射成新的json
     */
    @Test
    public void test9(){
        JSONObject srs = JSONObject.parseObject("{\"name\":\"zhangsan\",\"age\":12}");
        JSONObject tmp = JSONObject.parseObject("{\"tar_name\":\"#body.name\",\"tar_age\":\"@@round(#body.age+1)\",\"sex\":\"F\"}");
        JSONObject target = new JSONObject();
        OgnlContext context = new OgnlContext();
        context.put("body",srs);
        Set<Map.Entry<String, Object>> entries = tmp.entrySet();
        Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
        while (iterator.hasNext()){
            Map.Entry<String, Object> next = iterator.next();
            String key = next.getKey();
            Object value = next.getValue();
            if (value.toString().contains("#")){
                try {
                    Object expression = Ognl.parseExpression(value.toString());
                    Object src_value = Ognl.getValue(expression, context,context.getRoot());
                    target.put(key,src_value);
                } catch (OgnlException e) {
                    e.printStackTrace();
                }
            } else {
                target.put(key,value);
            }
        }
        System.out.println(target.toJSONString());
    }

    @Test
    public void test10(){
        String dogName  = Optional.ofNullable(person).map(p -> p.getDog()).map(d -> d.getName()).orElse("空了");
        System.out.println(dogName);
    }
}
