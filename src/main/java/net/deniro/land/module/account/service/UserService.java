package net.deniro.land.module.account.service;

import net.deniro.land.common.service.Result;
import net.deniro.land.common.utils.Md5Utils;
import net.deniro.land.common.utils.PropertiesReader;
import net.deniro.land.module.account.dao.UserDao;
import net.deniro.land.module.account.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static net.deniro.land.module.account.entity.User.LoginSource;
import static net.deniro.land.module.account.entity.User.UserStatus;

/**
 * 用户
 *
 * @author deniro
 *         2015/10/10
 */
public class UserService {

    static Logger logger = Logger.getLogger(UserService.class);

    @Autowired
    private UserDao userDao;

    /**
     * 用户码（用于向action传递参数）
     */
    public static final String USER_CODE = "user";

    /**
     * 登录
     *
     * @param account         账号
     * @param password        密码
     * @param loginSourceCode 登录来源码
     * @return
     */
    public Result login(String account, String password, int
            loginSourceCode) {

        Result result = new Result();

        /**
         * 验证
         */
        if (StringUtils.isBlank(account)) {
            result.setMessage("请输入账号！");
            return result;
        }

        if (StringUtils.isBlank(password)) {
            result.setMessage("请输入密码！");
            return result;
        }

        LoginSource loginSource = LoginSource.get(loginSourceCode);
        if (loginSource == null) {
            result.setMessage("登录来源码非法！");
            logger.error("登录来源码非法！loginSourceCode:" + loginSourceCode);
            return result;
        }

        List<User> users = userDao.findByAccount(account);
        if (users == null || users.isEmpty()) {
            result.setMessage("不存在这个账号！");
            return result;
        } else if (users.size() > 1) {
            logger.warn("存在 " + users.size() + " 个相同账号的用户！");
        }

        User user = users.get(0);
        int statusCode = user.getStatus();
        UserStatus userStatus = UserStatus.get(statusCode);
        if (userStatus == null) {
            result.setMessage("账号状态码非法！");
            logger.error("账号状态码非法！statusCode:" + statusCode);
            return result;
        }
        switch (userStatus) {
            case FREEZE:
                result.setMessage("该账号已被冻结！");
                return result;
            case CANCEL:
                result.setMessage("该用户已被注销！");
                return result;
            default:
                break;
        }

        switch (loginSource) {
            case WEB:
                password = Md5Utils.encryptIn16(password);
                if (!StringUtils.equals(password, user.getPassword())) {
                    result.setMessage("密码错误！");
                    return result;
                }
                break;
            case ANDROID:
                if (!StringUtils.equals(password, PropertiesReader.value("gzty.android" +
                        ".password"))) {
                    result.setMessage("密码错误！");
                    return result;
                }
                break;
            default:
                break;
        }


        result.set(USER_CODE, user);
        result.setSuccess(true);
        result.setMessage("登陆成功！");
        return result;
    }

}