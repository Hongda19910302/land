package net.deniro.land.module.system.action;

import net.deniro.land.common.entity.QueryParam;
import net.deniro.land.module.system.entity.RoleQueryParam;
import net.deniro.land.module.system.service.ModuleService;
import net.deniro.land.module.system.service.RoleService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.InvocationTargetException;

/**
 * 通用
 *
 * @author deniro
 *         2015/10/20
 */
@Controller
@RequestMapping("/common")
@Deprecated
public class CommonController {

    static Logger logger = Logger.getLogger(CommonController.class);

    @Autowired
    private RoleService roleService;

    @Autowired
    private ModuleService moduleService;

    /**
     * 跳转至管理主界面
     *
     * @return
     */
    @RequestMapping(value = "/index")
    public String index(QueryParam queryParam, ModelMap mm) {
        //传递查询参数
        mm.addAttribute("queryParam", queryParam);

        //模块配置
        mm.addAttribute("moduleSearchCfg", moduleService.findByModuleId(queryParam.getModuleId()));

        /**
         * 分模块处理
         */
        try {
            switch (queryParam.getModuleId()) {
                case 23://角色管理
                    //分页查询角色
                    RoleQueryParam param = new RoleQueryParam();
                    BeanUtils.copyProperties(param, queryParam);
                    mm.addAttribute("page", roleService.findPage(param));
                    break;
            }
        } catch (IllegalAccessException e) {
            logger.error("跳转至管理主界面", e);
        } catch (InvocationTargetException e) {
            logger.error("跳转至管理主界面", e);
        }

        return "common/index";
    }
}
