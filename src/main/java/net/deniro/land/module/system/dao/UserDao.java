package net.deniro.land.module.system.dao;

import net.deniro.land.common.dao.BaseDao;
import net.deniro.land.module.system.entity.User;
import org.springframework.stereotype.Repository;

import static net.deniro.land.module.system.entity.User.Status.*;

import java.util.List;


/**
 * 用户
 *
 * @author deniro
 *         2015/10/10
 */
@Repository
public class UserDao extends BaseDao<User> {

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
