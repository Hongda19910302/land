package net.deniro.land.module.system.action;

import net.deniro.land.common.dwz.AjaxResponse;
import net.deniro.land.common.dwz.AjaxResponseError;
import net.deniro.land.module.component.service.CompFormService;
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
     * 假删除（改为删除状态）
     *
     * @param companyId 单位ID
     * @return
     */
    @RequestMapping(value = "/fakeDelete")
    @ResponseBody
    public AjaxResponse fakeDelete(Integer companyId) {
        if (companyId == null) {
            return new AjaxResponseError("操作失败");
        }

        Company company = companyService.findById(companyId);
        company.setStatus(2);//删除状态
        boolean isOk = companyService.update(company);
        if (isOk) {
            List<String> navTabIds = new ArrayList<String>();
            navTabIds.add(COMPANY_ID);
            return getAjaxSuccessAndCloseCurrentDialog("操作成功", navTabIds);
        } else
            return new AjaxResponseError("操作失败");
    }

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

        List<String> navTabIds = new ArrayList<String>();
        navTabIds.add(COMPANY_ID);

        if (company.getCompanyId() == null || company.getCompanyId() == 0) {//新增
            boolean isOk = companyService.add(company);
            if (isOk) {
                return getAjaxSuccessAndCloseCurrentDialog("操作成功", navTabIds);
            } else
                return new AjaxResponseError("操作失败");
        } else {//编辑
            boolean isOk = companyService.update(company);
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
     * @param companyId   单位ID
     * @param mm
     * @param session
     * @return
     */
    @RequestMapping(value = "/addOrEditIndex")
    public String addOrEditIndex(Integer componentId, Integer companyId, ModelMap mm,
                                 HttpSession session) {

        if (companyId != null) {//编辑
            Company company = companyService.findById(companyId);
            mm.addAttribute(CompFormService.OBJECT_NAME, company);
        }

        mm.addAttribute("isDialog", true);

        form(componentId, mm, session);
        return COMPONENT_FORM_URL;
    }

    /**
     * 跳转至单位管理主界面
     *
     * @param queryParam
     * @param mm
     * @param session
     * @return
     */
    @RequestMapping(value = "/index")
    public String index(CompanyQueryParam queryParam, ModelMap mm, HttpSession session) {
        //单元管理，如果不是超级管理员，就加入当前账号所属企业作为基础查询条件
        if (!isSuperAdmin(session)) {
            queryParam.setCompanyId(String.valueOf(getCurrentUser(session).getCompanyId()));
        }


        super.pageSearch(mm, companyService.findPage(queryParam), queryParam,
                "company/index",session);
        return COMPONENT_PAGE_SEARCH_URL;
    }
}
