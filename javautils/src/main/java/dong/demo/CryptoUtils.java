package dong.demo;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

/**
 * RSA加密解密 AES加密解密 RSA公私密钥生成
 *
 * @author 沈尚玉 2015-10-13
 *
 */
public class CryptoUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(CryptoUtils.class);

    /**
     * RSA公钥加密明文
     *
     * @param content
     *            待加密明文
     * @return 密文
     */
    public static String publicEnc(String content,String pk) {
        try {
            KeyFactory keyf = KeyFactory.getInstance("RSA");
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            PublicKey pubkey = null;
            InputStream is = new ByteArrayInputStream(pk.getBytes("utf-8"));

            byte[] pubbytes = new byte[new Long(pk.length()).intValue()];
            if (is.read(pubbytes) > 0) {
                X509EncodedKeySpec pubX509 = new X509EncodedKeySpec(
                        Base64.decode(new String(pubbytes)));

                pubkey = keyf.generatePublic(pubX509);
                cipher.init(Cipher.ENCRYPT_MODE, pubkey);
                byte[] cipherText = cipher.doFinal(content.getBytes());
                // 转换为Base64编码存储，以便于internet传送
                return Base64.encode(cipherText);
            }

        } catch (Exception e) {
            LOGGER.error("加密异常", e) ;
        }
        return "";
    }

    /**
     * RSA私钥解密密文
     *
     * @param content
     *            待解密密文
     * @return 明文
     */
    public static String privateDec(String content,String prikeyStr) {
        try {
            KeyFactory keyf = KeyFactory.getInstance("RSA");
            PrivateKey privkey = null;
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            InputStream key = new ByteArrayInputStream(
                    prikeyStr.getBytes("utf-8"));
            byte[] pribytes = new byte[new Long(prikeyStr.length()).intValue()];
            if (key.read(pribytes) > 0) {
                // 生成私钥
                PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(
                        Base64.decode(new String(pribytes)));
                privkey = keyf.generatePrivate(priPKCS8);
                cipher.init(Cipher.DECRYPT_MODE, privkey);
                byte[] newPlainText = cipher.doFinal(Base64.decode(content));

                return (new String(newPlainText));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";

    }

    /**
     * 解密 以String密文输入,String明文输出
     *
     * @param strMi
     *            加密后转换为base64格式的字符串
     * @param strKey
     *            加密用的Key
     * @return 解密的字符串
     *
     *         首先base64解密，而后在用key解密
     */
    public static String getDecString(String strMi, String strKey) {

        try {
            SecretKey secretKey = getKey(strKey);
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");// 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, key);// 初始化

            byte[] bytes = Base64.decode(strMi);

            byte[] result = cipher.doFinal(bytes);
            String strMing = new String(result, "utf-8");

            return strMing; // 解密
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * @param strKey
     *            密钥
     * @return 安全密钥
     *
     *         指定具体产生key的算法，跨操作系统产生 SecretKey，如果不指定，各种操作系统产生的安全key不一致。
     */
    public static SecretKey getKey(String strKey) {
        try {
            KeyGenerator _generator = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(strKey.getBytes());
            _generator.init(128, secureRandom);
            return _generator.generateKey();
        } catch (Exception e) {
            throw new RuntimeException("初始化密钥出现异常");
        }
    }

    /**
     * 加密以String明文输入,String密文输出
     *
     * @param strContent
     *            待加密字符串
     * @param strKey
     *            加密用的Key
     * @return 加密后转换为base64格式字符串
     */
    public static String getEncString(String strContent, String strKey) {
        String strMi = "";

        try {

            SecretKey secretKey = getKey(strKey);
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");// 创建密码器
            byte[] byteContent = strContent.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(byteContent);

            strMi = Base64.encode(result);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strMi; // 加密
    }

    /**
     * @param charCount
     *            字符串数量
     * @return 键盘上字符产生数量为charCount的随机字符串
     */
    public static String randChar(int charCount) {
        String charValue = "";
        // 生成随机字母串
        for (int i = 0; i < charCount; i++) {
            // 键盘上字符产生随机数
            char c = (char) (randomInt(33, 128));
            charValue += String.valueOf(c);
        }
        return charValue;
    }

    /**
     * 返回[from,to)之间的一个随机整数
     *
     * @param from
     *            起始值
     * @param to
     *            结束值
     * @return [from,to)之间的一个随机整数
     */
    public static int randomInt(int from, int to) {
        // Random r = new Random();
        return from + new Random().nextInt(to - from);
    }

    /**
     * @param path
     *            文件路径
     * @throws Exception
     *
     *             生成rsa 公私钥，加密base64编码存储到文件系统
     */
    public static void createKeyPairs(String path) throws Exception {
        // create the keys
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(1024, new SecureRandom());
        KeyPair pair = generator.generateKeyPair();
        PublicKey pubKey = pair.getPublic();
        PrivateKey privKey = pair.getPrivate();
        byte[] pk = pubKey.getEncoded();
        byte[] privk = privKey.getEncoded();
        // base64编码，屏蔽特殊字符
        String strpk = new String(Base64.encode(pk));
        String strprivk = new String(Base64.encode(privk));

        // 输出私钥文件
        File priKeyfile = new File(path + "rsa_pri_key.pem");

        FileOutputStream out = new FileOutputStream(priKeyfile);

        out.write(strprivk.getBytes());
        out.close();

        // 输出公钥文件
        File pubKeyfile = new File(path + "rsa_pub_key.pem");

        FileOutputStream outPub = new FileOutputStream(pubKeyfile);

        outPub.write(strpk.getBytes());

        outPub.close();
    }

    /**
     * RSA私钥解密密文
     *
     * @param content 待解密密文
     * @return 明文
     *
     *         秘钥utf-8, 内容base64-encode, 返回时也用utf-8
     *
     *         这里虽然叫privateDec， 其实， 只要是
     *         pub加密 priv解密，  或者priv加密， pub解密都可以
     */
    public static String rsaDecrypt(String content, String prikeyStr) {
        try {
            KeyFactory keyf = KeyFactory.getInstance("RSA");
            PrivateKey privkey = null;
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            InputStream key = new ByteArrayInputStream(prikeyStr.getBytes("utf-8"));
            byte[] pribytes = new byte[new Long(prikeyStr.length()).intValue()];
            if (key.read(pribytes) > 0) {
                // 生成私钥
                PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decode(new String(pribytes)));
                privkey = keyf.generatePrivate(priPKCS8);
                cipher.init(Cipher.DECRYPT_MODE, privkey);
                byte[] newPlainText = cipher.doFinal(Base64.decode(content));

                return (new String(newPlainText, "UTF-8"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";

    }

    //根据WacaiJsonResult中的sign字段(签名)， 对content字段验签
    static public void checksgin(LicaiJsonResult result, String checkSignKey  ) throws Exception {

        RSAUtil.verifyWithString(result.getContent(), checkSignKey, result.getSign());


    }
}
