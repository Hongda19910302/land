package net.deniro.land.module.system.service;

import net.deniro.land.common.spring.mvc.ResourcePathExposer;
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

    /**
     * 【案件巡查】模块ID
     */
    public static final Integer CASE_INSPECT_MODULE_ID = 12;

    @Autowired
    private MenuDao menuDao;

    /**
     * 查询所有可见的顶级菜单
     *
     * @return
     */
    public List<MenuItem> findAllTopInDisplay() {
        try {
            List<MenuItem> items = menuDao.findAllTopInDisplay();
            setAttribute(items);
            return items;
        } catch (Exception e) {
            logger.error("查询所有可见的顶级菜单", e);
            return new ArrayList<MenuItem>();
        }
    }

    /**
     * 依据父菜单ID，查询所有可见的子菜单模块
     *
     * @param parentId 父菜单ID
     * @return
     */
    public List<MenuItem> findChildrenInDisplay(Integer parentId) {
        try {
            List<MenuItem> items = menuDao.findChildrenInDisplay(parentId);
            setAttribute(items);
            return items;
        } catch (Exception e) {
            logger.error(" 依据父菜单ID，查询所有可见的子菜单模块", e);
            return new ArrayList<MenuItem>();
        }
    }

    /**
     * 设置属性（是否为父部门、个性化图标）
     *
     * @param menuItems
     */
    private void setAttribute(List<MenuItem> menuItems) {
        String ICON_URL_PREFIX = ResourcePathExposer.getResourceRoot()
                + "/dwz/themes/default/images/dialog/";

        List<Integer> parentIds = menuDao.findParentIds();
        for (MenuItem entity : menuItems) {
            if (parentIds.contains(entity.getBackPrivilegeId())) {//是父节点
                entity.setIsParent("true");
                entity.setIconOpen(ICON_URL_PREFIX + "house.png");
                entity.setIconClose(ICON_URL_PREFIX + "house_go.png");
            } else {
                entity.setIsParent("false");
                entity.setIcon(ICON_URL_PREFIX + "group.png");
            }
        }
    }

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
