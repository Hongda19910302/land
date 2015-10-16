package net.deniro.land.module.system.dao;

import net.deniro.land.common.dao.BaseDao;
import net.deniro.land.module.system.entity.Company;
import net.deniro.land.module.system.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static net.deniro.land.module.system.entity.User.Status.NORMAL;

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
     * 查询所有单位
     *
     * @return
     */
    public List<Company> findAll() {
        StringBuilder hql = new StringBuilder(" from Company where 1=1 ");
        return this.find(hql.toString(), new Object[]{});
    }

}
