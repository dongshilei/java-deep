package dong;import com.alibaba.fastjson.JSON;import org.apache.commons.lang3.StringUtils;import java.io.UnsupportedEncodingException;import java.math.BigDecimal;import java.util.*;import java.util.regex.Pattern;/** * Created by DONGSHILEI on 2017/11/16 */public class Test {    private int count = 0;    public void recursion(){        count++;        recursion();    }    public void testStack(){        try {            recursion();        } catch (Exception e) {            System.out.println("deep of stack is "+ count);            e.printStackTrace();        }    }    public static void main(String[] args) {      /* String cd = "301518122900111546067597|-|2|-|查询成功|-|20181229|-|151142|-|U0|-|1|-|P2P2116|-|交易已超时|-||-||-||-||-||-|a|-|";        String[] split = cd.split("\\|-\\|");        System.out.println(split.length);*/      //String seq = "200219062402223523639348";      String seq = "200219091602203114691853";        int i = seq.hashCode() % 1024;        System.out.println(i);        final int a[] = {0};       a[0]++;       a[0]++;        System.out.println(a[0]);        System.out.println(Boolean.TRUE.toString());        List<String> list = new ArrayList<>();        list.add("1111");        list.add("2222");        System.out.println(JSON.toJSONString(list));        String task = "A1";        System.out.println(StringUtils.substring(task,1));        Map<String,String> map = new HashMap<>();        map.put("Content-Type","application/json");        map.put("code","30050");        System.out.println(map);    }}