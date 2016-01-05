package net.deniro.land.module.system.action;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * 行政区域
 *
 * @author deniro
 *         2016/1/5
 */
@Controller
@RequestMapping("/region")
public class RegionController extends BaseController {

    /**
     * 跳转到新增或编辑行政区域页
     *
     * @return
     */
    @RequestMapping("/addOrEditIndex")
    public String addOrEditIndex(Integer componentId, ModelMap mm,
                                 HttpSession
                                         session) {
        form(componentId, mm, session);
        return COMPONENT_FORM_URL;
    }

    /**
     * 跳转到行政区域管理页
     *
     * @return
     */
    @RequestMapping("/index")
    public String index() {
        return "system/region/index";
    }
}
