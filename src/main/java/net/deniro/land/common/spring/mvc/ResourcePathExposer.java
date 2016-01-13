package net.deniro.land.common.spring.mvc;

import net.deniro.land.module.icase.entity.Modules;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;

/**
 * 资源路径
 *
 * @author deniro
 *         15-4-9上午9:56
 */
public class ResourcePathExposer implements ServletContextAware {

    private ServletContext servletContext;
    private static String resourceRoot;

    @Autowired
    private Modules modules;

    public void init() {
//        实际应用中，可以在外部属性文件或数据库保存应用的发布版本号，在此获取。
        String version = "3.9.0";
//        资源逻辑路径带上应用的发布版本号
        resourceRoot = "/static-" + version;
//        将资源逻辑路径暴露到ServletContext的属性列表中
//        这样JSP文件就可通过${resourceRoot}引用其值
        if (servletContext != null){
            servletContext.setAttribute("resourceRoot", getServletContext().getContextPath() + resourceRoot);
            servletContext.setAttribute("platformInfo","移动巡查执法平台 v"+version);//平台信息
            servletContext.setAttribute("copyright","Copyright &copy; 2015-2016 deniro " +
                    "All Rights Reserved.");//版本信息
        }

        //模块初始化
        modules.init();




    }

    public ServletContext getServletContext() {
        return servletContext;
    }

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public static String getResourceRoot() {
        return resourceRoot;
    }
}
