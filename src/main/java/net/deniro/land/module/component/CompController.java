package net.deniro.land.module.component;

import net.deniro.land.module.component.entity.TreeQueryParam;
import net.deniro.land.module.system.entity.Department;
import net.deniro.land.module.system.service.DepartmentService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * 前端组件
 *
 * @author deniro
 *         2015/10/28
 */
@Controller
@RequestMapping("/comp")
public class CompController {

    @Autowired
    private DepartmentService departmentService;

    /**
     * 查询部门树节点
     *
     * @param treeQueryParam 树型控件查询参数
     * @return
     */
    @RequestMapping(value = "/findDepartmentTreeNode")
    @ResponseBody
    public List<Department> findDepartmentTreeNode(TreeQueryParam treeQueryParam) {
        List<Department> departments = new ArrayList<Department>();

        if (StringUtils.isBlank(treeQueryParam.getDepartmentId())) {//第一次加载
            departments.addAll(departmentService.findTops(treeQueryParam.getCompanyId()));
        } else {
            departments.addAll(departmentService.findChilds(NumberUtils.toInt
                    (treeQueryParam.getDepartmentId())));
        }

        return departments;
    }

    /**
     * 跳转至 部门选择组件
     *
     * @return
     */
    @RequestMapping(value = "/lookupDepartment")
    public String lookupDepartment() {
        return "/component/lookup-department";
    }

}
