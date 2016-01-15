package net.deniro.land.module.system.action;

import net.deniro.land.common.dwz.AjaxResponse;
import net.deniro.land.common.dwz.AjaxResponseError;
import net.deniro.land.common.service.dwz.Result;
import net.deniro.land.common.service.dwz.ResultError;
import net.deniro.land.common.utils.Md5Utils;
import net.deniro.land.module.component.service.CompFormService;
import net.deniro.land.module.system.entity.Company;
import net.deniro.land.module.system.entity.Department;
import net.deniro.land.module.system.entity.User;
import net.deniro.land.module.system.entity.UserQueryParam;
import net.deniro.land.module.system.service.CompanyService;
import net.deniro.land.module.system.service.DepartmentService;
import net.deniro.land.module.system.service.UserService;
import nl.captcha.Captcha;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static net.deniro.land.common.dwz.AjaxResponseSuccess.MENU_TAB_PREFIX;

/**
 * 用户
 *
 * @author deniro
 *         2015/10/10
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

    static Logger logger = Logger.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private DepartmentService departmentService;

    /**
     * 【用户管理】页签
     */
    public static final String USER_ID = MENU_TAB_PREFIX + "22";

    /**
     * 默认密码，加密后
     */
    public static final String DEFAULT_PASSWORD = "49ba59abbe56e057";

    /**
     * 修改状态
     *
     * @param userId 用户ID
     * @param status 状态
     * @return
     */
    @RequestMapping(value = "/changeStatus")
    @ResponseBody
    public AjaxResponse changeStatus(Integer userId, Integer status) {
        if (userId == null || status == null) {
            return new AjaxResponseError("操作失败");
        }

        User entity = userService.findById(userId);
        entity.setStatus(status);//设置状态值
        boolean isOk = userService.update(entity);
        if (isOk) {
            List<String> navTabIds = new ArrayList<String>();
            navTabIds.add(USER_ID);
            return getAjaxSuccessAndCloseCurrentDialog("操作成功", navTabIds);
        } else
            return new AjaxResponseError("操作失败");
    }

    /**
     * 新增或编辑
     *
     * @param entity
     * @return
     */
    @RequestMapping(value = "/addOrEdit")
    @ResponseBody
    public AjaxResponse addOrEdit(User entity) {

        if (entity == null) {
            return new AjaxResponseError("操作失败");
        }

        List<String> navTabIds = new ArrayList<String>();
        navTabIds.add(USER_ID);

        if (entity.getUserId() == null || entity.getUserId() == 0) {//新增

            if (userService.isExisted(entity.getAccount())) {
                return new AjaxResponseError("该账号已存在");
            }

            entity.setStatus(User.Status.NORMAL.code());
            entity.setPassword(DEFAULT_PASSWORD);

            boolean isOk = userService.add(entity);
            if (isOk) {
                return getAjaxSuccessAndCloseCurrentDialog("操作成功", navTabIds);
            } else
                return new AjaxResponseError("操作失败");
        } else {//编辑
            User newUser = userService.findById(entity.getUserId());
            newUser.setName(entity.getName());
            newUser.setTel(entity.getTel());
            newUser.setRoleId(entity.getRoleId());
            newUser.setSex(entity.getSex());

            if (entity.getCompanyId() != null)
                newUser.setCompanyId(entity.getCompanyId());

            if (entity.getDepartmentId() != null)
                newUser.setDepartmentId(entity.getDepartmentId());


            boolean isOk = userService.update(newUser);
            if (isOk) {
                return getAjaxSuccessAndCloseCurrentDialog("操作成功", navTabIds);
            } else
                return new AjaxResponseError("操作失败");
        }
    }

    /**
     * 跳转至新增或编辑页面
     *
     * @param componentId
     * @param userId      用户ID
     * @param mm
     * @param session
     * @return
     */
    @RequestMapping(value = "/addOrEditIndex")
    public String addOrEditIndex(Integer componentId, Integer userId, ModelMap mm,
                                 HttpSession session) {

        if (userId != null) {//编辑
            User user = userService.findById(userId);

            Integer companyId = user.getCompanyId();
            Company company = companyService.findById(companyId);

            Integer departmentId = user.getDepartmentId();
            Department department = departmentService.findById(departmentId);

            user.setCompanyDepartmentText(company.getCompanyName() + "/" + department.getName());
            mm.addAttribute(CompFormService.OBJECT_NAME, user);
        }

        form(componentId, mm, session);
        return COMPONENT_FORM_URL;
    }


    /**
     * 修改密码
     *
     * @param oldPwd  旧密码
     * @param newPwd  新密码
     * @param session
     * @return
     */
    @RequestMapping(value = "/modifyPwd")
    @ResponseBody
    public AjaxResponse modifyPwd(String oldPwd, String newPwd, HttpSession
            session) {
        if (StringUtils.isBlank(oldPwd) || StringUtils.isBlank(newPwd)) {
            return new AjaxResponseError("密码修改失败");
        }

        User user = getCurrentUser(session);
        if (!StringUtils.equals(user.getPassword(), Md5Utils.encryptIn16(oldPwd))) {
            return new AjaxResponseError("旧密码不正确");
        }


        boolean isOk = userService.updatePwd(user.getUserId(), Md5Utils.encryptIn16(newPwd));
        if (isOk) {
            return getAjaxSuccessAndCloseCurrent("密码修改成功", null);
        } else {
            return new AjaxResponseError("密码修改失败");
        }
    }

    /**
     * 跳转到密码修改页
     *
     * @param componentId
     * @param mm
     * @param session
     * @return
     */
    @RequestMapping(value = "/modifyPwdIndex")
    public String modifyPwdIndex(Integer componentId, ModelMap mm,
                                 HttpSession
                                         session) {
        form(componentId, mm, session);
        return COMPONENT_FORM_URL;
    }

    /**
     * 跳转至账户管理主界面
     *
     * @return
     */
    @RequestMapping(value = "/index")
    public String index(UserQueryParam queryParam, ModelMap mm) {
        super.pageSearch(mm, userService.findPage(queryParam), queryParam, "user/index");
        return COMPONENT_PAGE_SEARCH_URL;
    }

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

            Result result = userService.login(account, password, loginSourceCode,
                    User.LoginType.NORMAL.code());

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
