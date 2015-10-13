package net.deniro.land.module.system.action;

import net.deniro.land.common.service.dwz.Result;
import net.deniro.land.common.service.dwz.ResultError;
import net.deniro.land.module.system.service.UserService;
import nl.captcha.Captcha;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpSession;

/**
 * 用户
 *
 * @author deniro
 *         2015/10/10
 */
@Controller
@RequestMapping("/user")
public class UserController {

    static Logger logger = Logger.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    /**
     * 退出（登出）
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "/exit")
    public String exit(HttpSession session) {
        session.removeAttribute(UserService.USER_CODE);
        return "../../login";
    }


    /**
     * 登录
     *
     * @param account         账号
     * @param password        密码
     * @param code            验证码
     * @param loginSourceCode 登录来源
     * @param session         session
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Result login(String account, String password, String code, int
            loginSourceCode, HttpSession session) {

        try {
            //判断验证码是否正确
            Captcha captcha = (Captcha) session.getAttribute(Captcha.NAME);
            if (!captcha.isCorrect(code)) {
                return new ResultError("验证码不正确！");
            }

            Result result = userService.login(account, password, loginSourceCode);

            {//当前登录账号写入session
                if (result.isSuccess())
                    session.setAttribute(UserService.USER_CODE, result.get(UserService
                            .USER_CODE));
                if (session.getAttribute(UserService.USER_CODE) == null) {
                    logger.error("写入session不成功！");
                }
            }

            return result;
        } catch (Exception e) {
            logger.error("登录", e);
            return new ResultError();
        }
    }
}
