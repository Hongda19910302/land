package net.deniro.land.module.system.action;

import net.deniro.land.common.dao.Page;
import net.deniro.land.common.dwz.AjaxResponse;
import net.deniro.land.common.dwz.AjaxResponseError;
import net.deniro.land.module.component.service.CompFormService;
import net.deniro.land.module.system.entity.Role;
import net.deniro.land.module.system.entity.RoleQueryParam;
import net.deniro.land.module.system.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static net.deniro.land.common.dwz.AjaxResponseSuccess.MENU_TAB_PREFIX;

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
     * 【角色管理】页签
     */
    public static final String ROLE_ID = MENU_TAB_PREFIX + "23";

    /**
     * 新增或编辑
     *
     * @param role
     * @return
     */
    @RequestMapping(value = "/addOrEdit")
    @ResponseBody
    public AjaxResponse addOrEdit(Role role) {

        if (role == null) {
            return new AjaxResponseError("操作失败");
        }

        List<String> navTabIds = new ArrayList<String>();
        navTabIds.add(ROLE_ID);

        if (role.getBackRoleId() == null || role.getBackRoleId() == 0) {//新增
            boolean isOk = roleService.add(role);
            if (isOk) {
                return getAjaxSuccessAndCloseCurrentDialog("操作成功", navTabIds);
            } else
                return new AjaxResponseError("操作失败");
        } else {//编辑
            boolean isOk = roleService.update(role);
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
     * @param roleId      角色ID
     * @param mm
     * @param session
     * @return
     */
    @RequestMapping(value = "/addOrEditIndex")
    public String addOrEditIndex(Integer componentId, Integer roleId, ModelMap mm,
                                 HttpSession session) {

        if (roleId != null) {//编辑
            mm.addAttribute(CompFormService.OBJECT_NAME, roleService.findById(roleId));
        }

        form(componentId, mm, session);
        return COMPONENT_FORM_URL;
    }

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
