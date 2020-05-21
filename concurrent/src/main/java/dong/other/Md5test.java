package dong.other;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5test {
	public static void main(String[] args) throws Exception {
		String custName = "贺琦芳";
		String idNo = "141124199204090079";
		String custNameMD5 = md5(custName);
		String idNoMD5 = md5(idNo);
		System.out.println("idNoMD5:"+idNoMD5+" [custNameMD5]:"+custNameMD5);
		}
		/**
		* 对一段明文进行md5处理
		* @param plain
		* @return
		 * @throws NoSuchAlgorithmException 
		*/
		private static String md5(String plain) throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance("MD5");
		digest.update(plain.getBytes(StandardCharsets.UTF_8));
		byte[] bytes = digest.digest();
		return HexUtil.bytes2Hexstr(bytes);
		}
		private static String bytes2Hexstr(byte[] bytes) {
		StringBuilder sb = new StringBuilder(32);
		for(int i = 0; i < bytes.length; ++i) {
		String hex = Integer.toHexString(bytes[i] & 255);
		if(hex.length() == 1) {
		hex = '0' + hex;
		}
		sb.append(hex.toUpperCase());
		}
		return sb.toString();
		}

}
