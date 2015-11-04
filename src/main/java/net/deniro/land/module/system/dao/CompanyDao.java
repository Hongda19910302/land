package net.deniro.land.module.system.dao;

import net.deniro.land.common.dao.BaseDao;
import net.deniro.land.common.dao.Page;
import net.deniro.land.module.system.entity.Company;
import net.deniro.land.module.system.entity.CompanyQueryParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 单位
 *
 * @author deniro
 *         2015/10/16
 */
@Repository
public class CompanyDao extends BaseDao<Company> {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * 分页查询
     *
     * @param queryParam 查询参数
     * @return
     */
    public Page findPage(CompanyQueryParam queryParam) {
        StringBuilder hql = new StringBuilder(" from Company t where 1=1 ");

        /**
         * 新增查询条件
         */
        if (StringUtils.isNotBlank(queryParam.getCompanyName())) {
            hql.append(" and t.companyName like '").append(queryParam.getCompanyName())
                    .append("%'");
        }
        if (StringUtils.isNotBlank(queryParam.getStatus())) {
            hql.append(" and t.status = '").append(queryParam.getStatus())
                    .append("'");
        }
        if (StringUtils.isNotBlank(queryParam.getCompanyId())) {
            hql.append(" and t.companyId = '").append(queryParam.getCompanyId()).append("'");
        }

        hql.append(" order by companyId desc");

        return super.pagedQuery(hql.toString(), queryParam.getPageNum(), queryParam
                .getNumPerPage());
    }

    /**
     * 查询所有单位
     *
     * @return
     */
    public List<Company> findAll() {
        StringBuilder hql = new StringBuilder(" from Company where 1=1 ");
        return this.find(hql.toString(), new Object[]{});
    }

}
