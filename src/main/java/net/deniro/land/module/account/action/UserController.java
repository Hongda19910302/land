package net.deniro.land.module.account.action;

import net.deniro.land.common.service.dwz.Result;
import net.deniro.land.module.account.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @Autowired
    private UserService userService;

    /**
     * 登录
     *
     * @return
     */
    @RequestMapping(value = "/login")
    @ResponseBody
    public Result login(String account, String password, int
            loginSourceCode, HttpSession httpSession) {
        Result result = userService.login(account, password, loginSourceCode);

        if (result.isSuccess()) {//写入session
            httpSession.setAttribute(UserService.USER_CODE, result.get(UserService.USER_CODE));
        }

        return result;
    }
}
