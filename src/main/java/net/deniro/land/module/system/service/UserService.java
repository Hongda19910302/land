package net.deniro.land.module.system.service;

import net.deniro.land.common.dao.Page;
import net.deniro.land.common.service.dwz.Result;
import net.deniro.land.common.service.dwz.ResultError;
import net.deniro.land.common.service.dwz.ResultSuccess;
import net.deniro.land.common.utils.Md5Utils;
import net.deniro.land.common.utils.PropertiesReader;
import net.deniro.land.module.system.dao.UserDao;
import net.deniro.land.module.system.entity.User;
import net.deniro.land.module.system.entity.UserQueryParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static net.deniro.land.module.system.entity.User.*;

/**
 * 用户
 *
 * @author deniro
 *         2015/10/10
 */
@Service
public class UserService {

    static Logger logger = Logger.getLogger(UserService.class);

    @Autowired
    private UserDao userDao;


    /**
     * 用户码（用于向action传递参数）
     */
    public static final String USER_CODE = "user";

    /**
     * 判断账号是否已存在
     * @param account
     * @return
     */
    public boolean isExisted(String account){
        List<User> users=userDao.findByAccount(account);
        return !(users == null || users.isEmpty());

    }

    /**
     * 更新
     *
     * @param entity
     * @return
     */
    public boolean update(User entity) {
        try {
            userDao.update(entity);
            return true;
        } catch (Exception e) {
            logger.error("更新", e);
            return false;
        }
    }

    /**
     * 新增
     *
     * @param entity
     * @return
     */
    public boolean add(User entity) {
        try {
            userDao.save(entity);
            return true;
        } catch (Exception e) {
            logger.error("新增", e);
            return false;
        }
    }

    /**
     * 依据ID，获取用户对象
     *
     * @param userId
     * @return
     */
    public User findById(Integer userId) {
        try {
            return userDao.get(userId);
        } catch (Exception e) {
            logger.error("依据ID，获取用户对象", e);
            return new User();
        }
    }

    /**
     * 更新密码
     *
     * @param userId 用户ID
     * @param pwd    新密码
     * @return
     */
    public boolean updatePwd(Integer userId, String pwd) {
        try {
            int count = userDao.updatePwd(userId, pwd);
            return count > 0;
        } catch (Exception e) {
            logger.error("更新密码", e);
            return false;
        }
    }

    /**
     * 依据ID，获取用户
     *
     * @param userId
     * @return
     */
    public User get(Integer userId) {
        try {
            return userDao.get(userId);
        } catch (Exception e) {
            return new User();
        }
    }

    /**
     * 依据部门ID，获取巡查员列表
     *
     * @param departmentId
     * @return
     */
    public List<User> findInspectorsByDepartmentId(Integer departmentId) {
        try {
            return userDao.findInspectorsByDepartmentId(departmentId);
        } catch (Exception e) {
            logger.error("依据部门ID，获取巡查员列表", e);
            return new ArrayList<User>();
        }
    }

    /**
     * 依据创建者ID，获取巡查员列表
     *
     * @param creatorId
     * @return
     */
    public List<User> findInspectorsByCreatorId(Integer creatorId) {
        try {
            return userDao.findInspectorsByCreatorId(creatorId);
        } catch (Exception e) {
            logger.error("依据创建者ID，获取巡查员列表", e);
            return new ArrayList<User>();
        }
    }

    /**
     * 分页查询
     *
     * @param queryParam 查询参数
     * @return
     */
    public Page findPage(UserQueryParam queryParam) {
        try {
            return userDao.findPage(queryParam);
        } catch (Exception e) {
            logger.error("分页查询", e);
            return new Page();
        }
    }

    /**
     * 登录
     *
     * @param account         账号
     * @param password        密码
     * @param loginSourceCode 登录来源码；0-android；1-web
     * @param loginTypeCode   登录类型码；0-常规；1-工作添翼登录
     * @return
     */
    public Result login(String account, String password, int
            loginSourceCode, int loginTypeCode) {

        Result result = new ResultError();

        try {
            /**
             * 验证参数是否为空
             */
            if (StringUtils.isBlank(account)) {
                result.setMessage("请输入账号！");
                return result;
            }
            if (StringUtils.isBlank(password)) {
                result.setMessage("请输入密码！");
                return result;
            }

            /**
             * 验证账号是否存在
             */
            List<User> users = userDao.findByAccount(account);
            if (users == null || users.isEmpty()) {
                result.setMessage("不存在这个账号！");
                return result;
            } else if (users.size() > 1) {
                logger.warn("存在 " + users.size() + " 个相同账号的用户！");
            }
            User user = users.get(0);

            /**
             * 验证账号状态
             */
            int statusCode = user.getStatus();
            Status status = Status.get(statusCode);
            if (status == null) {
                result.setMessage("账号状态码非法！");
                logger.error("账号状态码非法！statusCode:" + statusCode);
                return result;
            }
            switch (status) {
                case FREEZE:
                    result.setMessage("该账号已被冻结！");
                    return result;
                case CANCEL:
                    result.setMessage("该用户已被注销！");
                    return result;
                default:
                    break;
            }

            /**
             * 验证密码
             */
            LoginSource loginSource = LoginSource.get(loginSourceCode);
            if (loginSource == null) {
                result.setMessage("登录来源码非法！");
                logger.error("登录来源码非法！loginSourceCode:" + loginSourceCode);
                return result;
            }
            switch (loginSource) {
                case WEB://md5加密后验证
                    password = Md5Utils.encryptIn16(password);
                    if (!StringUtils.equals(password, user.getPassword())) {
                        result.setMessage("账号或密码错误！");
                        return result;
                    }
                    break;
                case ANDROID:
                    break;
                default:
                    break;
            }
            LoginType loginType = LoginType.get(loginTypeCode);
            if (loginType == null) {
                result.setMessage("登录方式码非法！");
                logger.error("登录方式码非法！loginTypeCode:" + loginTypeCode);
                return result;
            }
            switch (loginType) {
                case NORMAL:
                    break;
                case GZTY://取配置文件中的密码进行验证
                    if (!StringUtils.equals(password, PropertiesReader.value("gzty.android" +
                            ".password"))) {
                        result.setMessage("账号或密码错误！");
                        return result;
                    }
                    break;
            }

            result = new ResultSuccess("登陆成功！");
            result.set(USER_CODE, user);
            return result;
        } catch (Exception e) {
            logger.error("登录", e);
            result.setMessage("系统异常！");
            return result;
        }
    }

}
