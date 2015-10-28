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
            return departmentDao.findChilds(parentDepartmentId);
        } catch (Exception e) {
            logger.error("查询所有子部门信息（正常状态）", e);
            return new ArrayList<Department>();
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
            return departmentDao.findTops(companyId);
        } catch (Exception e) {
            logger.error(" 查询所有顶级部门（正常状态）", e);
            return new ArrayList<Department>();
        }
    }
}
