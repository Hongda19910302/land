package net.deniro.land.module.system.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 角色
 *
 * @author deniro
 *         2015/10/13
 */
@Controller
@RequestMapping("/role")
public class RoleController {

    /**
     * 跳转至角色管理主界面
     *
     * @return
     */
    @RequestMapping(value = "/index")
    public String index() {
        return "/role/index";
    }
}
