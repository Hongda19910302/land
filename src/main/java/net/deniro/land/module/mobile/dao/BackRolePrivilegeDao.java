package net.deniro.land.module.mobile.dao;

import net.deniro.land.common.dao.BaseDao;
import net.deniro.land.module.mobile.entity.TBackRolePrivilege;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 角色与权限关系
 *
 * @author deniro
 *         2015/11/5
 */
@Repository
public class BackRolePrivilegeDao extends BaseDao<TBackRolePrivilege> {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * 删除某个角色下的所有模块权限关系
     *
     * @param roleId
     * @return
     */
    public int deleteAllByRoleId(Integer roleId) {
        String sql = "delete from t_back_role_privilege where BACK_ROLE_ID=:roleId";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("roleId", roleId);

        return namedParameterJdbcTemplate.update(sql, params);
    }

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
