package net.deniro.land.module.system.action;

import net.deniro.land.common.dao.Page;
import net.deniro.land.common.entity.QueryParam;
import net.deniro.land.module.system.entity.Company;
import net.deniro.land.module.system.entity.RoleQueryParam;
import net.deniro.land.module.system.service.CompanyService;
import net.deniro.land.module.system.service.ModuleService;
import net.deniro.land.module.system.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 角色
 *
 * @author deniro
 *         2015/10/13
 */
@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private ModuleService moduleService;

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

        //分页查询角色
        mm.addAttribute("page", roleService.findPage(queryParam));

        //查询所有单位
        List<Company> companies = companyService.findAll();
        Map<Integer, String> returnCompanies = new LinkedHashMap<Integer, String>();
        for (Company company : companies) {
            returnCompanies.put(company.getCompanyId(), company.getCompanyName());
        }
        mm.addAttribute("companys", returnCompanies);

        //传递查询参数
        mm.addAttribute("queryParam", queryParam);

        mm.addAttribute("moduleSearchCfg", moduleService.findByModuleId(queryParam.getModuleId()));

        return "common/index";


    }


}
