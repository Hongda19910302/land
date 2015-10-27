package net.deniro.land.module.system.action;

import net.deniro.land.common.dao.Page;
import net.deniro.land.module.system.entity.RoleQueryParam;
import net.deniro.land.module.system.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 角色
 *
 * @author deniro
 *         2015/10/13
 */
@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;

    /**
     * 分页查询
     *
     * @return
     */
    @RequestMapping(value = "/findPage")
    @ResponseBody
    @Deprecated
    public Page findPage(RoleQueryParam queryParam) {
        return roleService.findPage(queryParam);
    }

    /**
     * 跳转至角色管理主界面
     *
     * @return
     */
    @RequestMapping(value = "/index")
    public String index(RoleQueryParam queryParam, ModelMap mm) {
        super.pageSearch(mm, roleService.findPage(queryParam), queryParam, "role/index");
        return COMPONENT_PAGE_SEARCH_URL;
    }


}
