package net.deniro.land.module.system.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 行政区域
 *
 * @author deniro
 *         2016/1/5
 */
@Controller
@RequestMapping("/region")
public class RegionController {

    /**
     * 跳转到行政区域管理页
     *
     * @return
     */
    @RequestMapping("/index")
    public String regionIndex() {
        return "system/region/index";
    }
}
