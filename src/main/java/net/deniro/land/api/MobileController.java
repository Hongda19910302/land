package net.deniro.land.api;

import net.deniro.land.api.entity.*;
import net.deniro.land.common.service.dwz.Result;
import net.deniro.land.module.icase.dao.CaseDao;
import net.deniro.land.module.icase.entity.TVariableField;
import net.deniro.land.module.icase.service.CaseService;
import net.deniro.land.module.icase.service.VariableFieldService;
import net.deniro.land.module.system.entity.TRegion;
import net.deniro.land.module.system.entity.User;
import net.deniro.land.module.system.service.RegionService;
import net.deniro.land.module.system.service.UserService;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 移动客户端接口
 *
 * @author deniro
 *         2015/11/5
 */
@Controller
@RequestMapping("gtweb/android")
public class MobileController {

    static Logger logger = Logger.getLogger(MobileController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private RegionService regionService;

    @Autowired
    private VariableFieldService variableFieldService;

    @Autowired
    private CaseService caseService;

    /**
     * 渲染文件的路径前缀
     */
    public static final String URL_PREFIX = "mobile/";

    /**
     * 依据创建者ID，获取巡查员列表
     *
     * @param creatorId
     * @param mm
     * @return
     */
    @RequestMapping(value = "get-recommond-inspector")
    public String findInspectorsByCreatorId(Integer creatorId, ModelMap mm) {
        ResponseResult r = null;

        try {
            List<User> list = caseService.findByCreatorId(creatorId);
            mm.addAttribute("userList", list);
            r = new SuccessResult();
        } catch (Exception e) {
            logger.error(" 依据单位ID，获取字段信息");
            r = new FailureResult();
        } finally {
            mm.addAttribute("r", r);
            return URL_PREFIX + "findInspectorsByCreatorIdResult";
        }
    }

    /**
     * 依据单位ID，获取字段信息
     *
     * @param companyId
     * @param mm
     * @return
     */
    @RequestMapping(value = "get-variable-field-list")
    public String findVariableFieldByCompanyId(Integer companyId, ModelMap mm) {
        ResponseResult r = null;

        try {
            List<TVariableField> list = variableFieldService.findByCompanyId(companyId);
            mm.addAttribute("tVariableFieldList", list);
            r = new SuccessResult();
        } catch (Exception e) {
            logger.error(" 依据单位ID，获取字段信息");
            r = new FailureResult();
        } finally {
            mm.addAttribute("r", r);
            return URL_PREFIX + "findVariableFieldByCompanyIdResult";
        }
    }

    /**
     * 依据区域ID，获取子区域列表
     *
     * @param regionId
     * @param mm
     * @return
     */
    @RequestMapping(value = "get-next-child-region")
    public String findRegionChildrenByRegionId(Integer regionId, ModelMap mm) {
        ResponseResult r = null;

        try {
            List<TRegion> regions = regionService.findChildrenByRegionId(regionId);
            mm.addAttribute("regionList", regions);
            r = new SuccessResult();
        } catch (Exception e) {
            logger.error("  依据区域ID，获取子区域列表");
            r = new FailureResult();
        } finally {
            mm.addAttribute("r", r);
            return URL_PREFIX + "findRegionChildrenByRegionIdResult";
        }
    }

    /**
     * 根据单位id获取顶级行政机构
     *
     * @param companyId
     * @return
     */
    @RequestMapping(value = "/get-top-region-by-company")
    public String findTopRegionByCompanyId(Integer companyId, ModelMap mm) {

        ResponseResult r = null;

        try {
            List<TRegion> regions = regionService.findByCompanyId(companyId);
            mm.addAttribute("regionList", regions);
            r = new SuccessResult();
        } catch (Exception e) {
            logger.error("根据单位id获取顶级行政机构");
            r = new FailureResult();
        } finally {
            mm.addAttribute("r", r);
            return URL_PREFIX + "findTopRegionByCompanyIdResult";
        }
    }

    /**
     * 登录
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "/user-login")
    public String login(LoginParam param, ModelMap mm) {

        /**
         * 尝试登录
         */
        int loginTypeCode = NumberUtils.toInt(param.getLoginType());
        int loginSourceCode = NumberUtils.toInt(param.getSource());
        Result result = userService.login(param.getAccount(), param.getPassword(),
                loginSourceCode, loginTypeCode);

        /**
         * 生成结果
         */
        ResponseResult r = new ResponseResult();
        if (result.isSuccess()) {//成功后，设置账户对象
            mm.addAttribute("user", result.get(UserService.USER_CODE));
            r.setResult(ResultCode.SUCCESS.value());
        } else {//失败
            r.setResult(ResultCode.FAILURE.value());
        }
        r.setDescribe(result.getMessage());

        mm.addAttribute("r", r);

        return URL_PREFIX + "loginResult";
    }

}
