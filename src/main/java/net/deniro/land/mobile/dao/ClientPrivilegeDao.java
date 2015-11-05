package net.deniro.land.mobile.dao;

import net.deniro.land.common.dao.BaseDao;
import net.deniro.land.mobile.entity.TClientPrivilege;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 客户端权限
 *
 * @author deniro
 *         2015/11/5
 */
@Repository
public class ClientPrivilegeDao extends BaseDao<TClientPrivilege> {
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
