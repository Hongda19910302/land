package net.deniro.land.common.spring.mvc;

import net.deniro.land.module.system.entity.User;
import net.deniro.land.module.system.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * session超时拦截器
 *
 * @author deniro
 *         2015/11/20
 */
public class SessionTimeoutInterceptor implements HandlerInterceptor {

    static Logger logger = Logger.getLogger(SessionTimeoutInterceptor.class);

    /**
     * 不需要进行拦截的URL
     */
    private List<String> allowUrls;

    public void setAllowUrls(List<String> allowUrls) {
        this.allowUrls = allowUrls;
    }

    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        //获取请求的URL
        String requestUrl = httpServletRequest.getRequestURI().replace(httpServletRequest
                .getContextPath(), "");
        logger.info("请求的URL：" + requestUrl);

        //遇到不需要拦截的URL，就直接放行
        if (allowUrls != null && !allowUrls.isEmpty()) {
            for (String allowUrl : allowUrls) {
                if (requestUrl.contains(allowUrl)) {
                    return true;
                }
            }
        }

        /**
         * 判断session是否存在
         */
        User user = (User) httpServletRequest.getSession().getAttribute(UserService.USER_CODE);
        if (user != null) {//放行
            return true;
        } else {//session已失效，抛出异常
            logger.warn("非法请求："+requestUrl);
            throw new SessionTimeoutException();
        }
    }

    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
