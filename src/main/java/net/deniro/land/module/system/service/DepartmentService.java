package net.deniro.land.module.system.service;

import net.deniro.land.module.system.dao.DepartmentDao;
import net.deniro.land.module.system.entity.Department;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 部门
 *
 * @author deniro
 *         2015/10/28
 */
@Service
public class DepartmentService {

    static Logger logger = Logger.getLogger(DepartmentService.class);

    @Autowired
    private DepartmentDao departmentDao;


    /**
     * 查询所有子部门信息（正常状态）
     *
     * @param parentDepartmentId 父部门ID
     * @return
     */
    public List<Department> findChilds(Integer parentDepartmentId) {
        try {
            List<Department> departments = departmentDao.findChilds(parentDepartmentId);
            setIsParentAttribute(departments);
            return departments;
        } catch (Exception e) {
            logger.error("查询所有子部门信息（正常状态）", e);
            return new ArrayList<Department>();
        }
    }

    /**
     * 设置是否为父部门属性
     *
     * @param departments
     */
    private void setIsParentAttribute(List<Department> departments) {
        List<Integer> parentDepartmentIds = departmentDao.findParentIds();
        for (Department department : departments) {
            if (parentDepartmentIds.contains(department.getDepartmentId())) {//是父节点
                department.setIsParent("true");
            } else {
                department.setIsParent("false");
            }
        }
    }

    /**
     * 查询所有顶级部门（正常状态）
     *
     * @param companyId 公司ID
     * @return
     */
    public List<Department> findTops(Integer companyId) {
        try {
            List<Department> departments = departmentDao.findTops(companyId);
            setIsParentAttribute(departments);
            return departments;
        } catch (Exception e) {
            logger.error(" 查询所有顶级部门（正常状态）", e);
            return new ArrayList<Department>();
        }
    }
}
