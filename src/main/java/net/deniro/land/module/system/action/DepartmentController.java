package net.deniro.land.module.system.action;

import net.deniro.land.module.system.entity.DepartmentQueryParam;
import net.deniro.land.module.system.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

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
     * 跳转至部门管理主界面
     * @return
     */
    @RequestMapping(value = "/index2")
    public String index2(){
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
