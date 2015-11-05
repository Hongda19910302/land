package net.deniro.land.module.mobile.service;

import net.deniro.land.module.mobile.dao.BackRolePrivilegeDao;
import net.deniro.land.module.mobile.dao.ClientPrivilegeDao;
import net.deniro.land.module.mobile.entity.TBackRolePrivilege;
import net.deniro.land.module.mobile.entity.TClientPrivilege;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

        /**
         * 重置临时权限表
         */
        List<TClientPrivilege> privileges = clientPrivilegeDao.findAll();
        for (TClientPrivilege privilege : privileges) {
            privilege.setIsExist(TClientPrivilege.PrivilegeType.NO.code());
            clientPrivilegeDao.update(privilege);
        }

        /**
         * 设置权限
         */
        List<TBackRolePrivilege> rolePrivileges = backRolePrivilegeDao.findByRoleId(roleId);
        for (TBackRolePrivilege rolePrivilege : rolePrivileges) {
            if (rolePrivilege.getBackPrivilege() != null) {

                if (rolePrivilege.getBackPrivilege().getLevel().equals(SECOND_LEVEL)) {
                    String _privilegeName = rolePrivilege.getBackPrivilege().getName();
                    List<TClientPrivilege> currentPrivileges = clientPrivilegeDao.findByName
                            (_privilegeName);
                    if (currentPrivileges != null && !currentPrivileges.isEmpty()) {
                        TClientPrivilege currentPrivilege = currentPrivileges.get(0);

                        //表示该菜单模块存在
                        currentPrivilege.setIsExist(TClientPrivilege.PrivilegeType.YES
                                .code());
                        clientPrivilegeDao.update(currentPrivilege);
                    }
                }
            }
        }

        /**
         * 格式化
         */
        String _roleStr = null;
        List<TClientPrivilege> _privilegeList = clientPrivilegeDao.findAll();
        for (TClientPrivilege _clientPrivilege : _privilegeList) {
            Integer _privilege = _clientPrivilege.getIsExist();
            String _str = String.valueOf(_privilege);
            if (_roleStr != null) {
                _roleStr = _roleStr + _str;
            } else {
                _roleStr = _str;
            }
        }
        return _roleStr;
    }
}
