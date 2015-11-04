package net.deniro.land.module.icase.action;

import net.deniro.land.module.icase.entity.DataTypeQueryParam;
import net.deniro.land.module.icase.service.DataTypeService;
import net.deniro.land.module.system.action.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 案件下拉项
 *
 * @author deniro
 *         2015/11/4
 */
@Controller
@RequestMapping("/dataType")
public class DataTypeController extends BaseController {
    @Autowired
    private DataTypeService dataTypeService;

    /**
     * 跳转至案件字段主界面
     *
     * @return
     */
    @RequestMapping(value = "/index")
    public String index(DataTypeQueryParam queryParam, ModelMap mm) {
        super.pageSearch(mm, dataTypeService.findPage(queryParam), queryParam, "dataType/index");
        return COMPONENT_PAGE_SEARCH_URL;
    }
}
