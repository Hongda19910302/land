package net.deniro.land.module.system.dao;

import net.deniro.land.common.dao.BaseDao;
import net.deniro.land.module.system.entity.TRegionRelation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 单位或部门与区域映射关系
 *
 * @author deniro
 *         2016/1/7
 */
@Repository
public class RegionRelationDao extends BaseDao<TRegionRelation> {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * 依据单位ID，删除所有与此单位相关的区域映射关系
     *
     * @param companyId
     * @return
     */
    public int deleteAllByCompanyId(Integer companyId) {
        String sql = "delete from t_region_relation where relation_type=0 and relation_id=:companyId";

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("companyId", companyId);

        return namedParameterJdbcTemplate.update(sql, params);
    }

    /**
     * 依据单位ID，获取与区域的映射关系
     *
     * @param companyId
     * @return
     */
    public List<TRegionRelation> findByCompanyId(Integer companyId) {
        String hql = "from TRegionRelation t where t.relationType=0 and t" +
                ".relationId=?";
        return this.find(hql, companyId);
    }
}
