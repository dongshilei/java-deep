package dong.demo;


import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author luobin
 *
 *         RSA加密的内容不能超过128字节
 *
 *         目前的签名算法只能由 公私钥的生成方执行， 而客户端只能做验签。
 *
 *         如果要实现 客户端签名， 服务端验签 可以这样做： 先用 SHA1对明文做digest，然后用公钥加密 服务端验签的时候，
 *         先用私钥解密digest， 再用SHA1验证， 即可, 但是这样做的意义不大， 因为， 公钥对外是公开的， 任何人只要获得公钥，
 *         都可以对服务端发起请求。
 *
 *         要实现验证客户端身份的功能， 还是必须要由客户端生成密钥对， 由客户端上传公钥到服务端， 然后再由客户端用自己的私钥发起请求，
 *         才可以保证客户端的身份。
 *
 *
 *         而公钥的可靠性( 可能由恶意方伪造公钥)， 是靠证书机构保证的， 直接从证书机构下载证书， 获得可靠的公钥
 *
 *
 *         关于签名， 简单来说， 就是， 如果 A对B发送一个公钥, A自己用私钥进行签名， 那么B就可以可靠的确保
 *         预期从A获得的请求，一定真的是从A过来的， 没有篡改的可能， 反之不行。
 *
 */
public class RSAUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(RSAUtil.class);

    private static final String KEY_ALGORITHM = "RSA";
    private static final String SIGNATURE_ALGORITHM = "SHA1withRSA";

    /**
     * 用私钥对信息生成数字签名
     *
     * @param plain
     *            待签名明文
     * @param key
     *            私钥串
     *
     * @return 签名后的密文
     * @throws Exception
     */
    public static byte[] sign(byte[] plain, String key) throws Exception {
        PrivateKey privateKey = deSerializationPrivateKey(key);
        // 用私钥对信息生成数字签名
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateKey);
        signature.update(plain);

        return new Base64().encode(signature.sign());
    }


    public static String signAsString(String plainStr, String key)
            throws Exception {
        long start = System.currentTimeMillis();

        byte[] plain = plainStr.getBytes("ISO8859-1");
        LOGGER.info("before signname length:{} M", ((plain.length * 1.0d) / (1024 * 1024)));
        // System.out.println("before signname length:" +
        // ((plain.length*1.0d)/(1024*1024))+"M");
        byte[] mi = RSAUtil.sign(plain, key);
        LOGGER.info("after signname length:{} M", ((mi.length * 1.0d) / (1024 * 1024)));
        // System.out.println("after signname length:" +
        // ((mi.length*1.0d)/(1024*1024))+"M");

        long end = System.currentTimeMillis();
        LOGGER.info("signname time:{}", (end - start));
        // System.out.println("signname time:" + (end - start));
        return new String(mi, "ISO8859-1");
    }


    public static boolean verifyWithString(String plainStr, String key,
                                           String signStr) throws Exception {

        byte[] plain = plainStr.getBytes("ISO8859-1");
        byte[] sign = signStr.getBytes("ISO8859-1");
        return verify(plain, key, sign);
    }

    public static boolean verify(byte[] plain, String key, byte[] sign)
            throws Exception {

        // 取公钥匙对象
        PublicKey publicKey = deSerializationPublicKey(key);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(publicKey);
        signature.update(plain);

        // 验证签名是否正常
        return signature.verify(new Base64().decode(sign));
    }

    /**
     * 解密<br>
     * 用私钥解密
     *
     * @param sign
     *            密文
     * @param key
     *            私钥串
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] sign, String key)
            throws Exception {
        Key privateKey = deSerializationPrivateKey(key);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        return cipher.doFinal(new Base64().decode(sign));
    }

    /**
     * 解密<br>
     * 用私钥解密
     *
     * @param sign
     *            密文
     * @param key
     *            公钥串
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(byte[] sign, String key)
            throws Exception {
        Key publicKey = deSerializationPublicKey(key);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, publicKey);

        return cipher.doFinal(new Base64().decode(sign));
    }

    /**
     * 加密<br>
     * 用公钥加密
     *
     * @param plain
     *            明文
     * @param key
     *            公钥串
     * @return 密文
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] plain, String key)
            throws Exception {
        Key publicKey = deSerializationPublicKey(key);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        return new Base64().encode(cipher.doFinal(plain));
    }

    /**
     * 加密<br>
     * 用私钥加密
     *
     * @param plain
     *            明文
     * @param key
     *            私钥串
     * @return 密文
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(byte[] plain, String key)
            throws Exception {
        Key privateKey = deSerializationPrivateKey(key);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);

        return new Base64().encode(cipher.doFinal(plain));
    }

    /**
     * 从字符串中反序列化出公钥对象
     *
     * @param publicKeyStr
     *            公钥串
     * @return 公钥对象
     * @throws Exception
     */
    private static RSAPublicKey deSerializationPublicKey(String publicKeyStr)
            throws Exception {
        // Read key files back and decode them from BASE64
        byte[] publicKeyBytes = new Base64().decode(publicKeyStr.getBytes());

        // Convert back to public and private key objects
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
        return (RSAPublicKey) keyFactory.generatePublic(publicKeySpec);

    }

    /**
     * 从私钥串中反序列化出私钥对象
     *
     * @param privateKeyStr
     * @return
     * @throws Exception
     */
    private static RSAPrivateKey deSerializationPrivateKey(String privateKeyStr)
            throws Exception {
        // Read key files back and decode them from BASE64
        byte[] privateKeyBytes = new Base64().decode(privateKeyStr);

        // Convert back to public and private key objects
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        return (RSAPrivateKey) keyFactory.generatePrivate(privateKeySpec);
    }

    /**
     * 生成一对新的公私钥
     *
     * @return
     * @throws Exception
     */
    public static RSAKeyPair generateKeyPair() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator
                .getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(1024);

        KeyPair keyPair = keyPairGen.generateKeyPair();

        // 公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

        // 私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        byte[] privateKeyBytes = privateKey.getEncoded();
        byte[] publicKeyBytes = publicKey.getEncoded();
        String publicKeyJava = new String(new Base64().encode(publicKeyBytes));
        String privateKeyJava = new String(new Base64().encode(privateKeyBytes));
        String privateKeyNet = getRSAPrivateKeyAsNetFormat(privateKeyBytes);
        String publicKeyNet = getRSAPublicKeyAsNetFormat(privateKeyBytes);
        return new RSAKeyPair(publicKeyJava, privateKeyJava, publicKeyNet,
                privateKeyNet);
    }

    /**
     * @param encodedPrivkey
     * @return
     */
    private static String getRSAPublicKeyAsNetFormat(byte[] encodedPrivkey)
            throws Exception {
        StringBuffer buff = new StringBuffer(1024);

        PKCS8EncodedKeySpec pvkKeySpec = new PKCS8EncodedKeySpec(encodedPrivkey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPrivateCrtKey pvkKey = (RSAPrivateCrtKey) keyFactory
                .generatePrivate(pvkKeySpec);
        buff.append("<RSAKeyValue>");
        buff.append("<Modulus>"
                + new String(new Base64().encode(removeMSZero(pvkKey
                .getModulus().toByteArray()))) + "</Modulus>");
        buff.append("<Exponent>"
                + new String(new Base64().encode(removeMSZero(pvkKey
                .getPublicExponent().toByteArray()))) + "</Exponent>");
        buff.append("</RSAKeyValue>");
        return buff.toString().replaceAll("[ \t\n\r]", "");
    }

    private static String getRSAPrivateKeyAsNetFormat(byte[] encodedPrivkey)
            throws Exception {
        StringBuffer buff = new StringBuffer(1024);
        PKCS8EncodedKeySpec pvkKeySpec = new PKCS8EncodedKeySpec(encodedPrivkey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPrivateCrtKey pvkKey = (RSAPrivateCrtKey) keyFactory
                .generatePrivate(pvkKeySpec);
        buff.append("<RSAKeyValue>");
        buff.append("<Modulus>"
                + new String(new Base64().encode((removeMSZero(pvkKey
                .getModulus().toByteArray())))) + "</Modulus>");
        buff.append("<Exponent>"
                + new String(new Base64().encode((removeMSZero(pvkKey
                .getPublicExponent().toByteArray())))) + "</Exponent>");
        buff.append("<P>"
                + new String(new Base64().encode((removeMSZero(pvkKey
                .getPrimeP().toByteArray())))) + "</P>");
        buff.append("<Q>"
                + new String(new Base64().encode((removeMSZero(pvkKey
                .getPrimeQ().toByteArray())))) + "</Q>");
        buff.append("<DP>"
                + new String(new Base64().encode((removeMSZero(pvkKey
                .getPrimeExponentP().toByteArray())))) + "</DP>");
        buff.append("<DQ>"
                + new String(new Base64().encode((removeMSZero(pvkKey
                .getPrimeExponentQ().toByteArray())))) + "</DQ>");
        buff.append("<InverseQ>"
                + new String(new Base64().encode((removeMSZero(pvkKey
                .getCrtCoefficient().toByteArray())))) + "</InverseQ>");
        buff.append("<D>"
                + new String(new Base64().encode((removeMSZero(pvkKey
                .getPrivateExponent().toByteArray())))) + "</D>");
        buff.append("</RSAKeyValue>");
        return buff.toString().replaceAll("[ \t\n\r]", "");
    }

    private static byte[] removeMSZero(byte[] data) {
        byte[] data1;
        int len = data.length;
        if (data[0] == 0) {
            data1 = new byte[data.length - 1];
            System.arraycopy(data, 1, data1, 0, len - 1);
        } else
            data1 = data;
        return data1;
    }

    public static void main(String[] args) throws Exception {
        // java.security.KeyPairGenerator keyPairGen = KeyPairGenerator
        // .getInstance(KEY_ALGORITHM);
        // keyPairGen.initialize(1024);
        //
        // KeyPair keyPair = keyPairGen.generateKeyPair();
        //
        // // 公钥
        // RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        // // 对数据加密
        // Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        // cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        // byte[] signByteArr = cipher.doFinal("renshui".getBytes());
        // cipher = Cipher.getInstance(KEY_ALGORITHM);
        // PrivateKey priKey =
        // deSerializationPrivateKey(serializationPrivateKey((RSAPrivateKey)keyPair.getPrivate()));
        // cipher.init(Cipher.DECRYPT_MODE, priKey);
        // System.out.println(new String(cipher.doFinal(signByteArr)));
        // System.out.println(serializationPrivateKey((RSAPrivateKey)keyPair.getPrivate()));a
    }

    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    /**
     * <P>
     * 私钥解密
     * </p>
     *
     * @param encryptedData
     *            已加密数据
     * @param privateKey
     *            私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] decryptLongDataByPrivateKey(byte[] encryptedData,
                                                     String privateKey) throws Exception {
        byte[] keyBytes = Base64.decodeBase64(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher
                        .doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher
                        .doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    /**
     * <p>
     * 公钥解密
     * </p>
     *
     * @param encryptedData
     *            已加密数据
     * @param publicKey
     *            公钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] decryptLongDataByPublicKey(byte[] encryptedData,
                                                    String publicKey) throws Exception {
        byte[] keyBytes = Base64.decodeBase64(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher
                        .doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher
                        .doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    /**
     * <p>
     * 公钥加密
     * </p>
     *
     * @param data
     *            源数据
     * @param publicKey
     *            公钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] encryptLongDataByPublicKey(byte[] data,
                                                    String publicKey) throws Exception {
        byte[] keyBytes = Base64.decodeBase64(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }

    /**
     * <p>
     * 私钥加密
     * </p>
     *
     * @param data
     *            源数据
     * @param privateKey
     *            私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] encryptLongDataByByPrivateKey(byte[] data,
                                                       String privateKey) throws Exception {
        byte[] keyBytes = Base64.decodeBase64(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }
}
