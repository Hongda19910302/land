package net.deniro.land.common.spring;

import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;

/**
 * 资源路径
 *
 * @author deniro
 *         15-4-9上午9:56
 */
@Deprecated
public class ResourcePathExposer implements ServletContextAware {

    private ServletContext servletContext;
    private String resourceRoot;

    public void init() {
//        实际应用中，可以在外部属性文件或数据库保存应用的发布版本号，在此获取。
        String version = "3.3.1";
//        资源逻辑路径带上应用的发布版本号
        resourceRoot = "/static-" + version;
//        将资源逻辑路径暴露到ServletContext的属性列表中
//        这样JSP文件就可通过${resourceRoot}引用其值
        if (servletContext != null)
            getServletContext().setAttribute("resourceRoot", getServletContext().getContextPath() + resourceRoot);

    }

    public ServletContext getServletContext() {
        return servletContext;
    }

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public String getResourceRoot() {
        return resourceRoot;
    }
}
