package net.deniro.land.module.icase.action;

import net.deniro.land.module.icase.entity.VariableFieldQueryParam;
import net.deniro.land.module.icase.service.VariableFieldService;
import net.deniro.land.module.system.action.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 案件字段
 *
 * @author deniro
 *         2015/11/4
 */
@Controller
@RequestMapping("/variableField")
public class VariableFieldController extends BaseController {
    @Autowired
    private VariableFieldService variableFieldService;

    /**
     * 跳转至案件字段主界面
     *
     * @return
     */
    @RequestMapping(value = "/index")
    public String index(VariableFieldQueryParam queryParam, ModelMap mm) {
        super.pageSearch(mm, variableFieldService.findPage(queryParam), queryParam, "variableField/index");
        return COMPONENT_PAGE_SEARCH_URL;
    }

}
