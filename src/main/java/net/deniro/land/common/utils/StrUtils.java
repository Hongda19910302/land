package net.deniro.land.common.utils;

import org.apache.commons.lang3.text.WordUtils;

/**
 * 字符串
 *
 * @author deniro
 *         2015/10/23
 */
public class StrUtils {

    /**
     * 替换字符串中的下划线，并让它的下一个字母为大写
     *
     * @param srcStr        原字符串
     * @param srcFirstUpper 原字符串是否大写
     * @return
     */
    public static String replaceUnderLineAndFirstUpper(String srcStr, boolean
            srcFirstUpper) {
        String findStr = "_";
        StringBuilder newStr = new StringBuilder("");
        int first = 0;

        if (srcFirstUpper) {
            srcStr = WordUtils.capitalize(srcStr);
        }

        while (srcStr.indexOf(findStr) != -1) {//还未结束，继续处理
            first = srcStr.indexOf(findStr);
            if (first != srcStr.length()) {
                newStr.append(srcStr.substring(0, first));//添加下划线之前的字符串
                srcStr = srcStr.substring(first + findStr.length(), srcStr.length());
                //获取原字符串中未处理过的字符串
                srcStr = WordUtils.capitalize(srcStr);//首字母大写
            }
        }
        newStr.append(srcStr);
        return newStr.toString();
    }
}
