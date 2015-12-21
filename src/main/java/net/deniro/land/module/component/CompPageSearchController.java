package net.deniro.land.module.component;

import net.deniro.land.module.component.entity.CompPageSearchQueryParam;
import net.deniro.land.module.component.service.CompPageSearchService;
import net.deniro.land.module.system.action.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 分页查询组件
 *
 * @author deniro
 *         2015/12/21
 */
@Controller
@RequestMapping("/compPageSearch")
public class CompPageSearchController extends BaseController {

    @Autowired
    private CompPageSearchService compPageSearchService;

    /**
     * 跳转至 分页组件管理主界面
     *
     * @return
     */
    @RequestMapping(value = "/index")
    public String index(CompPageSearchQueryParam queryParam, ModelMap mm) {
        super.pageSearch(mm, compPageSearchService.findPage(queryParam), queryParam,
                "compPageSearch/index");
        return COMPONENT_PAGE_SEARCH_URL;
    }

}
