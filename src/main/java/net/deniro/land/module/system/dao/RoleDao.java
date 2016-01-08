package net.deniro.land.module.system.dao;

import net.deniro.land.common.dao.BaseDao;
import net.deniro.land.common.dao.Page;
import net.deniro.land.common.entity.QueryParam;
import net.deniro.land.module.system.entity.Role;
import net.deniro.land.module.system.entity.RoleQueryParam;
import org.apache.commons.lang3.StringUtils;
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
    public Page findPage(RoleQueryParam queryParam) {
        StringBuilder hql = new StringBuilder(" from Role t where 1=1 ");
        hql.append(" and t.status<>").append(DELETE.code());

        /**
         * 新增查询条件
         */
        if (StringUtils.isNotBlank(queryParam.getBackRoleName())) {
            hql.append(" and t.backRoleName like '").append(queryParam.getBackRoleName())
                    .append("%'");
        }
        if(queryParam.getStatus()!=null){
            hql.append(" and t.status=").append(queryParam.getStatus());
        }
        if(queryParam.getCompanyId()!=null){
            hql.append(" and t.companyId=").append(queryParam.getCompanyId());
        }

        hql.append(" order by t.backRoleId desc");





        return super.pagedQuery(hql.toString(), queryParam.getPageNum(), queryParam
                .getNumPerPage());
    }


}
