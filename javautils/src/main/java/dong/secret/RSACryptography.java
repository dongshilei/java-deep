package dong.secret;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.pkcs.RSAPrivateKeyStructure;
import org.bouncycastle.asn1.x509.RSAPublicKeyStructure;

import javax.crypto.Cipher;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @program: java-deep
 * @description
 * @author: DONGSHILEI
 * @create: 2020/2/26 16:50
 **/
public class RSACryptography {
    public static String publicKeyString="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCISLP98M/56HexX/9FDM8iuIEQozy6kn2JMcbZS5/BhJ+U4PZIChJfggYlWnd8NWn4BYr2kxxyO8Qgvc8rpRZCkN0OSLqLgZGmNvoSlDw80UXq90ZsVHDTOHuSFHw8Bv//B4evUNJBB8g9tpVxr6P5EJ6FMoR/kY2dVFQCQM4+5QIDAQAB";
    public static void main(String[] args) throws Exception {
        //获取公钥
        PublicKey publicKey=getPublicKey(publicKeyString);
        //PublicKey publicKey=get();
        //PublicKey publicKey = getPublicKeyForPKCS1(publicKeyString);
        long millis = System.currentTimeMillis();
        String second = (millis / 1000L)+"" ;
        System.out.println(second);
        //公钥加密
        //String encryptedBytes=encrypt(second, publicKey);
       // String encryptedBytes = encryptPkcs1padding(publicKey, second);
        String encryptedBytes = encryptByPublicKey(second, publicKeyString);
        System.out.println("加密后："+ encryptedBytes);
    }
    //将base64编码后的公钥字符串转成PublicKey实例
    public static PublicKey getPublicKey(String publicKey) throws Exception{
        byte[ ] keyBytes= Base64.decodeBase64(publicKey.getBytes());
        X509EncodedKeySpec keySpec=new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory=KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }

  /*  public static PublicKey get() throws NoSuchAlgorithmException, InvalidKeySpecException {
        org.bouncycastle.asn1.pkcs.RSAPublicKey rsaPublicKey =
                org.bouncycastle.asn1.pkcs.RSAPublicKey.getInstance(org.bouncycastle.util.encoders.Base64.decode(publicKeyString));

        java.security.spec.RSAPublicKeySpec publicKeySpec =
                new java.security.spec.RSAPublicKeySpec(rsaPublicKey.getModulus(), rsaPublicKey.getPublicExponent());

        java.security.KeyFactory keyFactory = java.security.KeyFactory.getInstance("RSA");

        java.security.PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
        return publicKey;
    }*/
    //公钥加密
    public static String encrypt(String content, PublicKey publicKey) throws Exception{
        Cipher cipher=Cipher.getInstance("RSA/None/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        String outStr = Base64.encodeBase64String(cipher.doFinal(content.getBytes("UTF-8")));
        return outStr;
    }

    public static String encryptPkcs1padding(PublicKey publicKey, String content) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", "SunJCE");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        String outStr = Base64.encodeBase64String(cipher.doFinal(content.getBytes("UTF-8")));
        return outStr;
    }

    public static String encryptByPublicKey(String content, String base64PublicKeyStr) throws Exception {
        // 对公钥解密
        byte[] publicKeyBytes = Base64.decodeBase64(base64PublicKeyStr);
        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(publicKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        Key publicKey = keyFactory.generatePublic(x509KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return Base64.encodeBase64String(cipher.doFinal(content.getBytes("UTF-8")));

    }

    public static PublicKey getPublicKeyForPKCS1(String publicKeyString) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        // 取得私钥  for PKCS#1
        // 对公钥解密
        byte[] publicKeyBytes = Base64.decodeBase64(publicKeyString);
        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(publicKeyBytes);

        RSAPublicKeyStructure publicKeyStructure = new RSAPublicKeyStructure((ASN1Sequence) ASN1Sequence.fromByteArray(publicKeyString.getBytes("UTF-8")));
        RSAPublicKeySpec rsaPublicKeySpec = new RSAPublicKeySpec(publicKeyStructure.getModulus(), publicKeyStructure.getPublicExponent());
        KeyFactory keyFactory= KeyFactory.getInstance("RSA");
        PublicKey publicKey= keyFactory.generatePublic(rsaPublicKeySpec);
        return publicKey;
    }


}
