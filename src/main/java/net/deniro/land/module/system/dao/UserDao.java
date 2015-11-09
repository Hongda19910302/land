package net.deniro.land.module.system.dao;

import net.deniro.land.common.dao.BaseDao;
import net.deniro.land.common.dao.Page;
import net.deniro.land.module.icase.entity.TCase;
import net.deniro.land.module.system.entity.RoleQueryParam;
import net.deniro.land.module.system.entity.User;
import net.deniro.land.module.system.entity.UserQueryParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import static net.deniro.land.module.icase.entity.TCase.*;
import static net.deniro.land.module.system.entity.User.Status.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


/**
 * 用户
 *
 * @author deniro
 *         2015/10/10
 */
@Repository
public class UserDao extends BaseDao<User> {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * 依据部门ID，获取巡查员列表
     *
     * @param departmentId
     * @return
     */
    public List<User> findInspectorsByDepartmentId(Integer departmentId) {
        String hql = "select u from User u,TBackRolePrivilege cp where 1=1 ";
        hql += " and u.department.departmentId=? ";
        hql += " and u.roleId = cp.backRoleId ";
        hql += " and cp.backPrivilegeId = ? ";
        return this.find(hql, departmentId, CASE_INSPECTOR_PRIVILEGE_MODULE_ID);
    }

    /**
     * 依据创建者ID，获取巡查员列表
     *
     * @param creatorId
     * @return
     */
    public List<User> findInspectorsByCreatorId(Integer creatorId) {
        StringBuilder sql = new StringBuilder(" select distinct u.* ");
        sql.append(" from t_user u,t_back_role r,T_BACK_ROLE_PRIVILEGE cp ");
        sql.append(" where 1=1 ");
        sql.append(" and r.company_Id in ").append("(SELECT w.COMPANY_ID FROM t_user w " +
                "WHERE w.USER_ID=37)");//同一家公司
        sql.append(" and u.department_Id in").append(" (SELECT w.DEPARTMENT_ID FROM " +
                "t_user w WHERE w.USER_ID=" + creatorId + ")");//同一个部门
        sql.append(" and u.back_role_id =r.back_role_id");
        sql.append(" and r.back_role_id =cp.back_role_id");
        sql.append(" and cp.BACK_PRIVILEGE_ID=").append(
                CASE_INSPECTOR_PRIVILEGE_MODULE_ID);//巡查员权限

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

    /**
     * 分页查询
     *
     * @param queryParam 查询参数
     * @return
     */
    public Page findPage(UserQueryParam queryParam) {
        StringBuilder hql = new StringBuilder(" from User t where 1=1 ");
        hql.append(" and t.status<>").append(CANCEL.code());

        /**
         * 新增查询条件
         */
        if (StringUtils.isNotBlank(queryParam.getAccount())) {
            hql.append(" and t.account like '").append(queryParam.getAccount())
                    .append("%'");
        }
        if (StringUtils.isNotBlank(queryParam.getName())) {
            hql.append(" and t.name like '").append(queryParam.getName())
                    .append("%'");
        }
        if (StringUtils.isNotBlank(queryParam.getCompanyId())) {
            hql.append(" and t.companyId = '").append(queryParam.getCompanyId()).append("'");
        }
        if (StringUtils.isNotBlank(queryParam.getDepartmentId())) {
            hql.append(" and t.departmentId = '").append(queryParam.getDepartmentId()).append("'");
        }

        hql.append(" order by createTime desc");

        return super.pagedQuery(hql.toString(), queryParam.getPageNum(), queryParam
                .getNumPerPage());
    }

    /**
     * 依据账号获取用户信息
     *
     * @param account 账号
     * @return
     */
    public List<User> findByAccount(String account) {
        StringBuilder hql = new StringBuilder(" from User where status=");
        hql.append(NORMAL.code());
        hql.append(" and account = ?");
        return this.find(hql.toString(), new Object[]{account});
    }
}
