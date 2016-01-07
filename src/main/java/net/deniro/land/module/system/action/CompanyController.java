package net.deniro.land.module.system.action;

import net.deniro.land.module.system.entity.CompanyQueryParam;
import net.deniro.land.module.system.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * 单位
 *
 * @author deniro
 *         2015/11/4
 */
@Controller
@RequestMapping("/company")
public class CompanyController extends BaseController {

    @Autowired
    private CompanyService companyService;

    /**
     * 跳转至新增或编辑页面
     * @param componentId
     * @param mm
     * @param session
     * @return
     */
    @RequestMapping(value = "/addOrEditIndex")
    public String addOrEditIndex(Integer componentId, ModelMap mm, HttpSession session) {

        form(componentId, mm, session);
        return COMPONENT_FORM_URL;
    }

    /**
     * 跳转至单位管理主界面
     *
     * @return
     */
    @RequestMapping(value = "/index")
    public String index(CompanyQueryParam queryParam, ModelMap mm) {
        super.pageSearch(mm, companyService.findPage(queryParam), queryParam, "company/index");
        return COMPONENT_PAGE_SEARCH_URL;
    }
}
