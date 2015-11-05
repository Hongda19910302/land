package net.deniro.land.module.mobile.dao;

import net.deniro.land.common.dao.BaseDao;
import net.deniro.land.module.mobile.entity.TClientPrivilege;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.deniro.land.module.mobile.entity.TClientPrivilege.PrivilegeType;

/**
 * 客户端权限
 *
 * @author deniro
 *         2015/11/5
 */
@Repository
public class ClientPrivilegeDao extends BaseDao<TClientPrivilege> {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * 依据权限名，批量更新权限
     *
     * @param params key：权限名称；value：权限类型
     * @return
     */
    @Deprecated
    public int[] batchUpdateByName(Map<String, PrivilegeType> params) {
        List<SqlParameterSource> list = new ArrayList<SqlParameterSource>();
        for (Map.Entry<String, PrivilegeType> param : params.entrySet()) {
            list.add(new MapSqlParameterSource("privilegeName", param
                    .getKey()));
            list.add(new MapSqlParameterSource("isExist", param
                    .getValue().code()));
        }


        String sql = "update t_client_privilege set is_exist=:isExist where " +
                "privilege_name=:privilegeName";

        return namedParameterJdbcTemplate.batchUpdate(sql, list.toArray
                (new SqlParameterSource[0]));
    }

    /**
     * 更新所有记录的权限
     *
     * @param type 权限类型
     * @return 更新的记录数
     */
    @Deprecated
    public int updateAll(PrivilegeType type) {
        String sql = " update t_client_privilege set is_exist=:isExist ";

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("isExist", type.code());

        return namedParameterJdbcTemplate.update(sql, params);
    }

    /**
     * 依据名称，获取权限
     *
     * @param name 名称
     * @return
     */
    public List<TClientPrivilege> findByName(String name) {
        String hql = " from TClientPrivilege t where privilegeName=? order by t" +
                ".privilegeOrder";
        return this.find(hql, name);
    }

    /**
     * 获取所有权限
     *
     * @return
     */
    public List<TClientPrivilege> findAll() {
        String hql = " from TClientPrivilege t where 1=1 order by t.privilegeOrder";
        return this.find(hql);
    }

}
