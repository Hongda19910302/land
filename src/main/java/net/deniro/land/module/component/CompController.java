package net.deniro.land.module.component;

import net.deniro.land.common.spring.mvc.ResourcePathExposer;
import net.deniro.land.module.component.entity.CompanyTreeNode;
import net.deniro.land.module.component.entity.TreeQueryParam;
import net.deniro.land.module.system.entity.Company;
import net.deniro.land.module.system.entity.Department;
import net.deniro.land.module.system.service.CompanyService;
import net.deniro.land.module.system.service.DepartmentService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private CompanyService companyService;

    /**
     * 查询所有单位树节点
     *
     * @return
     */
    @RequestMapping(value = "/findAllCompany")
    @ResponseBody
    public List<CompanyTreeNode> findAllCompany() {
        List<CompanyTreeNode> tree = new ArrayList<CompanyTreeNode>();

        Map<String, String> companys = companyService.findAllInSelect();
        for (String s : companys.keySet()) {
            CompanyTreeNode companyTreeNode = new CompanyTreeNode();
            companyTreeNode.setCompanyId(s);
            companyTreeNode.setName(companys.get(s));
            companyTreeNode.setIcon(ResourcePathExposer.getResourceRoot()
                    + "/dwz/themes/default/images/dialog/award_star_gold_2.png");
            tree.add(companyTreeNode);
        }

        return tree;
    }


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
     * 跳转至单位、部门选择组件
     *
     * @return
     */
    @RequestMapping(value = "/lookupCompanyDepartment")
    public String lookupDepartment() {
        return "/component/lookup-company-department";
    }
}
