package net.deniro.land.common.spring;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * Spring容器加载所有Bean后，执行的处理器
 * <p/>
 * 可在这里做一些初始化工作
 *
 * @author deniro
 *         2015/6/11
 */
public class AfterAllBeanInitProcessor implements ApplicationListener<ContextRefreshedEvent> {

    static Logger logger = Logger.getLogger(AfterAllBeanInitProcessor.class);

    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        //验证是否是root applcationContext，避免在多个容器（root容器和项目容器）中执行多次
        if (contextRefreshedEvent.getApplicationContext().getParent() != null) {
            return;
        }

        //初始化
        logger.info("开始初始化工作...");

    }

}
