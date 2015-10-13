package net.deniro.land.module.system.service;

import net.deniro.land.module.system.dao.MenuDao;
import net.deniro.land.module.system.entity.MenuItem;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜单模块
 *
 * @author deniro
 *         2015/10/13
 */
@Service
public class MenuService {

    static Logger logger = Logger.getLogger(MenuService.class);

    @Autowired
    private MenuDao menuDao;

    /**
     * 依据角色ID，查询所有可查看的菜单模块
     *
     * @param roleId
     * @return
     */
    public List<MenuItem> findAll(Integer roleId) {
        try {
            //查询顶级菜单模块
            List<MenuItem> tops = menuDao.findTopByRoleId(roleId);
            if (tops.isEmpty()) {
                return tops;
            }

            //查询所有子菜单模块
            List<MenuItem> children = menuDao.findChildrenByRoleId(roleId);
            if (children.isEmpty()) {
                return tops;
            }

            //合并
            for (MenuItem child : children) {
                int parentId = child.getParentId();
                for (MenuItem top : tops) {
                    int topId = top.getBackPrivilegeId();
                    if (parentId == topId) {//把子菜单模块挂到顶级菜单模块下
                        top.getChild().add(child);
                        break;
                    }
                }
            }

            return tops;
        } catch (Exception e) {
            logger.error("依据角色ID，查询所有可查看的菜单模块", e);
            return new ArrayList<MenuItem>();
        }
    }
}
