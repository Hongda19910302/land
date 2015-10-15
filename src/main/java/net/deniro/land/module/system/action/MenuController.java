package net.deniro.land.module.system.action;

import net.deniro.land.module.system.entity.MenuItem;
import net.deniro.land.module.system.entity.User;
import net.deniro.land.module.system.service.MenuService;
import net.deniro.land.module.system.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * 菜单模块
 *
 * @author deniro
 *         2015/10/13
 */
@Controller
@RequestMapping("/menu")
public class MenuController {

    static Logger logger = Logger.getLogger(MenuController.class);

    @Autowired
    private MenuService menuService;

    /**
     * 依据当前登录账号的角色ID，查询所有可查看的菜单模块
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "/findAll")
    @ResponseBody
    @Deprecated
    public List<MenuItem> findAll(HttpSession session) {
        User currentUser = (User) session.getAttribute(UserService.USER_CODE);
        if (currentUser == null) {
            logger.warn("无法获取当前账号session！");
            return new ArrayList<MenuItem>();
        }

        return menuService.findAll(currentUser.getRoleId());
    }
}
