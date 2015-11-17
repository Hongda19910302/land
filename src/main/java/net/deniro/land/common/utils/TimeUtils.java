package net.deniro.land.common.utils;

import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间
 *
 * @author deniro
 *         2015/7/16
 */
@Log4j
public class TimeUtils {

    /**
     * 时间格式
     */
    public static SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd " +
            "HH:mm:ss");

    /**
     * 日期格式
     */
    public static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd ");

    /**
     * 获取当前日期（字符串格式）
     * <p/>
     * yyyy-MM-dd
     *
     * @return
     */
    public static String getCurrentDate() {
        return DATE_FORMAT.format(new Date());
    }

    /**
     * 获取当前时间
     * <p/>
     * yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String getCurrentTime() {
        return TIME_FORMAT.format(new Date());
    }

    /**
     * 解析字符串为日期对象
     *
     * @param time
     * @return
     */
    public static Date parse(String time) {
        if (StringUtils.isBlank(time)) {
            return new Date();
        } else {
            try {
                return TIME_FORMAT.parse(time);
            } catch (ParseException e) {
                log.error("解析字符串为日期对象", e);
                return new Date();
            }
        }
    }
}
