package net.deniro.land.module.system.dao;

import net.deniro.land.common.dao.BaseDao;
import net.deniro.land.module.system.entity.MenuItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * 菜单模块
 *
 * @author deniro
 *         2015/10/12
 */
@Repository
public class MenuDao extends BaseDao<MenuItem> {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * 查询顶级菜单模块列表（依据角色ID）
     *
     * @param roleId 角色ID
     * @return
     */
    public List<MenuItem> findTopByRoleId(Integer roleId) {
        String sql = "SELECT * from t_back_privilege x WHERE x.PARENT_ID is null  AND x.BACK_PRIVILEGE_ID in(" +
                "SELECT DISTINCT(v.BACK_PRIVILEGE_ID) FROM t_back_role_privilege v WHERE v.BACK_ROLE_ID=:roleId) " +
                "ORDER BY x.SORT_NO";
        MapSqlParameterSource mps = new MapSqlParameterSource().addValue("roleId", roleId);

        return namedParameterJdbcTemplate.query(sql, mps, new RowMapper<MenuItem>() {
            @Override
            public MenuItem mapRow(ResultSet resultSet, int i) throws SQLException {
                MenuItem entity = new MenuItem();
                entity.setName(resultSet.getString("name"));
                entity.setBackPrivilegeId(resultSet.getInt("BACK_PRIVILEGE_ID"));
                entity.setLevel(resultSet.getInt("LEVEL"));
                entity.setParentId(resultSet.getInt("PARENT_ID"));
                entity.setSortNo(resultSet.getInt("SORT_NO"));
                return entity;
            }
        });
    }

    /**
     * 获取顶级菜单模块列表
     *
     * @return
     */
    @Deprecated
    public List<MenuItem> findTop() {
        String hql = " from MenuItem t where t.parentId is null order by t.sortNo";
        return this.find(hql);
    }
}
