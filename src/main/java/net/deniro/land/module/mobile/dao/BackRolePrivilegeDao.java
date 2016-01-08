package net.deniro.land.module.mobile.dao;

import net.deniro.land.common.dao.BaseDao;
import net.deniro.land.module.mobile.entity.TBackRolePrivilege;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
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
     * 批量插入角色与模块权限映射关系
     * @param moduleIds
     * @param roleId
     * @return
     */
    public int[] batchInsert(List<Integer> moduleIds, Integer roleId) {
        String sql = "INSERT INTO t_back_role_privilege(BACK_ROLE_ID, BACK_PRIVILEGE_ID) " +
                "VALUES ( :roleId, :moduleId) ";

        int size = moduleIds.size();
        SqlParameterSource[] parameterSources = new SqlParameterSource[size];
        for (int i = 0; i < size; i++) {
            MapSqlParameterSource mps = new MapSqlParameterSource();
            mps.addValue("roleId", roleId);
            mps.addValue("moduleId", moduleIds.get(i));
            parameterSources[i] = mps;
        }

        return namedParameterJdbcTemplate.batchUpdate(sql, parameterSources);
    }

    /**
     * 删除某个角色下的所有模块权限映射关系
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
