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
     * 获取所有菜单模块（可显示）
     *
     * @return
     */
    public List<MenuItem> findAll() {
        String hql = " from MenuItem t where t.isDisplay='true' order by t.sortNo";
        return this.find(hql);
    }

    /**
     * 查询所有父菜单项ID（去重、可见状态）
     *
     * @return
     */
    public List<Integer> findParentIds() {
        StringBuilder sql = new StringBuilder("SELECT DISTINCT(w.PARENT_ID) PARENT_ID FROM" +
                " " +
                "T_BACK_PRIVILEGE w");
        sql.append(" WHERE w.is_display='true' AND w.PARENT_ID IS NOT NULL");
        return namedParameterJdbcTemplate.query(sql.toString(), new RowMapper<Integer>() {
            public Integer mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getInt("PARENT_ID");
            }
        });
    }

    /**
     * 依据父菜单ID，查询所有可见的子菜单模块
     *
     * @param parentId 父菜单ID
     * @return
     */
    public List<MenuItem> findChildrenInDisplay(Integer parentId) {
        String sql = "SELECT * from t_back_privilege x WHERE x.PARENT_ID=:parentId " +
                "and x.is_display='true' order by x.sort_no";
        MapSqlParameterSource mps = new MapSqlParameterSource().addValue("parentId", parentId);
        return findMenuItems(sql, mps);
    }

    /**
     * 查询所有可见的顶级菜单
     *
     * @return
     */
    public List<MenuItem> findAllTopInDisplay() {
        String sql = "SELECT * from t_back_privilege x WHERE x.PARENT_ID is null and x" +
                ".is_display='true' order by x.sort_no";
        MapSqlParameterSource mps = new MapSqlParameterSource();
        return findMenuItems(sql, mps);
    }

    /**
     * 依据角色ID，获取可查看的菜单模块ID列表
     */
    public static final String MENU_ITEMS_BY_ROLE_ID_SQL = "SELECT DISTINCT(v" +
            ".BACK_PRIVILEGE_ID) " +
            "FROM t_back_role_privilege v WHERE v.BACK_ROLE_ID=:roleId";

    /**
     * 依据角色ID，获取可查看的子菜单模块ID列表
     *
     * @return
     */
    public List<Integer> findChildrenIdsByRoleId(Integer roleId) {
        StringBuilder sql = new StringBuilder("SELECT v.BACK_PRIVILEGE_ID FROM t_back_privilege w,t_back_role_privilege v WHERE ");
        sql.append(" w.BACK_PRIVILEGE_ID=v.BACK_PRIVILEGE_ID AND v.BACK_ROLE_ID=:roleId  AND w" +
                ".is_display='true' AND w.PARENT_ID IS NOT NULL");

        MapSqlParameterSource mps = new MapSqlParameterSource().addValue("roleId", roleId);

        return namedParameterJdbcTemplate.query(sql.toString(), mps, new
                RowMapper<Integer>() {
                    public Integer mapRow(ResultSet resultSet, int i) throws SQLException {
                        return resultSet.getInt("BACK_PRIVILEGE_ID");
                    }
                });
    }

    /**
     * 查询所有子菜单模块（依据角色ID）
     *
     * @param roleId 角色ID
     * @return
     */
    public List<MenuItem> findChildrenByRoleId(Integer roleId) {
        String sql = "SELECT b.* FROM t_back_privilege AS a,t_back_privilege AS b WHERE a" +
                ".BACK_PRIVILEGE_ID= b.PARENT_ID AND b.is_display='true' AND a" +
                ".BACK_PRIVILEGE_ID in("
                + MENU_ITEMS_BY_ROLE_ID_SQL + ") ORDER " +
                "BY b.PARENT_ID,b.SORT_NO";
        MapSqlParameterSource mps = new MapSqlParameterSource().addValue("roleId", roleId);
        return findMenuItems(sql, mps);
    }

    /**
     * 查询顶级菜单模块（依据角色ID）
     *
     * @param roleId 角色ID
     * @return
     */
    public List<MenuItem> findTopByRoleId(Integer roleId) {
        String sql = "SELECT * from t_back_privilege x WHERE x.PARENT_ID is null  AND x" +
                ".is_display='true'" +
                "  AND x.BACK_PRIVILEGE_ID in(" +
                MENU_ITEMS_BY_ROLE_ID_SQL +
                ")ORDER BY x.SORT_NO";
        MapSqlParameterSource mps = new MapSqlParameterSource().addValue("roleId", roleId);

        return findMenuItems(sql, mps);
    }

    /**
     * 查询菜单项列表
     *
     * @param sql
     * @param mps
     * @return
     */
    private List<MenuItem> findMenuItems(String sql, MapSqlParameterSource mps) {
        return namedParameterJdbcTemplate.query(sql, mps, new RowMapper<MenuItem>() {
            public MenuItem mapRow(ResultSet resultSet, int i) throws SQLException {
                MenuItem entity = new MenuItem();
                entity.setName(resultSet.getString("name"));
                entity.setBackPrivilegeId(resultSet.getInt("BACK_PRIVILEGE_ID"));
                entity.setLevel(resultSet.getInt("LEVEL"));
                entity.setParentId(resultSet.getInt("PARENT_ID"));
                entity.setSortNo(resultSet.getInt("SORT_NO"));
                entity.setUrl(resultSet.getString("URL2"));
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
