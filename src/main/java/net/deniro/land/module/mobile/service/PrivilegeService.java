package net.deniro.land.module.mobile.service;

import net.deniro.land.module.mobile.dao.BackRolePrivilegeDao;
import net.deniro.land.module.mobile.dao.ClientPrivilegeDao;
import net.deniro.land.module.mobile.entity.TBackRolePrivilege;
import net.deniro.land.module.mobile.entity.TClientPrivilege;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static net.deniro.land.module.mobile.entity.TClientPrivilege.*;

/**
 * 权限
 *
 * @author deniro
 *         2015/11/5
 */
@Service
public class PrivilegeService {

    static Logger logger = Logger.getLogger(PrivilegeService.class);

    @Autowired
    private BackRolePrivilegeDao backRolePrivilegeDao;

    @Autowired
    private ClientPrivilegeDao clientPrivilegeDao;

    /**
     * 第二级菜单
     */
    @Deprecated
    public static final Integer SECOND_LEVEL = 2;


    /**
     * 依据角色ID，获取权限字符串
     *
     * @param roleId
     * @return 01100..010：
     * 1代表有权限，0无权限。各位置权限如下：
     * 0:新建案件
     * 1：立案审核
     * 2：案件查询
     * 3：案件巡查
     * 4：结案审核
     * 5：二次结案审核
     * 6: 我的案件
     * 7：案件分布
     * 8：案件回收站
     * 9：密码修改
     * 10：个人信息
     * 11：巡查员报表
     * 12：结案报表
     * 13：案件批示
     */
    public String findByRoleId(Integer roleId) {

        try {
            /**
             * 生成当前角色已拥有的权限名称集合
             */
            List<TBackRolePrivilege> rolePrivileges = backRolePrivilegeDao.findByRoleId(roleId);
            Set<String> privilegeNames = new HashSet<String>();
            for (TBackRolePrivilege rolePrivilege : rolePrivileges) {
                privilegeNames.add(rolePrivilege.getBackPrivilege().getName());
            }

            /**
             * 设置权限字符串
             */
            StringBuilder str = new StringBuilder();
            List<TClientPrivilege> clientPrivileges = clientPrivilegeDao.findAll();
            for (TClientPrivilege clientPrivilege : clientPrivileges) {
                if (privilegeNames.contains(clientPrivilege.getPrivilegeName())) {//有权限
                    str.append(PrivilegeType.YES.code());
                } else {//无权限
                    str.append(PrivilegeType.NO.code());
                }
            }
            return str.toString();
        } catch (Exception e) {
            logger.error("依据角色ID，获取权限字符串", e);
            return "";
        }
    }
}
