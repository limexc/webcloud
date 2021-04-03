package cn.limexc.util;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;
@PropertySource("classpath:system.properties")
public class StrMd5Utils {
    //可以自定义生成MD5 加密字符传前的混合 KEY
    @Value("${vecode.confuse}")
    private static String KEY;

    /**
     * 字符串进行MD5计算
     * @param key 进行计算的字符串
     * @return  MD5值
     */
    public static String MD5(String key) {
        char hexDigits[] = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
        };
        try {
            byte[] btInput = key.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str).toLowerCase();
        } catch (Exception e) {
            return null;
        }
    }


//    public static void main(String[] args) {
//        String sLongUrl = "http://www.baidu.com/121244/ddd";
//        for (String shortUrl : shortUrl(sLongUrl)) {
//            System.out.println(shortUrl);
//        }
//    }

    /**
     * ！！！！未测试！！！！
     * 生成重复概率较小的短码，可以用做分享文件的url，或者再改进一下，查一下数据库再放？
     * 该方法存在碰撞的可能性，解决冲突会比较麻烦。不过该方法生成的短码位数是固定的，也不存在连续生成的短码有序的情况。
     * @param url  传入字符串
     * @return     四组6位短码
     */

    public static String[] shortUrl(String url) {


        //要使用生成的字符
        String[] chars = new String[]{"a", "b", "c", "d", "e", "f", "g", "h",
                "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
                "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
                "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H",
                "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
                "U", "V", "W", "X", "Y", "Z"
        };
        //对传入字符串进行MD5加密
        String sMD5EncryptResult = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update((KEY + url).getBytes());
            byte[] digest = md.digest();
            sMD5EncryptResult = DatatypeConverter.printHexBinary(digest).toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        String[] resUrl = new String[4];
        //得到4组短链接字符串
        for (int i = 0; i < 4; i++) {
            //把加密字符按照8位一组16进制与0x3FFFFFFF进行位与运算
            String sTempSubString = sMD5EncryptResult.substring(i * 8, i * 8 + 8);
            //这里需要使用 long 型来转换，因为 Inteper.parseInt() 只能处理31位,首位为符号位,如果不用long，则会越界
            long lHexLong = 0x3FFFFFFF & Long.parseLong(sTempSubString, 16);
            String outChars = "";
            //循环获得每组6位的字符串
            for (int j = 0; j < 6; j++) {
                // 把得到的值与 0x0000003D 进行位与运算，取得字符数组 chars 索引(具体需要看chars数组的长度以防下标溢出，注意起点为0)
                long index = 0x0000003D & lHexLong;
                //把取得的字符相加
                outChars += chars[(int) index];
                //每次循环按位右移5位
                lHexLong = lHexLong >> 5;
            }
            //把字符串存入对应索引的输出数组
            resUrl[i] = outChars;
        }
        return resUrl;
    }



}
