package net.deniro.land.module.system.service;

import net.deniro.land.common.dao.Page;
import net.deniro.land.module.mobile.dao.BackRolePrivilegeDao;
import net.deniro.land.module.system.dao.RoleDao;
import net.deniro.land.module.system.entity.Role;
import net.deniro.land.module.system.entity.RoleQueryParam;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 角色
 *
 * @author deniro
 *         2015/10/14
 */
@Service
public class RoleService {

    static Logger logger = Logger.getLogger(RoleService.class);

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private BackRolePrivilegeDao backRolePrivilegeDao;

    /**
     * 设置权限
     *
     * @param roleId    角色ID
     * @param moduleIds 模块ID列表
     * @return
     */
    public boolean setAuthority(Integer roleId, String moduleIds) {
        try {
            backRolePrivilegeDao.deleteAllByRoleId(roleId);

            //转换为Integer数组
            String[] ids = moduleIds.split(",");
            List<Integer> modules = new ArrayList<Integer>();
            for (int i = 0; i < ids.length; i++) {
                modules.add(NumberUtils.toInt(ids[i]));
            }

            backRolePrivilegeDao.batchInsert(modules, roleId);

            return true;
        } catch (Exception e) {
            logger.error("设置权限", e);
            return false;
        }
    }

    /**
     * 更新
     *
     * @param entity
     * @return
     */
    public boolean update(Role entity) {
        try {
            roleDao.update(entity);
            return true;
        } catch (Exception e) {
            logger.error("更新", e);
            return false;
        }
    }

    /**
     * 新增
     *
     * @param entity
     * @return
     */
    public boolean add(Role entity) {
        try {
            roleDao.save(entity);
            return true;
        } catch (Exception e) {
            logger.error("新增", e);
            return false;
        }
    }

    /**
     * 依据ID，获取角色对象
     *
     * @param roleId
     * @return
     */
    public Role findById(Integer roleId) {
        try {
            return roleDao.get(roleId);
        } catch (Exception e) {
            logger.error("依据ID，获取角色对象", e);
            return new Role();
        }
    }

    /**
     * 分页查询
     *
     * @param queryParam 查询参数
     * @return
     */
    public Page findPage(RoleQueryParam queryParam) {
        try {
            return roleDao.findPage(queryParam);
        } catch (Exception e) {
            logger.error("分页查询", e);
            return new Page();
        }
    }
}
