package dong.httpclient;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.junit.Test;

/**
 * 微信小程序后台API测试
 */
public class WechatApiTest {



    @Test
    public void joinSchool(){
        String status = "http://localhost:8080/wechat/joinSchool?encode=myywgEo163hBcLho3p55Xg==";
        APIHttpClient ac = new APIHttpClient(status);
        JsonObject j = new JsonObject();
        //j.addProperty("encode", "myywgEo163hBcLho3p55Xg==");
        System.out.println(ac.post(j.toString()));
    }

    @Test
    public void getClasses(){
        String status = "http://localhost:8080/wechat/getClasses?schoolId=1";
        APIHttpClient ac = new APIHttpClient(status);
        JsonObject j = new JsonObject();
        System.out.println(ac.post(j.toString()));
    }

    @Test
    public void joinClass(){
        String status = "http://localhost:8080/wechat/joinClass?classId=1&nick=小猪佩奇&childName=佩奇&relation=爸爸";
        APIHttpClient ac = new APIHttpClient(status);
        JsonObject j = new JsonObject();
        System.out.println(ac.post(j.toString()));
    }

    @Test
    public void home(){
        String status = "http://localhost:8080/school/home";
        APIHttpClient ac = new APIHttpClient(status);
        JsonObject j = new JsonObject();
        System.out.println(ac.post(j.toString()));
    }

}
