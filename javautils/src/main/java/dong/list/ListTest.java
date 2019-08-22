package dong.list;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DONGSHILEI
 * @create 2017-12-12 16:54
 */
public class ListTest {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>(10);
        list.add("a");
        list.add("b");
        list.add("c");
        list.add(2,"hello");


        List<String> list2 = new ArrayList<>();
        list2.add("sss");
        list.addAll(list2);
        list.forEach(string ->{
            System.out.println(string);
        });
        for (final String s: list){
            System.out.println(s);
        }

        List<Long> list3 = new ArrayList<>();
        list3.add(new Long(1));
        list3.add(new Long(2));

        for (final Long li : list3){
            System.out.println(li);
        }
    }
}
