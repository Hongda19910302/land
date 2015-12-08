package net.deniro.land.common.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * UUID生成器
 *
 * @author deniro
 *         2015/8/5
 */
public class UUIDGenerator {

    /**
     * 获得一个UUID
     *
     * @return
     */
    public static String get() {
        String s = UUID.randomUUID().toString();

        //去除"-"符号
        return s.substring(0, 8) + s.substring(9, 13) + s.substring(19, 23) + s.substring(24);
    }

    /**
     * 获得多个UUID
     *
     * @param number UUID数量
     * @return
     */
    public static List<String> get(int number) {
        List<String> ss = new ArrayList<String>();

        if (number < 1) {
            return ss;
        }

        for (int i = 0; i < number; i++) {
            ss.add(get());
        }
        return ss;
    }
}
