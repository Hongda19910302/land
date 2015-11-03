package net.deniro.land.module.system.dao;

import net.deniro.land.common.dao.BaseDao;
import net.deniro.land.common.dao.Page;
import net.deniro.land.module.system.entity.RoleQueryParam;
import net.deniro.land.module.system.entity.User;
import net.deniro.land.module.system.entity.UserQueryParam;
import org.apache.commons.lang3.StringUtils;
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
