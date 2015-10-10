package net.deniro.land.common.utils;

import net.deniro.land.common.utils.SpringContextUtils;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * 属性文件读取器
 *
 * @author deniro
 *         2015/10/10
 */
public class PropertiesReader {

    private static ResourceBundleMessageSource messageSource = SpringContextUtils.getBean("resourceBundleMessageSource");

    /**
     * 获取属性值
     * @param name 属性名称
     * @return
     */
    public static String value(String name) {
        return messageSource.getMessage(name, null, "Default", null);
    }
}
