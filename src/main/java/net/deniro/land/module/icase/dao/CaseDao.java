package net.deniro.land.module.icase.dao;

import net.deniro.land.common.dao.BaseDao;
import net.deniro.land.module.icase.entity.TCase;
import net.deniro.land.module.system.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * 案件
 *
 * @author deniro
 *         2015/11/9
 */
@Repository
public class CaseDao extends BaseDao<TCase> {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * 依据创建者ID，获取巡查员列表
     *
     * @param creatorId
     * @return
     */
    public List<User> findByCreatorId(Integer creatorId) {
        StringBuilder sql = new StringBuilder(" select distinct u.* ");
        sql.append(" from t_user u,t_back_role r,T_BACK_ROLE_PRIVILEGE cp ");
        sql.append(" where 1=1 ");
        sql.append(" and r.company_Id in ").append("(SELECT w.COMPANY_ID FROM t_user w " +
                "WHERE w.USER_ID=37)");
        sql.append(" and u.department_Id in").append(" (SELECT w.DEPARTMENT_ID FROM " +
                "t_user w WHERE w.USER_ID=" + creatorId + ")");
        sql.append(" and u.back_role_id =r.back_role_id");
        sql.append(" and r.back_role_id =cp.back_role_id");
        sql.append(" and cp.BACK_PRIVILEGE_ID=").append(TCase
                .CASE_INSPECTOR_PRIVILEGE_MODULE_ID);

        return namedParameterJdbcTemplate.query(sql.toString(), new RowMapper<User>() {
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                User user = new User();
                user.setAccount(resultSet.getString("ACCOUNT"));
                user.setName(resultSet.getString("NAME"));
                user.setPosition(resultSet.getString("POSITION"));
                user.setUserId(resultSet.getInt("USER_ID"));
                return user;
            }
        });
    }
}
