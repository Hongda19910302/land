package net.deniro.land.module.system.action;

import net.deniro.land.common.dwz.AjaxResponse;
import net.deniro.land.common.dwz.AjaxResponseError;
import net.deniro.land.module.component.service.CompFormService;
import net.deniro.land.module.system.entity.Department;
import net.deniro.land.module.system.entity.Department.OperateType;
import net.deniro.land.module.system.entity.DepartmentQueryParam;
import net.deniro.land.module.system.service.DepartmentService;
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
 * 部门
 *
 * @author deniro
 *         2015/11/4
 */
@Controller
@RequestMapping("/department")
public class DepartmentController extends BaseController {

    @Autowired
    private DepartmentService departmentService;

    /**
     * 【部门管理】页签
     */
    public static final String DEPARTMENT_ID = MENU_TAB_PREFIX + "25";

    /**
     * 假删除
     *
     * @param currentDepartmentId
     * @return
     */
    @RequestMapping("/fakeDelete")
    @ResponseBody
    public AjaxResponse delete(Integer currentDepartmentId) {
        if (currentDepartmentId == null) {
            return new AjaxResponseError("操作失败");
        }

        Department department = departmentService.findById(currentDepartmentId);
        if (department.getDepartmentId() != null) {
            department.setStatus(2);
            boolean isOk = departmentService.update(department);
            if (isOk) {
                List<String> navTabIds = new ArrayList<String>();
                navTabIds.add(DEPARTMENT_ID);
                return getAjaxSuccess("删除成功", navTabIds);
            } else {
                return new AjaxResponseError("操作失败");
            }
        } else
            return new AjaxResponseError("操作失败");
    }

    /**
     * 新增或更新
     *
     * @param department
     * @return
     */
    @RequestMapping("/addOrEdit")
    @ResponseBody
    public AjaxResponse addOrEdit(Department department) {
        if (department == null || StringUtils.isBlank(department.getOperateType())) {
            return new AjaxResponseError("操作失败");
        }

        //解析操作类型
        OperateType operateType = null;
        try {
            operateType = OperateType.valueOf(department
                    .getOperateType());
        } catch (IllegalArgumentException e) {
            logger.error("操作类型无法解析");
            return new AjaxResponseError("操作失败");
        }

        List<String> navTabIds = new ArrayList<String>();
        navTabIds.add(DEPARTMENT_ID);

        switch (operateType) {
            case ADD_TOP://新增顶级部门
                if (department.getCurrentCompanyId() == -1) {
                    return new AjaxResponseError("操作失败");
                } else {
                    department.setCompanyId(department.getCurrentCompanyId());
                    return add(department, navTabIds);
                }
            case ADD_BROTHER://新增同级部门
                if (department.getCurrentCompanyId() == -1) {
                    return new AjaxResponseError("操作失败");
                } else {
                    department.setCompanyId(department.getCurrentCompanyId());
                    if (department.getCurrentDepartmentParentId() == -1) {//新增顶级部门
                    } else {//其他
                        department.setParentId(department.getCurrentDepartmentParentId());
                    }
                    return add(department, navTabIds);
                }
            case ADD_CHILD://新增子级部门
                if (department.getCurrentCompanyId() == -1) {
                    return new AjaxResponseError("操作失败");
                } else {
                    department.setCompanyId(department.getCurrentCompanyId());
                    if (department.getCurrentDepartmentId() == -1) {
                        return new AjaxResponseError("操作失败");
                    } else {
                        department.setParentId(department.getCurrentDepartmentId());
                        return add(department, navTabIds);
                    }
                }
            case EDIT://编辑
                Department newDepartment = departmentService.findById(department
                        .getCurrentDepartmentId());
                newDepartment.setName(department.getName());
                newDepartment.setStatus(department.getStatus());
                boolean isOk = departmentService.update(newDepartment);
                if (isOk) {
                    return getAjaxSuccessAndCloseCurrentDialog("操作成功", navTabIds);
                } else
                    return new AjaxResponseError("操作失败");
            default:
                return new AjaxResponseError("操作失败");
        }
    }

    /**
     * 新增部门
     *
     * @param department
     * @param navTabIds
     * @return
     */
    private AjaxResponse add(Department department, List<String> navTabIds) {
        boolean isOk = departmentService.add(department);
        if (isOk) {
            return getAjaxSuccessAndCloseCurrentDialog("操作成功", navTabIds);
        } else
            return new AjaxResponseError("操作失败");
    }


    /**
     * 跳转至部门新建或编辑页
     *
     * @param componentId
     * @param currentCompanyId          当前选择的单位ID
     * @param currentDepartmentId       当前选择的部门ID
     * @param currentDepartmentParentId 当前选择的部门的父部门ID
     * @param operateType
     * @param mm
     * @param session
     * @return
     */
    @RequestMapping(value = "/addOrEditIndex")
    public String addOrEditIndex(Integer componentId, Integer currentCompanyId, Integer
            currentDepartmentId, Integer currentDepartmentParentId,
                                 String
                                         operateType,
                                 ModelMap mm,
                                 HttpSession
                                         session) {

        Department department = new Department();

        //解析操作类型
        OperateType type = null;
        try {
            type = OperateType.valueOf(operateType);
        } catch (IllegalArgumentException e) {
            logger.error("操作类型无法解析");
        }
        if (type != null) {
            switch (type) {
                case EDIT://编辑
                    department = departmentService.findById(currentDepartmentId);
                    break;
            }
        }


        department.setOperateType(operateType);
        department.setCurrentCompanyId(currentCompanyId);
        department.setCurrentDepartmentId(currentDepartmentId);
        department.setCurrentDepartmentParentId(currentDepartmentParentId);

        mm.addAttribute(CompFormService.OBJECT_NAME, department);

        form(componentId, mm, session);
        return COMPONENT_FORM_URL;
    }

    /**
     * 跳转至部门管理主界面
     *
     * @return
     */
    @RequestMapping(value = "/index2")
    public String index2() {
        return "system/department/index";
    }


    /**
     * 跳转至部门管理主界面
     *
     * @return
     */
    @Deprecated
    @RequestMapping(value = "/index")
    public String index(DepartmentQueryParam queryParam, ModelMap mm) {
        super.pageSearch(mm, departmentService.findPage(queryParam), queryParam, "department/index");
        return COMPONENT_PAGE_SEARCH_URL;
    }
}
