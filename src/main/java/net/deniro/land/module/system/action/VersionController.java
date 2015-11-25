package net.deniro.land.module.system.action;

import net.deniro.land.module.system.entity.VersionQueryParam;
import net.deniro.land.module.system.service.VersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 版本说明
 *
 * @author deniro
 *         2015/11/25
 */
@Controller
@RequestMapping("/version")
public class VersionController extends BaseController {

    @Autowired
    private VersionService versionService;

    /**
     * 跳转至管理主界面
     *
     * @return
     */
    @RequestMapping(value = "/index")
    public String index(VersionQueryParam queryParam, ModelMap mm) {
        super.pageSearch(mm, versionService.findPage(queryParam), queryParam, "version/index");
        return COMPONENT_PAGE_SEARCH_URL;
    }
}
