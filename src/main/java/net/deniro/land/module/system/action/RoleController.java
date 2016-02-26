package net.deniro.land.module.system.action;

import net.deniro.land.common.dao.Page;
import net.deniro.land.common.dwz.AjaxResponse;
import net.deniro.land.common.dwz.AjaxResponseError;
import net.deniro.land.module.component.service.CompFormService;
import net.deniro.land.module.icase.entity.ModuleTreeNode;
import net.deniro.land.module.system.entity.MenuItem;
import net.deniro.land.module.system.entity.Role;
import net.deniro.land.module.system.entity.RoleQueryParam;
import net.deniro.land.module.system.service.MenuService;
import net.deniro.land.module.system.service.RoleService;
import org.apache.commons.lang3.StringUtils;
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

    @Autowired
    private MenuService menuService;

    /**
     * 【角色管理】页签
     */
    public static final String ROLE_ID = MENU_TAB_PREFIX + "23";

    /**
     * 查询所有模块树节点
     *
     * @param roleId 角色ID
     * @return
     */
    @RequestMapping(value = "/findAllModuleNodes2")
    @ResponseBody
    public List<ModuleTreeNode> findAllModuleNodes2(Integer roleId) {
        return menuService.findAllNodes(roleId);
    }

    /**
     * 查询所有模块树节点
     *
     * @param backPrivilegeId 模块ID
     * @param roleId          角色ID
     * @return
     */
    @RequestMapping(value = "/findAllModuleNodes")
    @ResponseBody
    @Deprecated
    public List<MenuItem> findAllModuleNodes(Integer backPrivilegeId, Integer roleId) {
        List<MenuItem> menuItems;
        if (backPrivilegeId == null) {//首次加载
            menuItems = menuService.findAllTopInDisplay();
        } else {//加载子模块
            menuItems = menuService.findChildrenInDisplay(backPrivilegeId, roleId);
        }

        return menuItems;
    }

    /**
     * 设置权限
     *
     * @param roleId    角色ID
     * @param moduleIds 模块ID列表
     * @return
     */
    @RequestMapping(value = "/setAuthority")
    @ResponseBody
    public AjaxResponse setAuthority(Integer roleId, String moduleIds) {
        if (roleId == null) {
            return new AjaxResponseError("操作失败");
        }

        boolean isOk = roleService.setAuthority(roleId, moduleIds);
        if (isOk) {
            return getAjaxSuccessAndCloseCurrentDialog("设置成功", null);
        } else
            return new AjaxResponseError("操作失败");
    }

    /**
     * 跳转到设置权限页面
     *
     * @param roleId
     * @return
     */
    @RequestMapping(value = "/setAuthorityIndex")
    public String setAuthorityIndex(Integer roleId, ModelMap mm) {
        mm.addAttribute("roleId", roleId);
        return "system/role/setAuthorityIndex2";
    }

    /**
     * 修改状态
     *
     * @param roleId 角色ID
     * @param status 状态
     * @return
     */
    @RequestMapping(value = "/changeStatus")
    @ResponseBody
    public AjaxResponse changeStatus(Integer roleId, Integer status) {
        if (roleId == null || status == null) {
            return new AjaxResponseError("操作失败");
        }

        Role entity = roleService.findById(roleId);
        entity.setStatus(status);//设置状态值
        boolean isOk = roleService.update(entity);
        if (isOk) {
            List<String> navTabIds = new ArrayList<String>();
            navTabIds.add(ROLE_ID);
            return getAjaxSuccessAndCloseCurrentDialog("操作成功", navTabIds);
        } else
            return new AjaxResponseError("操作失败");
    }

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
     * @param queryParam
     * @param mm
     * @param session
     * @return
     */
    @RequestMapping(value = "/index")
    public String index(RoleQueryParam queryParam, ModelMap mm, HttpSession session) {
        //角色管理，如果不是超级管理员，就加入当前账号所属企业作为基础查询条件
        if (!isSuperAdmin(session)) {
            queryParam.setCompanyId(getCurrentUser(session).getCompanyId());
        }

        super.pageSearch(mm, roleService.findPage(queryParam), queryParam, "role/index",session);
        return COMPONENT_PAGE_SEARCH_URL;
    }


}
