package net.deniro.land.module.component;

import net.deniro.land.common.service.Constants;
import net.deniro.land.common.spring.mvc.ResourcePathExposer;
import net.deniro.land.module.component.entity.CompForm;
import net.deniro.land.module.component.entity.CompanyTreeNode;
import net.deniro.land.module.component.entity.TreeQueryParam;
import net.deniro.land.module.component.service.CompFormService;
import net.deniro.land.module.system.entity.Department;
import net.deniro.land.module.system.entity.TRegion;
import net.deniro.land.module.system.entity.User;
import net.deniro.land.module.system.service.CompanyService;
import net.deniro.land.module.system.service.DepartmentService;
import net.deniro.land.module.system.service.RegionService;
import net.deniro.land.module.system.service.UserService;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
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

    @Autowired
    private RegionService regionService;

    @Autowired
    private CompFormService compFormService;

    /**
     * 跳转至地理坐标选择组件
     *
     * @param mode      模式
     * @param formId    表单ID
     * @param fieldName 表单字段名称 用于带回选择的字段数据
     * @param lng       经度
     * @param lat       纬度
     * @param mm
     * @return
     */
    @RequestMapping(value = "/lookupGeographicCoordinates")
    public String lookupGeographicCoordinates(String mode, String formId, String
            fieldName, String lng, String lat,
                                              ModelMap
                                                      mm) {
        mm.addAttribute("id", RandomUtils.nextInt(1, 100));//加入ID，避免同一组件冲突
        mm.addAttribute("formId", formId);
        mm.addAttribute("fieldName", fieldName);

        if (StringUtils.equals(mode, Constants.DISPLAY_MODE)) {
            mm.addAttribute("mode", mode);
        }
        if (StringUtils.isNotBlank(lng)) {
            mm.addAttribute("lng", lng);
        }
        if (StringUtils.isNotBlank(lat)) {
            mm.addAttribute("lat", lat);
        }

        return "/component/lookupGeographicCoordinates";
    }

    /**
     * 跳转至表单组件
     *
     * @param componentId
     * @param mm
     * @param session
     * @return
     */
    @Deprecated
    @RequestMapping(value = "/form")
    public String form(Integer componentId, ModelMap mm, HttpSession session) {
        User user = (User) session.getAttribute(UserService.USER_CODE);

        //获取当前用户的区域信息
        List<TRegion> regions = regionService.findByCompanyIdForTree(user.getCompanyId());
        if (regions != null && !regions.isEmpty()) {
            mm.addAttribute("currentUserRegion", regions.get(0));
        }

        CompForm compForm = compFormService.findById(componentId, user.getCompanyId());
        mm.addAttribute("compForm", compForm);
        mm.addAttribute("componentId", componentId);

        return "/component/form";
    }


    /**
     * 跳转至区域选择组件
     *
     * @param componentId 组件ID
     * @return
     */
    @RequestMapping(value = "/lookupRegion")
    public String lookupRegion(String componentId, ModelMap mm) {
        mm.addAttribute("componentId", componentId);
        return "/component/lookup-region";
    }

    /**
     * 递归查询区域树节点
     *
     * @param regionId 区域ID
     * @return
     */
    @RequestMapping(value = "/recursiveFindRegionTreeNode")
    @ResponseBody
    public List<TRegion> findRegionTreeNode(String
                                                    regionId, Integer companyId) {
        List<TRegion> data = new ArrayList<TRegion>();
        if (StringUtils.isBlank(regionId)) {//第一次加载
            data.addAll(regionService.findByCompanyIdForTree(companyId));
        } else {
            data.addAll(regionService.findChildrenByRegionIdForTree(NumberUtils.toInt
                    (regionId)));
        }

        return data;
    }

    /**
     * 查询所有区域树节点
     *
     * @param regionId 区域ID
     * @return
     */
    @RequestMapping(value = "/findAllRegionTreeNode")
    @ResponseBody
    public List<TRegion> findAllRegionTreeNode(String
                                                       regionId) {

        List<TRegion> data = new ArrayList<TRegion>();


        if (StringUtils.isBlank(regionId)) {//第一次加载
            data.addAll(regionService.findAllTop());
        } else {
            data.addAll(regionService.findChildrenByRegionIdForTree(NumberUtils.toInt
                    (regionId)));
        }

        return data;
    }


    /**
     * 查询区域树节点
     *
     * @param regionId 区域ID
     * @return
     */
    @RequestMapping(value = "/findRegionTreeNode")
    @ResponseBody
    public List<TRegion> findRegionTreeNode(String
                                                    regionId, HttpSession session) {

        List<TRegion> data = new ArrayList<TRegion>();


        if (StringUtils.isBlank(regionId)) {//第一次加载，获取当前登录用户归属公司的所在区域
            User user = (User) session.getAttribute(UserService.USER_CODE);
            if (user == null) {
                return data;
            }
            data.addAll(regionService.findByCompanyIdForTree(user.getCompanyId()));
        } else {
            data.addAll(regionService.findChildrenByRegionIdForTree(NumberUtils.toInt
                    (regionId)));
        }

        return data;
    }

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
     * @param pageSearchComponentId 分页查询组件ID
     * @return
     */
    @RequestMapping(value = "/lookupCompanyDepartment")
    public String lookupCompanyDepartment(Integer pageSearchComponentId, ModelMap mm) {
        mm.addAttribute("pageSearchComponentId", pageSearchComponentId);
        return "/component/lookup-company-department";
    }

    /**
     * 跳转至单位、区域选择组件
     *
     * @param pageSearchComponentId 分页查询组件ID
     * @return
     */
    @RequestMapping(value = "/lookupCompanyRegion")
    public String lookupCompanyRegion(Integer pageSearchComponentId, ModelMap mm) {
        mm.addAttribute("pageSearchComponentId", pageSearchComponentId);
        return "/component/lookup-company-region";
    }


}
