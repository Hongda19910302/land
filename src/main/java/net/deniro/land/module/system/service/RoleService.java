package net.deniro.land.module.system.service;

import net.deniro.land.common.dao.Page;
import net.deniro.land.module.system.dao.RoleDao;
import net.deniro.land.module.system.entity.Role;
import net.deniro.land.module.system.entity.RoleQueryParam;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
