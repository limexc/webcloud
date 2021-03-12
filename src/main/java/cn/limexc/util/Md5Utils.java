package cn.limexc.util;


import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Utils {
    private static MessageDigest md5;

    static {
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static String getStrMd5(String str){

        byte[] bytes = null;

        try {
            bytes = md5.digest(str.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        StringBuilder strBuild = new StringBuilder();

            for (byte bt:bytes){
                if ((bt & 0xff) >> 4 == 0) {
                    strBuild.append("0").append(Integer.toHexString(bt & 0xff));
                } else {
                    strBuild.append(Integer.toHexString(bt & 0xff));
                }
            }

            return strBuild.toString();

    }

}
