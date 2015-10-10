package net.deniro.land.common.utils;

import java.security.MessageDigest;

/**
 * md5工具
 *
 * @author deniro
 *         2015/10/10
 */
public class Md5Utils {

    /**
     * 加密
     *
     * @param s
     * @return 返回32位加密后的字符串
     */
    public static String encryptIn32(String s) {
        char[] hexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

        try {
            byte[] e = s.getBytes();
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(e);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char[] str = new char[j * 2];
            int k = 0;

            for (int i = 0; i < j; ++i) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 15];
                str[k++] = hexDigits[byte0 & 15];
            }

            return (new String(str)).toLowerCase();
        } catch (Exception var10) {
            return null;
        }
    }

    /**
     * 加密
     *
     * @param s
     * @return 返回16位加密后的字符串
     */
    public static String encryptIn16(String s) {
        return encryptIn32(s).substring(8, 24);
    }

    /**
     * 字节数组转化为字符串
     *
     * @param b
     * @return
     */
    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";

        for (int n = 0; n < b.length; ++n) {
            stmp = Integer.toHexString(b[n] & 255);
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }

            if (n < b.length - 1) {
                hs = hs + ":";
            }
        }

        return hs.toUpperCase();
    }

}
