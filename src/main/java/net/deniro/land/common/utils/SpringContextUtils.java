package net.deniro.land.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Spring 上下文环境工具
 * <p/>
 * 可用于多线程环境下，将Bean注入thread
 *
 * @author deniro
 *         2015/6/11
 */
public class SpringContextUtils implements ApplicationContextAware {

    /**
     * 上下文环境
     */
    private static ApplicationContext context;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    /**
     * 获取Bean
     *
     * @param beanName
     * @param <T>
     * @return
     */
    public static <T> T getBean(String beanName) {
        return (T) context.getBean(beanName);
    }
}
