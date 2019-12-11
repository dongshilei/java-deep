package dong.demo;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public class secretKeyTest {
    public static void main(String[] args) throws Exception {
        //RSA公钥
        String rsa = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCH8T0cjLTg07VbK40Gn0lbAAlvZY1Fv5kY9Ro85xo/dOBJRCznfiE11cP0Irl7n6G7oiFkL0D7FGwOizh05xLASgrlUVnuaJKlRUKkMWhQB2JwClirhIcUD5RQpymIC0CRHY8YClRQVBxurN91jvVBwnnmmyuX9FTLkVb3IHJ6twIDAQAB";

        Map content = new HashMap();
        content.put("appId", "928820396");
        content.put("saleChannel", "1056");
        content.put("productId", "568");
        content.put("saleChannelKey", "");
        content.put("productIdKey", "");
        String jsonContent = JSONObject.toJSONString(content);
        System.out.println("加密前："+jsonContent);
        String aesKey = CryptoUtils.randChar(10); // AES的密钥
        System.out.println("AES的密钥："+aesKey);
		String ras_aesKey = CryptoUtils.publicEnc(aesKey,rsa); // 经过RSA加密后的AES的密钥
        System.out.println("经过RSA加密后的AES的密钥："+ras_aesKey);
		String resultMi = CryptoUtils.getEncString(jsonContent, aesKey);
		System.out.println("加密后："+resultMi);
		//私钥
        String rsaStr="MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIfxPRyMtODTtVsrjQafSVsACW9ljUW/mRj1GjznGj904ElELOd+ITXVw/QiuXufobuiIWQvQPsUbA6LOHTnEsBKCuVRWe5okqVFQqQxaFAHYnAKWKuEhxQPlFCnKYgLQJEdjxgKVFBUHG6s33WO9UHCeeabK5f0VMuRVvcgcnq3AgMBAAECgYBpjdg8vciItfogg21qUe48eYfZ9kk+einfJhmsZmmMEi1A1m3jhZ011vjjLL9HDUkgjyBMUSCslEve8xzwMKfudtcU525eCubc+MzkJ48qyoBF8HPJoHxRWoRkaCdHGwOXXX2AKEPacR2NPlf70B0WT5s5IWQvW2R0wVqp+1q8EQJBAPlPBYbIZkgrP0Vlj2V0uSgjrOCHqEFlkwQ7lzSf0UMs2cubkoqeKOhQTBLOTySKyH4BWF7U9BN8Tz1KN8N2XP8CQQCLl0lrYVWxIQ3VCE5CvwLb3oRLnpWFHVG9sCd+D1mCiIxltnW/rrwviLYQVsE6Aq/l4UbxGM2qdtFegGhr8ApJAkA6e+0h9zT3TR3km7SN6lndLrFJYsl3vepFHe2UrMEcbxMQjohL+FpEVUHjT36FZgEufgZLCM3RHGJCUHzQX53lAkEAhZ5aVCRGz5fRUsNxjmijBu4X+v6hJ1uqXAXbt8pfpxioM9CVI9fSIToe9MLmkW3zC/w5WR2h+PNldK07x15tqQJAWbwlQ6eplDrSGiiNW8R+ccB+xBFBAO6ZAmRCEmN58ecgRy70h5+AfvhQygZIrPudbZTIhVEoquvBLYmFwKqOIg==";
        String aeskey = CryptoUtils.privateDec(ras_aesKey, rsaStr);
        System.out.println("解密后的AES的密钥"+aesKey);
        String resultStr = CryptoUtils.getDecString(resultMi, aeskey);
        System.out.println("解密后："+resultStr);
       
    }
}
