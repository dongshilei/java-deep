package dong.hashMap;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by DONGSHILEI on 2017/10/13
 */
public class HashMapTest {


    public static void main(String[] args) {
        test2();
    }

    private static void test1() {
        Country india = new Country("India",1000);
        Country japan = new Country("Japan",1000);
        Country france = new Country("France",2000);
        Country russia = new Country("Russia",2000);
        Country china = new Country("China",6000);
        Map<Country,String> countryStringMap = new HashMap<>();
        countryStringMap.put(india,"Delhi");
        countryStringMap.put(japan,"Tokyo");
        countryStringMap.put(france,"Paris");
        countryStringMap.put(russia,"Moscow");
        countryStringMap.put(china,"Beijing");
        //Entry遍历
        for (Map.Entry entry : countryStringMap.entrySet()){
            Country country = (Country)entry.getKey();
            System.out.println(country.getName()+"  "+entry.getValue());
        }
        // key 遍历
        for (Object key : countryStringMap.keySet()){
            Country country = (Country)key;
            System.out.println(country.getName()+"  "+countryStringMap.get(key));
        }
        // value 遍历
        for (Object value : countryStringMap.values()){
            System.out.println(value.toString());
        }
    }

    private static void test2(){
        Map<String,String> map = new HashMap<>();
        for(int i=0;i<10000000;i++){
            map.put(String.valueOf(i),String.valueOf(i));
        }
        System.out.println(map.size());
        Iterator<String> iterator = map.keySet().iterator();
        while (iterator.hasNext()){
            iterator.next();
            iterator.remove();
        }
        System.out.println(map.size());

    }
}
