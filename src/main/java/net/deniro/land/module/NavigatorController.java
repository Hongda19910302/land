package net.deniro.land.module;

import net.deniro.land.module.system.entity.MenuItem;
import net.deniro.land.module.system.entity.User;
import net.deniro.land.module.system.service.MenuService;
import net.deniro.land.module.system.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

/**
 * 导航
 *
 * @author deniro
 *         2015/10/12
 */
@Controller
@RequestMapping("/")
public class NavigatorController {

    static Logger logger = Logger.getLogger(NavigatorController.class);

    @Autowired
    private MenuService menuService;

    /**
     * 跳转至主界面
     *
     * @return
     */
    @RequestMapping(value = "/index")
    public String index(HttpSession session,ModelMap mm) {

        //依据当前登录账号的角色ID，查询所有可查看的菜单模块
        User currentUser = (User) session.getAttribute(UserService.USER_CODE);
        if (currentUser == null) {
            logger.warn("无法获取当前账号session！");
        }
        mm.addAttribute("allMenu",menuService.findAll(currentUser.getRoleId()));
        mm.addAttribute("currentUser",currentUser);

        return "/index";
    }

}
