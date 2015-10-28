package net.deniro.land.module.system.dao;

import net.deniro.land.common.dao.BaseDao;
import net.deniro.land.module.system.entity.Department;
import org.springframework.stereotype.Repository;

import java.util.List;

import static net.deniro.land.module.system.entity.Department.Status.*;

/**
 * 部门
 *
 * @author deniro
 *         2015/10/28
 */
@Repository
public class DepartmentDao extends BaseDao<Department> {

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
