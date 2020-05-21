package dong;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.TreeMap;

/**
 * @author DONGSHILEI
 * @create 2019-08-29 18:32
 * @since 1.0
 */
public class JsonTest {

    public static void main(String[] args) {
       JSONObject json = new JSONObject();
       JSONObject jheader = new JSONObject();
       jheader.put("jh1","111");
       jheader.put("jh2","222");
       jheader.put("jh3","333");
       JSONObject jbody = new JSONObject();
       jbody.put("jb1","111");
       jbody.put("jb2","222");
       jbody.put("jb3","333");
       json.put("jheader",jheader);
       json.put("jbody",jbody);
        TreeMap<String,TreeMap<String,Object>> treeMap = JSONObject.parseObject(json.toJSONString(), TreeMap.class);
        System.out.println(treeMap.firstKey());
    }
}
