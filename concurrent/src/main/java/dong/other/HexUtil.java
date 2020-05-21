/*
 * Copyright (C), 2002-2014, 苏宁易购电子商务有限公司
 * FileName: HexUtil.java
 * Author:   Andy_Wang01
 * Date:     Aug 26, 2014 10:59:23 AM
 * Description: //模块目的、功能描述
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package dong.other;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 *
 * @author Andy_Wang01
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class HexUtil {

    /**
     *
     * 十六进制字符串转字节数组
     *
     * @param hexstr 十六进制字符串
     * @return 字节数组
     */
    public static byte[] hexstr2Bytes(String hexstr) {
        String upper = hexstr.toUpperCase();
        int length = upper.length() / 2;
        byte[] ret = new byte[length];
        for (int i = 0; i < length; i++) {
            byte high = (byte) ("0123456789ABCDEF".indexOf(upper.charAt(2 * i)));
            byte low = (byte) ("0123456789ABCDEF".indexOf(upper.charAt(2 * i + 1)));
            ret[i] = (byte) ((high << 4) + low);
        }
        return ret;
    }

    /**
     *
     * 字节数组转十六进制字符串
     *
     * @param bytes 字节数组
     * @return 十六进制字符串
     */
    public static String bytes2Hexstr(byte[] bytes) {
        String ret = "";
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            ret += hex.toUpperCase();
        }
        return ret;
    }
}
