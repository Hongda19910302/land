package net.deniro.land.module.system.service;

import net.deniro.land.common.spring.mvc.ResourcePathExposer;
import net.deniro.land.module.icase.entity.ModuleTreeNode;
import net.deniro.land.module.icase.entity.Modules;
import net.deniro.land.module.system.dao.MenuDao;
import net.deniro.land.module.system.entity.MenuItem;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
    @Deprecated
    public List<MenuItem> findAllTopInDisplay() {
        try {
            List<MenuItem> items = menuDao.findAllTopInDisplay();
            setAttribute(items, null);
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
     * @param roleId   角色ID
     * @return
     */
    @Deprecated
    public List<MenuItem> findChildrenInDisplay(Integer parentId, Integer roleId) {
        try {
            List<MenuItem> items = menuDao.findChildrenInDisplay(parentId);
            setAttribute(items, roleId);
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
     * @param roleId
     */
    private void setAttribute(List<MenuItem> menuItems, Integer roleId) {
        String ICON_URL_PREFIX = ResourcePathExposer.getResourceRoot()
                + "/dwz/themes/default/images/dialog/";

        List<Integer> parentIds = menuDao.findParentIds();

        if (roleId != null) {//有权限的模块设置勾选
            List<Integer> authorityIds = menuDao.findChildrenIdsByRoleId(roleId);
            for (MenuItem entity : menuItems) {
                if (authorityIds.contains(entity.getBackPrivilegeId())) {
                    entity.setChecked("true");
                }
            }
        }


        for (MenuItem entity : menuItems) {
            if (parentIds.contains(entity.getBackPrivilegeId())) {//是父节点
                entity.setIsParent("true");
                entity.setIconOpen(ICON_URL_PREFIX + "folder.png");
                entity.setIconClose(ICON_URL_PREFIX + "folder_go.png");
            } else {
                entity.setIsParent("false");
                entity.setIcon(ICON_URL_PREFIX + "application_view_detail.png");
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
