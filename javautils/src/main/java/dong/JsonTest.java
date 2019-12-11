package dong;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @author DONGSHILEI
 * @create 2019-08-29 18:32
 * @since 1.0
 */
public class JsonTest {

    public static void main(String[] args) {
        String msg = "{\"bizType\":\"CREDIT_NOTICE\",\"body\":\"{\\\"workflowStatus\\\":\\\"success\\\",\\\"sign\\\":\\\"udo5PAmhAQMwzGdZ93fPIg\\\",\\\"processKey\\\":\\\"flow38\\\",\\\"outparam\\\":\\\"{\\\\\\\"frms_refuse_reason\\\\\\\":\\\\\\\"\\\\\\\\u5174\\\\\\\\u4E1A\\\\\\\\u673A\\\\\\\\u6784\\\\\\\\u62E6\\\\\\\\u622A,\\\\\\\\u591A\\\\\\\\u5934\\\\\\\\/\\\\\\\\u574F\\\\\\\\u8D26\\\\\\\\/\\\\\\\\u903E\\\\\\\\u671F\\\\\\\",\\\\\\\"frms_refuse_result\\\\\\\":\\\\\\\"REFUSE\\\\\\\",\\\\\\\"frms_verify_policy_labels\\\\\\\":\\\\\\\"\\\\\\\\u53CD\\\\\\\\u6B3A\\\\\\\\u8BC8\\\\\\\"}\\\",\\\"workflowErrorMsg\\\":\\\"\\\",\\\"userId\\\":\\\"1100000017\\\",\\\"flowId\\\":\\\"9b324ece-c72f-4d03-9e08-6d598809d103\\\",\\\"timestamp\\\":\\\"1567074421055\\\"}\",\"header\":{\"content-length\":\"440\",\"host\":\"10.26.67.195:9001\",\"content-type\":\"application/x-www-form-urlencoded\",\"connection\":\"Keep-Alive\",\"accept-encoding\":\"gzip,deflate\",\"accept\":\"text/plain, application/json, application/*+json, */*\",\"user-agent\":\"Apache-HttpClient/4.5.3 (Java/1.7.0_79)\"}}";
        JSONObject jsonObject = JSON.parseObject(msg);
        JSONObject body = jsonObject.getJSONObject("body");
        System.out.println(body.getJSONObject("outparam"));
    }
}
