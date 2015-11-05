package net.deniro.land.mobile;

import net.deniro.land.common.service.dwz.Result;
import net.deniro.land.mobile.entity.LoginParam;
import net.deniro.land.mobile.entity.LoginResult;
import net.deniro.land.mobile.entity.ResultCode;
import net.deniro.land.module.system.entity.User;
import net.deniro.land.module.system.service.UserService;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 移动客户端接口
 *
 * @author deniro
 *         2015/11/5
 */
@Controller
@RequestMapping("gtweb/android")
public class MobileController {

    @Autowired
    private UserService userService;

    /**
     * 登录
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "/user-login")
    @ResponseBody
    public LoginResult login(LoginParam param) {

        /**
         * 尝试登录
         */
        Result result = userService.login(param.getAccount(), param.getPassword(), NumberUtils
                .toInt(param
                        .getLoginType
                                ()));

        /**
         * 生成结果
         */
        LoginResult loginResult = new LoginResult();
        if (result.isSuccess()) {//成功后，设置账户对象
            loginResult.setUser((User) result.get(UserService.USER_CODE));
            loginResult.setResult(ResultCode.SUCCESS.value());
        } else {//失败
            loginResult.setResult(ResultCode.FAILURE.value());
        }
        loginResult.setDescribe(result.getMessage());

        return loginResult;
    }

}
