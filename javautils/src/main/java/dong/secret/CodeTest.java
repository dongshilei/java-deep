package dong.secret;

import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.binary.Base64;
import org.junit.Test;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * @author DONGSHILEI
 * @create 2017-12-05 18:08
 */
public class CodeTest {

    private static final String ENCODING = "UTF-8";

    @Test
    public void testBase64() throws IOException {
        String code = "{\"user\":1212121}";
        String password = "dsl";
        String encode = new BASE64Encoder().encode(code.getBytes());
        System.out.println(encode);
        byte[] bytes = new BASE64Decoder().decodeBuffer(encode);
        System.out.println(new String(bytes));

        Base64 base64 = new Base64();
        System.out.println(base64.encodeAsString(code.getBytes()));

        String s = base64.encodeAsString(AESUtil.encrypt(code, password).getBytes());
        System.out.println(s);
        String decrypt = AESUtil.decrypt(new String(base64.decode(s)), password);
        System.out.println(decrypt);
    }

    @Test
    public void encode() throws UnsupportedEncodingException {
        String str = "密钥存储（密钥是一段二进制数据，密钥在通信双方的传递过程";
        byte[] bytes = Base64.encodeBase64(str.getBytes(ENCODING));
        System.out.println(new String(bytes,ENCODING));
    }

    @Test
    public void encodeSafe() throws UnsupportedEncodingException {
        String str = "密钥存储（密钥是一段二进制数据，密钥在通信双方的传递过程";
        byte[] bytes = Base64.encodeBase64(str.getBytes(ENCODING),true);
        System.out.println(new String(bytes,ENCODING));
    }


    @Test
    public void encodeUrl() throws UnsupportedEncodingException {
        String str = "密钥存储（密钥是一段二进制数据，密钥在通信双方的传递过程";
        byte[] bytes = Base64.encodeBase64URLSafe(str.getBytes(Charsets.UTF_8));
        System.out.println(new String(bytes,Charsets.UTF_8));
    }
}

