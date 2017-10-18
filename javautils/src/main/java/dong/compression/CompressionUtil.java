package dong.compression;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * 字符串压缩/还原
 * Created by DONGSHILEI on 2017/10/12
 */
public class CompressionUtil {

    /**
     * 使用gzip进行压缩
     * @param primStr
     * @return
     */
    public static String gzip(String primStr){
        if (primStr==null||primStr.length()==0){
            return primStr;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip = null;
        try {
            gzip = new GZIPOutputStream(out);
            gzip.write(primStr.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (gzip!=null){
                try {
                    gzip.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return new BASE64Encoder().encode(out.toByteArray());
    }

    public static String gunzip(String compressedStr){
        if (compressedStr==null){
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = null;
        GZIPInputStream ginzip = null;
        byte[] compressed = null;
        String decompressed = null;
        try {
            compressed = new BASE64Decoder().decodeBuffer(compressedStr);
            in = new ByteArrayInputStream(compressed);
            ginzip = new GZIPInputStream(in);
            byte[] buffer = new byte[1024];
            int offset = -1;
            while ((offset=ginzip.read(buffer))!=-1){
                out.write(buffer,0,offset);
            }
            decompressed = out.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ginzip!=null){
                try {
                    ginzip.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (in!=null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out!=null){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return decompressed;
    }

    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        Random r = new Random(10);
        for (int i=0;i<10000;i++){
            sb.append(r.nextDouble());
        }
        //原字符串长度
        System.out.println(sb.length());
        String gzip = gzip(sb.toString());
        //压缩后字符串长度
        System.out.println(gzip.length());
        String gunzip = gunzip(gzip);
        //加压后字符串长度
        System.out.println(gunzip.length());
        // 压缩比接近 三分二
        //如果是有规律的字符串，压缩比更低
    }

}
