package net.deniro.land.module;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 导航
 *
 * @author deniro
 *         2015/10/12
 */
@Controller
@RequestMapping("/")
public class NavigatorController {

    /**
     * 跳转至主界面
     *
     * @return
     */
    @RequestMapping(value = "/index")
    public String index() {
        return "/index";
    }

}
