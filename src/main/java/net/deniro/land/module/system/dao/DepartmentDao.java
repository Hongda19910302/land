package net.deniro.land.module.system.dao;

import net.deniro.land.common.dao.BaseDao;
import net.deniro.land.common.dao.Page;
import net.deniro.land.module.system.entity.Department;
import net.deniro.land.module.system.entity.DepartmentQueryParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static net.deniro.land.module.system.entity.Department.Status.NORMAL;

/**
 * 部门
 *
 * @author deniro
 *         2015/10/28
 */
@Repository
public class DepartmentDao extends BaseDao<Department> {


    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * 分页查询
     *
     * @param queryParam 查询参数
     * @return
     */
    public Page findPage(DepartmentQueryParam queryParam) {
        StringBuilder hql = new StringBuilder(" from Department t where 1=1 ");

        /**
         * 新增查询条件
         */
        if (StringUtils.isNotBlank(queryParam.getName())) {
            hql.append(" and t.name like '").append(queryParam.getName())
                    .append("%'");
        }
        if (StringUtils.isNotBlank(queryParam.getStatus())) {
            hql.append(" and t.status = '").append(queryParam.getStatus())
                    .append("'");
        }
        if (StringUtils.isNotBlank(queryParam.getCompanyId())) {
            hql.append(" and t.companyId = '").append(queryParam.getCompanyId()).append("'");
        }

        hql.append(" order by departmentId desc");

        return super.pagedQuery(hql.toString(), queryParam.getPageNum(), queryParam
                .getNumPerPage());
    }

    /**
     * 查询所有父部门ID（去重、正常状态）
     *
     * @return
     */
    public List<Integer> findParentIds() {
        StringBuilder sql = new StringBuilder("SELECT DISTINCT(w.PARENT_ID) PARENT_ID FROM" +
                " " +
                "t_department w");
        sql.append(" WHERE w.`STATUS`=0 AND w.PARENT_ID IS NOT NULL");
        return namedParameterJdbcTemplate.query(sql.toString(), new RowMapper<Integer>() {
            public Integer mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getInt("PARENT_ID");
            }
        });
    }

    /**
     * 查询所有子部门信息（正常状态）
     *
     * @param parentDepartmentId 父部门ID
     * @return
     */
    public List<Department> findChilds(Integer parentDepartmentId) {
        StringBuilder hql = new StringBuilder(" from Department where 1=1 ");
        hql.append(" and status=").append(NORMAL.code());
        hql.append(" and parentId=?");

        return this.find(hql.toString(), new Object[]{parentDepartmentId});
    }

    /**
     * 查询所有顶级部门（正常状态）
     *
     * @param companyId 公司ID
     * @return
     */
    public List<Department> findTops(Integer companyId) {
        StringBuilder hql = new StringBuilder(" from Department where 1=1 ");
        hql.append(" and status=").append(NORMAL.code());
        hql.append(" and companyId=?");
        hql.append(" and parentId is null");

        return this.find(hql.toString(), new Object[]{companyId});
    }
}
