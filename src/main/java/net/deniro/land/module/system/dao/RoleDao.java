package net.deniro.land.module.system.dao;

import net.deniro.land.common.dao.BaseDao;
import net.deniro.land.common.dao.Page;
import net.deniro.land.common.entity.QueryParam;
import net.deniro.land.module.system.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import static net.deniro.land.module.system.entity.Role.Status.*;

/**
 * 角色
 *
 * @author deniro
 *         2015/10/14
 */
@Repository
public class RoleDao extends BaseDao<Role> {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * 分页查询
     *
     * @param queryParam 查询参数
     * @return
     */
    public Page findPage(QueryParam queryParam) {
        StringBuilder hql = new StringBuilder(" from Role t where 1=1 ");
        hql.append(" and t.status<>").append(DELETE.code());
        return super.pagedQuery(hql.toString(), queryParam.getPageNum(), queryParam
                .getNumPerPage());
    }


}
