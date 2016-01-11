package net.deniro.land.module.icase.action;

import net.deniro.land.module.component.service.CompFormService;
import net.deniro.land.module.icase.entity.TVariableField;
import net.deniro.land.module.icase.entity.VariableFieldQueryParam;
import net.deniro.land.module.icase.service.VariableFieldService;
import net.deniro.land.module.system.action.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

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
     * 跳转至新增或编辑页面
     *
     * @param componentId
     * @param dataTypeId  ID
     * @param mm
     * @param session
     * @return
     */
    @RequestMapping(value = "/addOrEditIndex")
    public String addOrEditIndex(Integer componentId, Integer dataTypeId, ModelMap mm,
                                 HttpSession session) {

        if (dataTypeId != null) {//编辑
            TVariableField entity = variableFieldService.findById(dataTypeId);
            mm.addAttribute(CompFormService.OBJECT_NAME, entity);
        }

        form(componentId, mm, session);
        return COMPONENT_FORM_URL;
    }

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
