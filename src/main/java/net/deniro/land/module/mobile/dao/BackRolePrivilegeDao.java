package net.deniro.land.module.mobile.dao;

import net.deniro.land.common.dao.BaseDao;
import net.deniro.land.module.mobile.entity.TBackRolePrivilege;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 角色与权限关系
 *
 * @author deniro
 *         2015/11/5
 */
@Repository
public class BackRolePrivilegeDao extends BaseDao<TBackRolePrivilege> {

    /**
     * 依据角色ID，获取角色与权限关系
     *
     * @param roleId
     * @return
     */
    public List<TBackRolePrivilege> findByRoleId(Integer roleId) {
        String hql = " from TBackRolePrivilege where 1=1 ";
        hql += " and backRoleId = ?";
        return this.find(hql, roleId);
    }
}
