package net.deniro.land.common.spring;

import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;

/**
 * ��Դ·��
 *
 * @author deniro
 *         15-4-9����9:56
 */
public class ResourcePathExposer implements ServletContextAware {

    private ServletContext servletContext;
    private String resourceRoot;

    public void init() {
//        ʵ��Ӧ���У��������ⲿ�����ļ������ݿⱣ��Ӧ�õķ����汾�ţ��ڴ˻�ȡ��
        String version = "1.0.0";
//        ��Դ�߼�·������Ӧ�õķ����汾��
        resourceRoot = "/static-" + version;
//        ����Դ�߼�·����¶��ServletContext�������б���
//        ����JSP�ļ��Ϳ�ͨ��${resourceRoot}������ֵ
        getServletContext().setAttribute("resourceRoot", getServletContext().getContextPath() + resourceRoot);

    }

    public ServletContext getServletContext() {
        return servletContext;
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public String getResourceRoot() {
        return resourceRoot;
    }
}
