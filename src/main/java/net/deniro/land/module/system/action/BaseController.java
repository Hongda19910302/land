package net.deniro.land.module.system.action;

import net.deniro.land.common.dao.Page;
import net.deniro.land.common.entity.QueryParam;
import net.deniro.land.module.system.entity.RoleQueryParam;
import net.deniro.land.module.system.service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;

/**
 * 基础
 *
 * @author deniro
 *         2015/10/20
 */
public class BaseController {

    @Autowired
    private ModuleService moduleService;

    /**
     * 处理模块
     * @param mm
     * @param page 分页查询结果
     * @param queryParam 查询参数
     * @param actionUrl action地址
     */
    public void handleModule(ModelMap mm,Page page,QueryParam queryParam,String actionUrl){
        //分页查询
        mm.addAttribute("page", page);

        //传递查询参数
        mm.addAttribute("queryParam", queryParam);

        //模块配置
        mm.addAttribute("moduleSearchCfg", moduleService.findByModuleId(queryParam.getModuleId()));

        //传递action地址
        mm.addAttribute("actionUrl",actionUrl);
    }
}
