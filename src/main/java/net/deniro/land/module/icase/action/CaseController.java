package net.deniro.land.module.icase.action;

import net.deniro.land.module.icase.entity.CaseParam;
import net.deniro.land.module.icase.service.CaseService;
import net.deniro.land.module.system.action.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 案件
 *
 * @author deniro
 *         2015/11/23
 */
@Controller
@RequestMapping("/case")
public class CaseController extends BaseController {

    @Autowired
    private CaseService caseService;

    /**
     * 跳转至【案件查询】
     *
     * @return
     */
    @RequestMapping(value = "/query")
    public String query(CaseParam queryParam, ModelMap mm) {
        super.pageSearch(mm, caseService.findPage(queryParam), queryParam, "case/query");
        return COMPONENT_PAGE_SEARCH_URL;
    }

    /**
     * 跳转至【我的案件】
     *
     * @return
     */
    @RequestMapping(value = "/my")
    public String my(CaseParam queryParam, ModelMap mm) {
        super.pageSearch(mm, caseService.findPage(queryParam), queryParam, "case/my");
        return COMPONENT_PAGE_SEARCH_URL;
    }
}
