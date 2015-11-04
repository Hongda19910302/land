package net.deniro.land.module.system.service;

import net.deniro.land.common.dao.Page;
import net.deniro.land.common.spring.mvc.ResourcePathExposer;
import net.deniro.land.module.system.dao.DepartmentDao;
import net.deniro.land.module.system.entity.Department;
import net.deniro.land.module.system.entity.DepartmentQueryParam;
import net.deniro.land.module.system.entity.UserQueryParam;
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
     * 分页查询
     *
     * @param queryParam 查询参数
     * @return
     */
    public Page findPage(DepartmentQueryParam queryParam) {
        try {
            return departmentDao.findPage(queryParam);
        } catch (Exception e) {
            logger.error("分页查询", e);
            return new Page();
        }
    }


    /**
     * 查询所有子部门信息（正常状态）
     *
     * @param parentDepartmentId 父部门ID
     * @return
     */
    public List<Department> findChilds(Integer parentDepartmentId) {
        try {
            List<Department> departments = departmentDao.findChilds(parentDepartmentId);
            setAttribute(departments);
            return departments;
        } catch (Exception e) {
            logger.error("查询所有子部门信息（正常状态）", e);
            return new ArrayList<Department>();
        }
    }

    /**
     * 设置属性（是否为父部门、个性化图标）
     *
     * @param departments
     */
    private void setAttribute(List<Department> departments) {
        String ICON_URL_PREFIX = ResourcePathExposer.getResourceRoot()
                + "/dwz/themes/default/images/dialog/";

        List<Integer> parentDepartmentIds = departmentDao.findParentIds();
        for (Department department : departments) {
            if (parentDepartmentIds.contains(department.getDepartmentId())) {//是父节点
                department.setIsParent("true");
                department.setIconOpen(ICON_URL_PREFIX + "house.png");
                department.setIconClose(ICON_URL_PREFIX + "house_go.png");
            } else {
                department.setIsParent("false");
                department.setIcon(ICON_URL_PREFIX+"group.png");
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
            setAttribute(departments);
            return departments;
        } catch (Exception e) {
            logger.error(" 查询所有顶级部门（正常状态）", e);
            return new ArrayList<Department>();
        }
    }
}
