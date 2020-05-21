package dong.secret;

import cn.hutool.core.codec.Base64;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @program: java-deep
 * @description
 * @author: DONGSHILEI
 * @create: 2020/2/26 16:07
 **/
public class RSACipher {
    private final static String CRYPTO_METHOD = "RSA";
   // private final static String CYPHER = "RSA/ECB/OAEPWITHSHA-256ANDMGF1PADDING";
    private final static String CYPHER = "RSA/ECB/OAEPWITHSHA-256ANDMGF1PADDING";
    private final static int CRYPTO_BITS = 2048;
    private static String PUB_KEY = "PUB_KEY";
    private static String PRIVATE_KEY = "PRIVATE_KEY";
    private static String CHARSET = "UTF-8";
    /*private final static int CRYPTO_BITS = 4096; This will encrypt in 4093bits, note however that is slower.*/

    public RSACipher() {
        KeyPair kp = getKeyPair();
        PublicKey publicKey = kp.getPublic();
        byte[] publicKeyBytes = publicKey.getEncoded();
        PUB_KEY = new String(Base64.encode(publicKeyBytes));
        //Save the public key so it is not generated each and every time
        PrivateKey privateKey = kp.getPrivate();
        byte[] privateKeyBytes = privateKey.getEncoded();
        PRIVATE_KEY = new String(Base64.encode(privateKeyBytes));
        //Also Save the private key so it is not generated each and every time
    }

    public static KeyPair getKeyPair() {
        KeyPair kp = null;
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance(CRYPTO_METHOD);
            kpg.initialize(CRYPTO_BITS);
            kp = kpg.generateKeyPair();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kp;
    }

    public static String encrypt(String clearText) {
        String encryptedBase64 = "";
        try {
            KeyFactory keyFac = KeyFactory.getInstance(CRYPTO_METHOD);
            KeySpec
                    keySpec =
                    new X509EncodedKeySpec(Base64.decode(PUB_KEY.trim()));
            Key key = keyFac.generatePublic(keySpec);
            final Cipher cipher = Cipher.getInstance(CYPHER);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptedBytes = cipher.doFinal(clearText.getBytes(CHARSET));
            encryptedBase64 = new String(Base64.encode(encryptedBytes));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encryptedBase64.replaceAll("(\\r|\\n)", "");
    }

    public static String decrypt(String encryptedBase64) {
        String decryptedString = "";
        try {
            KeyFactory keyFac = KeyFactory.getInstance(CRYPTO_METHOD);
            KeySpec keySpec = new PKCS8EncodedKeySpec(
                    Base64.decode(PRIVATE_KEY.trim()));
            Key key = keyFac.generatePrivate(keySpec);
            final Cipher cipher = Cipher.getInstance(CYPHER);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] encryptedBytes = Base64.decode(encryptedBase64);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            decryptedString = new String(decryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return decryptedString;
    }

    public static void main(String[] args) {
        RSACipher rsaCipher = new RSACipher();
        System.out.println("pub_key:" + PUB_KEY);
        System.out.println("private_key:" + PRIVATE_KEY);
        System.out.println("========================");
        long millis = System.currentTimeMillis();
        String second = (millis / 1000L)+"" ;
        System.out.println(second);
        String encrpytMsg = rsaCipher.encrypt(second);
        System.out.println(encrpytMsg);
        PUB_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgguC9ZvGgVaAzDQ429wDjjC5n/hecXbigrITL8rg6BrbbkBYdX+KAq9oPPoUzejufiSzKjJuSpeY9egtFXaPdB09Be0mWY1CXGourLL6E2KTsEeGyeXkhd3qNsijpuQhrzZb5xY2Jl5F/jx0ifw+UNPw/H5AF8ArvBiWH9KOK+H6afe8F28GdIEotZyfJRdXCZV0hiIbSulPZjt7xJPq0i5bXtnRQyyz4idC295IBsyaoJZrfvjMUzSyjWEfQ5MixWhhB37Qqah2tkcOpOd0zYPPRoA0smSJP9D0zxDL9PLf6cJwqTppC7lE+eoq1fmefv/ScmFqiyAPAwaQkZc0hwIDAQAB";
        encrpytMsg = rsaCipher.encrypt(second);
        System.out.println(encrpytMsg);
        //String decryptMsg = rsaCipher.decrypt(encrpytMsg);
        //System.out.println(decryptMsg);
    }
}
