package dong.httpclient;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;

public class APIHttpClient {

    /**
     * 接口地址
     */
    private static String apiURL = "http://106.75.107.156:8080/camera/check";
    private static final String pattern = "yyyy-MM-dd HH:mm:ss:SSS";
    private HttpClient httpClient = null;
    private HttpPost method = null;
    private long startTime = 0L;
    private long endTime = 0L;
    private int status = 0;

    /**
     * 接口地址
     *
     * @param url
     */
    public APIHttpClient(String url) {

        if (url != null) {
            apiURL = url;
        }
        if (apiURL != null) {
            httpClient = HttpClients.createDefault();
            method = new HttpPost(apiURL);
        }
    }

    /**
     * 调用 API
     * @param parameters
     * @return
     */
    public String post(String parameters) {
        String body = null;
        if (method != null & parameters != null
                && !"".equals(parameters.trim())) {
            try {
                // 建立一个NameValuePair数组，用于存储欲传送的参数
                method.addHeader("Content-type","application/json; charset=utf-8");
                method.setHeader("Accept", "application/json");
                method.setEntity(new StringEntity(parameters, Charset.forName("UTF-8")));
                startTime = System.currentTimeMillis();

                HttpResponse response = httpClient.execute(method);
                endTime = System.currentTimeMillis();
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode != HttpStatus.SC_OK) {
                    status = 1;
                }
                // Read the response body
                body = EntityUtils.toString(response.getEntity());

            } catch (IOException e) {
                // 网络错误
                status = 3;
            } finally {
            }

        }
        return body;
    }

    public static void main(String[] args) {
        String checke = "http://zhs.ddweixiao.cn/zhihuishu/camera/check";
        //String checke = "http://localhost:8080/camera/check";
        //String status = "http://zhs.ddweixiao.cn/zhihuishu/camera/status";
        String status = "http://localhost:8080/camera/status";
        APIHttpClient ac = new APIHttpClient(checke);
        JsonArray arry = new JsonArray();
        JsonObject j = new JsonObject();
        j.addProperty("username", "bjzhs");
        j.addProperty("password", "dbd8851d87e230da9c5db5d00e664399");
        arry.add("sxt20171115T1407102");
        j.add("snList",arry);
        System.out.println(j.toString());
        long start = System.currentTimeMillis();
        System.out.println(ac.post(j.toString()));
        long end = System.currentTimeMillis();
        System.out.println(end-start);
    }
}