package net.deniro.land.module.system.action;

import net.deniro.land.common.dwz.AjaxResponse;
import net.deniro.land.common.dwz.AjaxResponseError;
import net.deniro.land.module.system.entity.Company;
import net.deniro.land.module.system.entity.CompanyQueryParam;
import net.deniro.land.module.system.service.CompanyService;
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
     * 【单位管理】页签
     */
    public static final String COMPANY_ID = MENU_TAB_PREFIX + "24";

    /**
     * 新增或编辑
     *
     * @param company
     * @return
     */
    @RequestMapping(value = "/addOrEdit")
    @ResponseBody
    public AjaxResponse addOrEdit(Company company) {

        if (company == null) {
            return new AjaxResponseError("操作失败");
        }

        if (company.getCompanyId() == null || company.getCompanyId() == 0) {//新增
            boolean isOk = companyService.add(company);
            if (isOk) {
                List<String> navTabIds = new ArrayList<String>();
                navTabIds.add(COMPANY_ID);

                return getAjaxSuccessAndCloseCurrentDialog("操作成功", navTabIds);
            } else
                return new AjaxResponseError("操作失败");
        } else {//编辑
            return new AjaxResponseError("操作失败");
        }
    }

    /**
     * 跳转至新增或编辑页面
     *
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
